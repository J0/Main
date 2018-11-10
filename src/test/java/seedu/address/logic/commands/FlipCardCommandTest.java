package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_REVIEWING_DECK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FlipCardCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakinInDeckReview;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.FlipCardRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class FlipCardCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAnakinInDeckReview(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAnakinInDeckReview(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_flipcard_success() {
        assertCommandSuccess(new FlipCardCommand(), model, commandHistory, MESSAGE_SUCCESS,
                expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof FlipCardRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_flipcard_failure() {
        model.endReview();
        assertCommandFailure(new FlipCardCommand(), model, commandHistory, MESSAGE_NOT_REVIEWING_DECK);
    }
}
