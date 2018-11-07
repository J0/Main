package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakin;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Anakin;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAnakin_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAnakin(ClearCommand.COMMAND_WORD);

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAnakin_success() {
        Model model = new ModelManager(getTypicalAnakin(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAnakin(), new UserPrefs());
        expectedModel.resetData(new Anakin());
        expectedModel.commitAnakin(ClearCommand.COMMAND_WORD);

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

}
