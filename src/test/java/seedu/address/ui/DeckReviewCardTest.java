package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertDeckReviewCardDisplaysCard;

import org.junit.Test;

import guitests.guihandles.DeckReviewCardHandle;
import seedu.address.model.deck.Card;
import seedu.address.testutil.CardBuilder;

public class DeckReviewCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Card card = new CardBuilder().build();
        // question card
        DeckReviewCard uiCardQuestion = new DeckReviewCard(card, false);
        uiPartRule.setUiPart(uiCardQuestion);
        assertCardDisplay(uiCardQuestion, card, false);

        // answer card
        DeckReviewCard uiCardAnswer = new DeckReviewCard(card, true);
        uiPartRule.setUiPart(uiCardAnswer);
        assertCardDisplay(uiCardAnswer, card, true);
    }

    @Test
    public void equals() {
        Card card = new CardBuilder().build();
        DeckReviewCard uiCard = new DeckReviewCard(card, false);

        // same object -> returns true
        assertTrue(uiCard.equals(uiCard));

        // null -> returns false
        assertFalse(uiCard.equals(null));

        // different types -> returns false
        assertFalse(uiCard.equals(0));

        // different card -> returns false
        Card differentCard = new CardBuilder().withQuestion("differentQuestion")
                .withAnswer("differentAnswer").build();
        assertFalse(uiCard.equals(new DeckReviewCard(differentCard, false)));
    }

    /**
     * Asserts that {@code uiCard} displays the details of {@code expectedCard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(DeckReviewCard uiCard, Card expectedCard, boolean showAnswer) {
        guiRobot.pauseForHuman();

        DeckReviewCardHandle deckReviewCardHandle = new DeckReviewCardHandle(uiCard.getRoot(), showAnswer);

        // verify card details are displayed correctly
        assertDeckReviewCardDisplaysCard(expectedCard, deckReviewCardHandle, showAnswer);
    }
}
