package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstDeck;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakin;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAnakin(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAnakin(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstDeck(model);
        deleteFirstDeck(model);
        model.undoAnakin();
        model.undoAnakin();

        deleteFirstDeck(expectedModel);
        deleteFirstDeck(expectedModel);
        expectedModel.undoAnakin();
        expectedModel.undoAnakin();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoAnakin();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS + DeleteDeckCommand.COMMAND_WORD, expectedModel);

        // single redoable state in model
        expectedModel.redoAnakin();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS + DeleteDeckCommand.COMMAND_WORD, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
