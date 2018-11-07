package systemtests;

//import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS;
import static seedu.address.testutil.TestUtil.getDeck;
import static seedu.address.testutil.TestUtil.getLastIndexDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

//import static seedu.address.testutil.TestUtil.getMidIndexDeck;
//import static seedu.address.testutil.TypicalDecks.KEYWORD_MATCHING_JOHN;
//import seedu.address.logic.commands.RedoCommand;

public class DeleteCommandSystemTest extends AnakinSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeckCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown
 -------------------- */

        /* Case: delete the first deck in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteDeckCommand.COMMAND_WORD + "      " + INDEX_FIRST_DECK.getOneBased() + " ";
        Deck deletedPerson = removeDeck(expectedModel, INDEX_FIRST_DECK);
        String expectedResultMessage = String.format(MESSAGE_DELETE_DECK_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last deck in the list -> deleted */
        Model anakinModelBeforeDeletingLast = getModel();
        Index lastDeckIndex = getLastIndexDeck(anakinModelBeforeDeletingLast);
        assertCommandSuccess(lastDeckIndex);

        /* Case: undo deleting the last deck in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS + DeleteDeckCommand.COMMAND_WORD;
        assertCommandSuccess(command, anakinModelBeforeDeletingLast, expectedResultMessage);

        //        /* Case: redo deleting the last Deck in the list -> last deck deleted again */
        //        command = RedoCommand.COMMAND_WORD;
        //        removeDeck(anakinModelBeforeDeletingLast, lastDeckIndex);
        //        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        //        assertCommandSuccess(command, anakinModelBeforeDeletingLast, expectedResultMessage);
        //
        //        /* Case: delete the middle deck in the list -> deleted */
        //        Index middleDeckIndex = getMidIndexDeck(getModel());
        //        assertCommandSuccess(middleDeckIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown
 ---------------------- */

        //     /* Case: filtered person list, delete index within bounds of address book and person list -> deleted*/
        //        showDecksWithName(KEYWORD_MATCHING_JOHN);
        //        Index index = INDEX_FIRST_DECK;
        //        assertTrue(index.getZeroBased() < getModel().getFilteredDeckList().size());
        //        assertCommandSuccess(index);

        //        /* Case: filtered person list, delete index within bounds of address book but out of bounds of person
        //        list
        //         * -> rejected
        //         */
        //        showDecksWithName(KEYWORD_MATCHING_JOHN);
        //        int invalidIndex = getModel().getAnakin().getDeckList().size();
        //        command = DeleteDeckCommand.COMMAND_WORD + " " + invalidIndex;
        //        assertCommandFailure(command, MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a person card is selected
 ------------------------ */

        //         /* Case: delete the selected deck -> deck list panel selects the person before the deleted person */
        //        showAllPersons();
        //        expectedModel = getModel();
        //        Index selectedIndex = getLastIndex(expectedModel);
        //        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        //        selectPerson(selectedIndex);
        //        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        //        deletedPerson = removeDeck(expectedModel, selectedIndex);
        //        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        //        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);
        //
        //        /* --------------------------------- Performing invalid delete operation
        // ------------------------------------ */
        //
        /* Case: invalid index (0) -> rejected */
        command = DeleteDeckCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteDeckCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAnakin().getDeckList().size() + 1);
        command = DeleteDeckCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteDeckCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteDeckCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Deck} at the specified {@code index} in {@code anakinModel}'s address book.
     * @return the removed person
     */
    private Deck removeDeck(Model anakinModel, Index index) {
        Deck targetDeck = getDeck(anakinModel, index);
        anakinModel.deleteDeck(targetDeck);
        return targetDeck;
    }

    /**
     * Deletes the person at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Deck deletedDeck = removeDeck(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_DECK_SUCCESS, deletedDeck);

        assertCommandSuccess(
                DeleteDeckCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
                expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that
 the browser url
     * and selected card are expected to update accordingly depending on the card at {@code
 expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AnakinSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
