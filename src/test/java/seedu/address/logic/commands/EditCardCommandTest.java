package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_B;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_FAILURE;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakinInDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCardCommand.EditCardDescriptor;
import seedu.address.model.Anakin;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Answer;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Question;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.EditCardDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditCardCommand.
 */
public class EditCardCommandTest {

    private Model model = new ModelManager(getTypicalAnakinInDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void getIntoFirstDeck() {
        model.getIntoDeck(model.getFilteredDeckList().get(0));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Card editedCard = new Card(new Question("sample"), new Answer("sample"));
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCardCommand = new EditCardCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(INDEX_FIRST_CARD.getOneBased()), editedCard);
        expectedModel.commitAnakin(EditCardCommand.COMMAND_WORD);

        assertCommandSuccess(editCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        CardBuilder cardInList = new CardBuilder(lastCard);
        Card editedCard = cardInList
            .withQuestion(VALID_QUESTION_B).withAnswer(VALID_ANSWER_A).build();

        EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
            .withQuestion(VALID_QUESTION_B)
            .withAnswer(VALID_ANSWER_A).build();
        EditCardCommand editCardCommand = new EditCardCommand(indexLastCard, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new Anakin(model.getAnakin()), new UserPrefs());
        expectedModel.updateCard(lastCard, editedCard);
        expectedModel.commitAnakin(EditCardCommand.COMMAND_WORD);

        assertCommandSuccess(editCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCardCommand editCardCommand = new EditCardCommand(INDEX_FIRST_CARD, new EditCardDescriptor());
        Card editedCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new Anakin(model.getAnakin()), new UserPrefs());
        expectedModel.commitAnakin(EditCardCommand.COMMAND_WORD);

        assertCommandSuccess(editCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        //showCardAtIndex(model, INDEX_FIRST_CARD);

        Card cardInFilteredList = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        Card editedCard = new CardBuilder(cardInFilteredList).withQuestion(VALID_QUESTION_A).build();
        EditCardCommand editCardCommand = new EditCardCommand(INDEX_FIRST_CARD,
            new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A).build());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new Anakin(model.getAnakin()), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(0), editedCard);
        expectedModel.commitAnakin(EditCardCommand.COMMAND_WORD);

        assertCommandSuccess(editCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAnakinCardUnfilteredList_failure() {
        Card firstCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(firstCard).build();
        EditCardCommand editCardCommand = new EditCardCommand(INDEX_SECOND_CARD, descriptor);

        assertCommandFailure(editCardCommand, model, commandHistory, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_duplicateCardFilteredList_failure() {
        //showCardAtIndex(model, INDEX_FIRST_CARD);

        // edit card in filtered list into a duplicate in address book
        Card cardInList = model.getAnakin().getCardList().get(INDEX_SECOND_CARD.getZeroBased());
        EditCardCommand editCardCommand = new EditCardCommand(INDEX_FIRST_CARD,
            new EditCardDescriptorBuilder(cardInList).build());

        assertCommandFailure(editCardCommand, model, commandHistory, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_invalidAnakinCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A).build();
        EditCardCommand editCardCommand = new EditCardCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCardCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    // TODO: When filter functions are available write filter test
    //    @Test
    //    public void execute_invalidAnakinCardIndexFilteredList_failure() {
    //        showCardAtIndex(model, INDEX_FIRST_CARD);
    //        Index outOfBoundIndex = INDEX_SECOND_CARD;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getAnakin().getCardList().size());
    //
    //        EditCardCommand editCardCommand = new EditCardCommand(outOfBoundIndex,
    //                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A).build());
    //
    //        assertCommandFailure(editCardCommand, model, commandHistory, AddressbookMessages
    // .MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    //    }
    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Card editedCard = new CardBuilder().build();
        Card cardToEdit = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCardCommand = new EditCardCommand(INDEX_FIRST_CARD, descriptor);
        Model expectedModel = new ModelManager(new Anakin(model.getAnakin()), new UserPrefs());
        expectedModel.updateCard(cardToEdit, editedCard);
        expectedModel.commitAnakin(EditCardCommand.COMMAND_WORD);

        // edit -> first card edited
        editCardCommand.execute(model, commandHistory);

        // undo -> reverts anakin back to previous state and filtered card list to show all cards
        expectedModel.undoAnakin();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
            UndoCommand.MESSAGE_SUCCESS + EditCardCommand.COMMAND_WORD, expectedModel);

        // redo -> same first card edited again
        expectedModel.redoAnakin();
        assertCommandSuccess(new seedu.address.logic.commands.RedoCommand(), model, commandHistory,
            RedoCommand.MESSAGE_SUCCESS + EditCardCommand.COMMAND_WORD, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A).build();
        EditCardCommand editCardCommand = new EditCardCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCardCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, MESSAGE_FAILURE);
        assertCommandFailure(new seedu.address.logic.commands.RedoCommand(), model, commandHistory,
            seedu.address.logic.commands.RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Card} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited card in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the card object regardless of indexing.
     */

    @Test
    public void executeUndoRedo_validIndexFilteredList_sameAnakinCardEdited() throws Exception {
        Card editedCard = new CardBuilder().build();
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCardCommand = new EditCardCommand(INDEX_FIRST_CARD, descriptor);
        Model expectedModel = new ModelManager(new Anakin(model.getAnakin()), new UserPrefs());

        //  showCardAtIndex(model, INDEX_SECOND_CARD);
        Card cardToEdit = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        expectedModel.updateCard(cardToEdit, editedCard);
        expectedModel.commitAnakin(EditCardCommand.COMMAND_WORD);

        // edit -> edits second card in unfiltered card list / first card in filtered card list
        editCardCommand.execute(model, commandHistory);

        // undo -> reverts anakin back to previous state and filtered card list to show all cards
        expectedModel.undoAnakin();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS + EditCardCommand.COMMAND_WORD, expectedModel);

        // assertNotEquals(model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased()), cardToEdit);
        // redo -> edits same second card in unfiltered card list
        expectedModel.redoAnakin();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS + EditCardCommand.COMMAND_WORD, expectedModel);
    }

    @Test
    public void equals() {
        EditCardDescriptor cardDesc = new EditCardDescriptorBuilder()
            .withAnswer(VALID_ANSWER_A).withQuestion(VALID_QUESTION_A)
            .build();
        EditCardDescriptor cardDescB = new EditCardDescriptorBuilder()
            .withAnswer(VALID_ANSWER_B).withQuestion(VALID_QUESTION_B)
            .build();
        final EditCardCommand standardCommand = new EditCardCommand(INDEX_FIRST_CARD, cardDesc);

        // same values -> returns true
        EditCardDescriptor copyDescriptor = new EditCardDescriptor(cardDesc);
        EditCardCommand commandWithSameValues = new EditCardCommand(INDEX_FIRST_CARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_SECOND_CARD, cardDesc)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_FIRST_CARD, cardDescB)));
    }

}
