package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeckAtIndex;
import static seedu.address.testutil.TypicalDecks.DECK_WITH_CARDS;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakin;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAnakin(), new UserPrefs());
        expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_DECK, expectedModel);

        model.getIntoDeck(DECK_WITH_CARDS);
        expectedModel.getIntoDeck(DECK_WITH_CARDS);
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_CARD, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_DECK, expectedModel);

        model.getIntoDeck(DECK_WITH_CARDS);
        expectedModel.getIntoDeck(DECK_WITH_CARDS);
        //showCardAtIndexOfCurrentDeck(model, INDEX_FIRST_CARD);
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_CARD, expectedModel);
    }
}
