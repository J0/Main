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
 * {@code PreviousCardCommand}.
 */
public class PreviousCardCommandTest {
    private Model model = new ModelManager(getTypicalAnakinInDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executePreviousCardSuccess() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);
        model.startReview();

        PreviousCardCommand previousCardCommand = new PreviousCardCommand();
        String expectedMessage = String.format(PreviousCardCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        int newIndex = model.getIndexOfCurrentCard() - 1;
        if (newIndex < 0) {
            newIndex = model.getFilteredCardList().size() - 1;
        }
        expectedModel.setIndexOfCurrentCard(newIndex);

        assertCommandSuccess(previousCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executePreviousCardFail() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);

        PreviousCardCommand previousCardCommand = new PreviousCardCommand();
        String expectedMessage = String.format(MESSAGE_NOT_REVIEWING_DECK);

        assertCommandFailure(previousCardCommand, model, commandHistory, expectedMessage);
    }
}
