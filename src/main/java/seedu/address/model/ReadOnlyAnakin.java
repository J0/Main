package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * Unmodifiable view of Anakin
 */
public interface ReadOnlyAnakin {

    /**
     * Returns an unmodifiable view of the decks list.
     * This list will not contain any duplicate decks.
     */
    ObservableList<Deck> getDeckList();

    /**
     * Returns an unmodifiable view of the cards list.
     * This list will not contain any duplicate cards.
     */
    ObservableList<Card> getCardList();

    /**
     * Returns true if user is inside a deck.
     */
    boolean isInsideDeck();

    /**
     * @return boolean of whether deck is being reviewed.
     */
    boolean isReviewingDeck();

    /**
     * @return string represents the last commands that commits Anakin
     */
    String getLastCommand();
}
