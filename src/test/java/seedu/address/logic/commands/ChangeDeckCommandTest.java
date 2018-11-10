package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ChangeDeckCommand.EXIT_DECK_ARGS;
import static seedu.address.logic.commands.ChangeDeckCommand.MESSAGE_CD_SUCCESS;
import static seedu.address.logic.commands.ChangeDeckCommand.MESSAGE_EXIT_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeckAtIndex;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakin;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakinInDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DECK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Deck;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ChangeDeckCommand}.
 */
public class ChangeDeckCommandTest {

    private Model model = new ModelManager(getTypicalAnakin(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Deck deckToEnter = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
        ChangeDeckCommand cdCommand = new ChangeDeckCommand(INDEX_FIRST_DECK);

        String expectedMessage = String.format(MESSAGE_CD_SUCCESS, deckToEnter);

        ModelManager expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.getIntoDeck(deckToEnter);
        expectedModel.commitAnakin(ChangeDeckCommand.COMMAND_WORD);

        assertCommandSuccess(cdCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        ChangeDeckCommand cdCommand = new ChangeDeckCommand(outOfBoundIndex);

        assertCommandFailure(cdCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Deck deckToEnter = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
        ChangeDeckCommand cdCommand = new ChangeDeckCommand(INDEX_FIRST_DECK);

        String expectedMessage = String.format(MESSAGE_CD_SUCCESS, deckToEnter);

        Model expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.getIntoDeck(deckToEnter);
        expectedModel.commitAnakin(ChangeDeckCommand.COMMAND_WORD);

        assertCommandSuccess(cdCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_validEnterDeck_success() {
        Model actualModel = new ModelManager(this.model.getAnakin(), new UserPrefs());
        Deck deckToEnter = this.model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
        ChangeDeckCommand cdCommand = new ChangeDeckCommand(INDEX_FIRST_DECK);

        String expectedMessage = String.format(MESSAGE_CD_SUCCESS, deckToEnter);

        // Enter deck so that cdCommand can leave it
        Model expectedModel = new ModelManager(actualModel.getAnakin(), new UserPrefs());

        expectedModel.getIntoDeck(deckToEnter);
        expectedModel.commitAnakin(ChangeDeckCommand.COMMAND_WORD);

        assertCommandSuccess(cdCommand, actualModel, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validLeaveDeck_success() {
        Model actualModel = new ModelManager(getTypicalAnakinInDeck(), new UserPrefs());
        ChangeDeckCommand cdCommand = new ChangeDeckCommand();

        String expectedMessage = String.format(MESSAGE_EXIT_SUCCESS);

        Model expectedModel = new ModelManager(actualModel.getAnakin(), new UserPrefs());

        expectedModel.getOutOfDeck();
        expectedModel.commitAnakin(ChangeDeckCommand.COMMAND_WORD + EXIT_DECK_ARGS);

        assertCommandSuccess(cdCommand, actualModel, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidLeaveDeck_throwsCommandException() {
        ChangeDeckCommand cdCommand = new ChangeDeckCommand();

        String expectedException = String.format(Messages.MESSAGE_NOT_INSIDE_DECK);

        assertCommandFailure(cdCommand, model, commandHistory, expectedException);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);

        Index outOfBoundIndex = INDEX_SECOND_DECK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAnakin().getDeckList().size());

        ChangeDeckCommand cdCommand = new ChangeDeckCommand(outOfBoundIndex);

        assertCommandFailure(cdCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ChangeDeckCommand cdFirstCommand = new ChangeDeckCommand(INDEX_FIRST_DECK);
        ChangeDeckCommand cdSecondCommand = new ChangeDeckCommand(INDEX_SECOND_DECK);

        // same object -> returns true
        assertTrue(cdFirstCommand.equals(cdFirstCommand));

        // same values -> returns true
        ChangeDeckCommand cdFirstCommandCopy = new ChangeDeckCommand(INDEX_FIRST_DECK);
        assertTrue(cdFirstCommand.equals(cdFirstCommandCopy));

        // different types -> returns false
        assertFalse(cdFirstCommand.equals(1));

        // null -> returns false
        assertFalse(cdFirstCommand.equals(null));

        // different deck -> returns false
        assertFalse(cdFirstCommand.equals(cdSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDeck(Model model) {
        model.updateFilteredDeckList(p -> false);

        assertTrue(model.getFilteredDeckList().isEmpty());
    }
}
