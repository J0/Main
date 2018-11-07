package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakinInDeck;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.TypicalCards;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ReviewCommand}.
 */
public class ReviewCommandTest {
    private Model model = new ModelManager(getTypicalAnakinInDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeReviewSuccess() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);

        ReviewCommand reviewCommand = new ReviewCommand(Index.fromOneBased(1));
        String expectedMessage = String.format(ReviewCommand.MESSAGE_SUCCESS, deckToReview);

        ModelManager expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.startReview();

        assertCommandSuccess(reviewCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeReviewFail() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);
        model.startReview();

        ReviewCommand reviewCommand = new ReviewCommand(Index.fromOneBased(1));
        String expectedMessage = String.format(ReviewCommand.MESSAGE_ALREADY_REVIEWING_DECK);

        assertCommandFailure(reviewCommand, model, commandHistory, expectedMessage);
    }
}
