package seedu.address.model.deck;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERFORMANCE_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_A;
import static seedu.address.testutil.TypicalCards.CARD_A;
import static seedu.address.testutil.TypicalCards.CARD_B;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.CardBuilder;

public class CardTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameCard() {
        // same object -> returns true
        assertTrue(CARD_A.isSameCard(CARD_A));

        // null -> returns false
        assertFalse(CARD_A.isSameCard(null));

        // different question -> returns false
        Card editedCardA = new CardBuilder(CARD_A).withQuestion(VALID_QUESTION_A).build();
        assertFalse(CARD_A.isSameCard(editedCardA));

        // same question, different answers -> returns true
        editedCardA = new CardBuilder(CARD_A).withAnswer(VALID_ANSWER_A).build();
        assertTrue(CARD_A.isSameCard(editedCardA));

        // same question, different performanec -> retruns true
        editedCardA = new CardBuilder(CARD_A).withPerformance(VALID_PERFORMANCE_A).build();
        assertTrue(CARD_A.isSameCard(editedCardA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Card cardCopy = new CardBuilder(CARD_A).build();
        assertTrue(CARD_A.equals(cardCopy));

        // same object -> returns true
        assertTrue(CARD_A.equals(CARD_A));

        // null -> returns false
        assertFalse(CARD_A.equals(null));

        // different type -> returns false
        assertFalse(CARD_A.equals(5));

        // different card -> returns false
        assertFalse(CARD_A.equals(CARD_B));
    }
}
