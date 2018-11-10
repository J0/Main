package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NOT_REVIEWING_DECK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakinInDeck;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.TypicalCards;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EndReviewCommand}.
 */
public class EndReviewCommandTest {
    private Model model = new ModelManager(getTypicalAnakinInDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeEndReviewSuccess() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);
        model.startReview();

        EndReviewCommand endReviewCommand = new EndReviewCommand();
        String expectedMessage = String.format(EndReviewCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.endReview();
        expectedModel.commitAnakin(EndReviewCommand.COMMAND_WORD);

        assertCommandSuccess(endReviewCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeEndReviewFail() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);

        EndReviewCommand endReviewCommand = new EndReviewCommand();
        String expectedMessage = String.format(MESSAGE_NOT_REVIEWING_DECK);

        assertCommandFailure(endReviewCommand, model, commandHistory, expectedMessage);
    }
}
