package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakinInDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCardCommand}.
 */
public class DeleteCardCommandTest {

    private Model model = new ModelManager(getTypicalAnakinInDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void getIntoFirstDeck() {
        model.getIntoDeck(model.getFilteredDeckList().get(0));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCardCommand deleteCommand = new DeleteCardCommand(INDEX_FIRST_CARD);

        String expectedMessage = String.format(DeleteCardCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        ModelManager expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.deleteCard(cardToDelete);
        expectedModel.commitAnakin(DeleteCardCommand.COMMAND_WORD);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        DeleteCardCommand deleteCommand = new DeleteCardCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        //showCardAtIndex(model, INDEX_FIRST_CARD);
        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCardCommand deleteCommand = new DeleteCardCommand(INDEX_FIRST_CARD);

        String expectedMessage = String.format(DeleteCardCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        Model expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.deleteCard(cardToDelete);
        expectedModel.commitAnakin(DeleteCardCommand.COMMAND_WORD);
        showNoCard(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCardCommand deleteFirstCommand = new DeleteCardCommand(INDEX_FIRST_CARD);
        DeleteCardCommand deleteSecondCommand = new DeleteCardCommand(INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCardCommand deleteFirstCommandCopy = new DeleteCardCommand(INDEX_FIRST_CARD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(0));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCard(Model model) {
        model.updateFilteredCardList(p -> false);

        assertTrue(model.getFilteredCardList().isEmpty());
    }
}
