package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NOT_REVIEWING_DECK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakinInDeckReview;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Performance;

public class ClassifyCommandTest {

    private Model model = new ModelManager(getTypicalAnakinInDeckReview(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_classify_success() {
        int currentIndex = model.getIndexOfCurrentCard();
        Card card = model.getFilteredCardList().get(currentIndex);
        Card editedCard = Card.classifyCard(card, Performance.EASY);

        String expectedMessage = String.format(ClassifyCommand.MESSAGE_CLASSIFICATION_SUCCESS,
                Performance.EASY, editedCard);

        Model expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(currentIndex), editedCard);

        assertCommandSuccess(new ClassifyCommand(Performance.EASY), model, commandHistory, expectedMessage,
                expectedModel);
    }

    @Test
    public void execute_classify_failure() {
        model.endReview();
        assertCommandFailure(new ClassifyCommand(Performance.EASY), model, commandHistory, MESSAGE_NOT_REVIEWING_DECK);
    }
}
