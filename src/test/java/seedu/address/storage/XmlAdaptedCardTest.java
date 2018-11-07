package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCards.CARD_A_WITH_META;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deck.Answer;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Performance;
import seedu.address.model.deck.Question;
import seedu.address.testutil.Assert;

public class XmlAdaptedCardTest {
    private static final String INVALID_QUESTION = " ";
    private static final String INVALID_ANSWER = " ";
    private static final String INVALID_PERFORMANCE = "tough";
    private static final String VALID_QUESTION = CARD_A_WITH_META.getQuestion().toString();
    private static final String VALID_ANSWER = CARD_A_WITH_META.getAnswer().toString();
    private static final String VALID_PERFORMANCE = CARD_A_WITH_META.getPerformance().toString();

    @Test
    public void toModelType_validCardDetails_returnsCard() throws Exception {
        XmlAdaptedCard card = new XmlAdaptedCard(CARD_A_WITH_META);
        Card actualCard = card.toModelType();
        assertEquals(CARD_A_WITH_META, actualCard);
        // Check for equality of metadata too
        assertEquals(CARD_A_WITH_META.getPerformance(), actualCard.getPerformance());
        assertEquals(CARD_A_WITH_META.getTimesReviewed(), actualCard.getTimesReviewed());
    }

    @Test
    public void toModelType_validCardDetailsNoMeta_returnsCardDefaultMeta() throws Exception {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_QUESTION, VALID_ANSWER);
        Card actualCard = card.toModelType();
        assertEquals(CARD_A_WITH_META, actualCard);
        assertEquals(Performance.NORMAL, actualCard.getPerformance());
        assertEquals(0, actualCard.getTimesReviewed());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(INVALID_QUESTION, VALID_ANSWER);
        String expectedMessage = Question.MESSAGE_QUESTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(null, VALID_ANSWER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_QUESTION, INVALID_ANSWER);
        String expectedMessage = Answer.MESSAGE_ANSWER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_QUESTION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidPerformance_returnsCard() throws Exception {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_QUESTION, VALID_ANSWER, INVALID_PERFORMANCE, 0);
        Card actualCard = card.toModelType();
        assertEquals(CARD_A_WITH_META, actualCard);
    }

    @Test
    public void toModelType_validPerformance_returnsCard() throws Exception {
        XmlAdaptedCard card = new XmlAdaptedCard(VALID_QUESTION, VALID_ANSWER, VALID_PERFORMANCE, 0);
        Card actualCard = card.toModelType();
        assertEquals(CARD_A_WITH_META, actualCard);
        assertEquals(CARD_A_WITH_META.getPerformance(), actualCard.getPerformance());
    }
}
