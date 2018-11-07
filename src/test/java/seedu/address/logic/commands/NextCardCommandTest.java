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
 * {@code NextCardCommand}.
 */
public class NextCardCommandTest {
    private Model model = new ModelManager(getTypicalAnakinInDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeNextCardSuccess() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);
        model.startReview();

        NextCardCommand nextCardCommand = new NextCardCommand();
        String expectedMessage = String.format(NextCardCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.setIndexOfCurrentCard(model.getIndexOfCurrentCard() + 1);

        assertCommandSuccess(nextCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeNextCardLastIndexSuccess() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);
        model.startReview();
        model.setIndexOfCurrentCard(model.getFilteredCardList().size() - 1);

        NextCardCommand nextCardCommand = new NextCardCommand();
        String expectedMessage = String.format(NextCardCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.setIndexOfCurrentCard(0);

        assertCommandSuccess(nextCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeNextCardFail() {
        Deck deckToReview = TypicalCards.getTypicalDeck();
        model.getIntoDeck(deckToReview);

        NextCardCommand nextCardCommand = new NextCardCommand();
        String expectedMessage = String.format(MESSAGE_NOT_REVIEWING_DECK);

        assertCommandFailure(nextCardCommand, model, commandHistory, expectedMessage);
    }
}
