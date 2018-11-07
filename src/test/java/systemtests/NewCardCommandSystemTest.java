package systemtests;

//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.testutil.TypicalDecks.DECK_A;
//import static seedu.address.testutil.TypicalDecks.AMY;
//import static seedu.address.testutil.TypicalDecks.DECK_B;
//import static seedu.address.testutil.TypicalDecks.DECK_C;
//import static seedu.address.testutil.TypicalDecks.DECK_D;
//import static seedu.address.testutil.TypicalDecks.IDA;
//import static seedu.address.testutil.TypicalDecks.KEYWORD_MATCHING_JOHN;

//import org.junit.Test;

//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.NewDeckCommand;
//import seedu.address.logic.commands.NewCardCommand;
//
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
//import seedu.address.model.Model;
//import seedu.address.model.deck.Name;
//import seedu.address.model.deck.Deck;
//import seedu.address.testutil.DeckBuilder;
//import seedu.address.testutil.TestUtil;

public class NewCardCommandSystemTest extends AnakinSystemTest {

//    @Test
//    public void add() {
//        Model addressbookModel = getModel();
//
//        /* ------------------------ Perform add operations on the shown unfiltered list
// ----------------------------- */
//
//        /* Case: add a Deck without cards to a non-empty anakin, command with leading spaces and trailing
// spaces
//         * -> added
//         */
//        Deck toAdd = DECK_A;
//        String command = "   " + NewDeckCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
//                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: undo adding Amy to the list -> Amy deleted */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, addressbookModel, expectedResultMessage);
//
//        /* Case: redo adding Deck_A to the list -> Deck_A added again */
//        command = RedoCommand.COMMAND_WORD;
//        addressbookModel.addDeck(toAdd);
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, addressbookModel, expectedResultMessage);
//
//        /* Case: add a deck with all fields same as another person in the anakin except name -> added */
//        toAdd = new DeckBuilder(DECK_A).withName(DECK_B).build();
//        command = NewDeckCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
//                + TAG_DESC_FRIEND;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a person with all fields same as another person in the anakin except phone and email
//         * -> added
//         */
//        toAdd = new DeckBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
//        command = TestUtil.getNewCardCommand(toAdd);
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add to empty anakin -> added */
//        deleteAllDecks();
//        assertCommandSuccess(ALICE);
//
//        /* Case: add a person with tags, command with parameters in random order -> added */
//        toAdd = BOB;
//        command = NewCardCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
//                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a person, missing tags -> added */
//        assertCommandSuccess(HOON);
//
//        /* -------------------------- Perform add operation on the shown filtered list
// ------------------------------ */
//
//        /* Case: filters the person list before adding -> added */
//        showDecksWithName(KEYWORD_MATCHING_JOHN);
//        assertCommandSuccess(IDA);
//
//        /* ------------------------ Perform add operation while a person card is selected
// --------------------------- */
//
//        /* Case: selects first card in the deck list, add a person -> added, card selection remains unchanged */
//        selectDeck(Index.fromOneBased(1));
//        assertCommandSuccess(CARL);
//
//        /* ----------------------------------- Perform invalid add operations
// --------------------------------------- */
//
//        /* Case: add a duplicate person -> rejected */
//        command = TestUtil.getNewCardCommand(HOON);
//        assertCommandFailure(command, NewCardCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate person except with different cards -> rejected */
//        toAdd = new DeckBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
//        command = TestUtil.getNewCardCommand(toAdd);
//        assertCommandFailure(command, NewCardCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate deck except with different email -> rejected */
//        toAdd = new DeckBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
//        command = TestUtil.getNewCardCommand(toAdd);
//        assertCommandFailure(command, NewCardCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate person except with different address -> rejected */
//        toAdd = new DeckBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
//        command = TestUtil.getNewCardCommand(toAdd);
//        assertCommandFailure(command, NewCardCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate person except with different tags -> rejected */
//        command = TestUtil.getNewCardCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
//        assertCommandFailure(command, NewCardCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: missing name -> rejected */
//        command = NewCardCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCardCommand.MESSAGE_USAGE));
//
//        /* Case: missing phone -> rejected */
//        command = NewCardCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCardCommand.MESSAGE_USAGE));
//
//        /* Case: missing email -> rejected */
//        command = NewCardCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCardCommand.MESSAGE_USAGE));
//
//        /* Case: missing address -> rejected */
//        command = NewCardCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCardCommand.MESSAGE_USAGE));
//
//        /* Case: invalid keyword -> rejected */
//        command = "newcards " + TestUtil.getDeckDetails(toAdd);
//        assertCommandFailure(command, AddressbookMessages.MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: invalid name -> rejected */
//        command = NewCardCommand.COMMAND_WORD + INVALID_NAME_DESC + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);
//

//    }
//
//    /**
//     * Executes the {@code NewCardCommand} that adds {@code toAdd} to the model and asserts that the,<br>
//     * 1. Command box displays an empty string.<br>
//     * 2. Command box has the default style class.<br>
//     * 3. Result display box displays the success message of executing {@code NewCardCommand} with the details of
//     * {@code toAdd}.<br>
//     * 4. {@code Storage} and {@code DeckListPanel} equal to the corresponding components in
//     * the current model added with {@code toAdd}.<br>
//     * 5. Browser url and selected card remain unchanged.<br>
//     * 6. Status bar's sync status changes.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(Deck toAdd) {
//        assertCommandSuccess(TestUtil.getNewCardCommand(toAdd), toAdd);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(Deck)}. Executes {@code command}
//     * instead.
//     * @see NewCardCommandSystemTest#assertCommandSuccess(Deck)
//     */
//    private void assertCommandSuccess(String command, Deck toAdd) {
//        Model expectedModel = getModel();
//        expectedModel.addDeck(toAdd);
//        String expectedResultMessage = String.format(NewCardCommand.MESSAGE_SUCCESS, toAdd);
//
//        assertCommandSuccess(command, expectedModel, expectedResultMessage);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Deck)} except asserts that
//     * the,<br>
//     * 1. Result display box displays {@code expectedResultMessage}.<br>
//     * 2. {@code Storage} and {@code DeckListPanel} equal to the corresponding components in
//     * {@code expectedModel}.<br>
//     * @see NewCardCommandSystemTest#assertCommandSuccess(String, Deck)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String
//            expectedResultMessage) {
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchangedExceptSyncStatus();
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays {@code command}.<br>
//     * 2. Command box has the error style class.<br>
//     * 3. Result display box displays {@code expectedResultMessage}.<br>
//     * 4. {@code Storage} and {@code DeckListPanel} remain unchanged.<br>
//     * 5. Browser url, selected card and status bar remain unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
}
