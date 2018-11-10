package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.CardCardHandle;
import guitests.guihandles.CardListPanelHandle;
import guitests.guihandles.DeckCardHandle;
import guitests.guihandles.DeckListPanelHandle;
import guitests.guihandles.DeckReviewCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualDeckCard} displays the same values as {@code expectedDeckCard}.
     */
    public static void assertDeckCardEquals(DeckCardHandle expectedDeckCard, DeckCardHandle actualDeckCard) {
        assertEquals(expectedDeckCard.getId(), actualDeckCard.getId());
        assertEquals(expectedDeckCard.getName(), actualDeckCard.getName());
    }

    /**
     * Asserts that {@code actualDeckCard} displays the details of {@code expectedDeck}.
     */
    public static void assertDeckCardDisplaysDeck(Deck expectedDeck, DeckCardHandle actualCard) {
        assertEquals(expectedDeck.getName().fullName, actualCard.getName());
    }

    /**
     * Asserts that the list in {@code deckListPanelHandle} displays the details of {@code decks} correctly and
     * in the correct order.
     */
    public static void assertDeckListMatching(DeckListPanelHandle deckListPanelHandle, Deck... decks) {
        for (int i = 0; i < decks.length; i++) {
            deckListPanelHandle.navigateToCard(i);
            assertDeckCardDisplaysDeck(decks[i], deckListPanelHandle.getDeckCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code deckListPanelHandle} displays the details of {@code decks} correctly and
     * in the correct order.
     */
    public static void assertDeckListMatching(DeckListPanelHandle deckListPanelHandle, List<Deck> decks) {
        assertDeckListMatching(deckListPanelHandle, decks.toArray(new Deck[0]));
    }

    /**
     * Asserts the size of the list in {@code deckListPanelHandle} equals to {@code size}.
     */
    public static void assertDeckListSize(DeckListPanelHandle deckListPanelHandle, int size) {
        int numberOfDecks = deckListPanelHandle.getListSize();
        assertEquals(size, numberOfDecks);
    }

    /**
     * Asserts that {@code actualCardCard} displays the same values as {@code expectedCardCard}.
     */
    public static void assertCardCardEquals(CardCardHandle expectedCard, CardCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getQuestion(), actualCard.getQuestion());
    }

    /**
     * Asserts that {@code actualCardCard} displays the details of {@code expectedCard}.
     */
    public static void assertCardCardDisplaysCard(Card expectedCard, CardCardHandle actualCard) {
        assertEquals(expectedCard.getQuestion().fullQuestion, actualCard.getQuestion());
    }

    /**
     * Asserts that the list in {@code cardListPanelHandle} displays the details of {@code cards} correctly and
     * in the correct order.
     */
    public static void assertCardListMatching(CardListPanelHandle cardListPanelHandle, Card... cards) {
        for (int i = 0; i < cards.length; i++) {
            cardListPanelHandle.navigateToCard(i);
            assertCardCardDisplaysCard(cards[i], cardListPanelHandle.getCardCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code cardListPanelHandle} displays the details of {@code cards} correctly and
     * in the correct order.
     */
    public static void assertCardListMatching(CardListPanelHandle cardListPanelHandle, List<Card> cards) {
        assertCardListMatching(cardListPanelHandle, cards.toArray(new Card[0]));
    }

    /**
     * Asserts the size of the list in {@code cardListPanelHandle} equals to {@code size}.
     */
    public static void assertCardListSize(CardListPanelHandle cardListPanelHandle, int size) {
        int numberOfCards = cardListPanelHandle.getListSize();
        assertEquals(size, numberOfCards);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    /**
     * Asserts that {@code actualDeckReviewCard} displays the details of {@code expectedDeckReviewCard}.
     */
    public static void assertDeckReviewCardDisplaysCard(Card expectedCard, DeckReviewCardHandle actualCard,
                                                        boolean showAnswer) {
        if (showAnswer) {
            assertEquals("A: " + expectedCard.getAnswer().fullAnswer, actualCard.getText());
        } else {
            assertEquals("Q: " + expectedCard.getQuestion().fullQuestion, actualCard.getText());
        }

    }
}
