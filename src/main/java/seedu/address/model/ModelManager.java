package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AnakinChangedEvent;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.anakinexceptions.DeckNotFoundException;

/**
 * Represents the in-memory model of Anakin data.
 */
public class ModelManager extends ComponentManager implements Model {
    public static final Logger LOGGER = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAnakin versionedAnakin;
    private final FilteredList<Deck> filteredDecks;
    // The filteredCards is not assigned. Should have methods to assign filteredCards (when user is inside a deck).
    private FilteredList<Card> filteredCards;

    /**
     * Initializes a ModelManager with the given Anakin and userPrefs.
     */
    public ModelManager(ReadOnlyAnakin anakin, UserPrefs userPrefs) {
        super();
        requireAllNonNull(anakin, userPrefs);

        LOGGER.fine("Initializing with anakin: " + anakin + " and user prefs " + userPrefs);

        versionedAnakin = new VersionedAnakin(anakin);
        filteredDecks = new FilteredList<>(versionedAnakin.getDeckList());
        filteredCards = new FilteredList<>(versionedAnakin.getCardList());
    }

    public ModelManager() {
        this(new Anakin(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAnakin newData) {
        versionedAnakin.resetData(newData);
        indicateAnakinChanged();
    }

    @Override
    public ReadOnlyAnakin getAnakin() {
        return versionedAnakin;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAnakinChanged() {
        raise(new AnakinChangedEvent(versionedAnakin));
    }

    @Override
    public void sort() {
        versionedAnakin.sort();
        if (isInsideDeck()) {
            updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        } else {
            updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        }
        indicateAnakinChanged();
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireAllNonNull(deck);
        return versionedAnakin.hasDeck(deck);
    }

    @Override
    public void deleteDeck(Deck deck) {
        versionedAnakin.removeDeck(deck);
        indicateAnakinChanged();
    }

    @Override
    public void addDeck(Deck deck) {
        versionedAnakin.addDeck(deck);
        updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        indicateAnakinChanged();
    }

    @Override
    public void updateDeck(Deck target, Deck editedDeck) {
        requireAllNonNull(target, editedDeck);

        versionedAnakin.updateDeck(target, editedDeck);
        indicateAnakinChanged();
    }

    @Override
    public boolean hasCard(Card card) throws DeckNotFoundException {
        requireAllNonNull(card);
        return versionedAnakin.hasCard(card);
    }

    @Override
    public void deleteCard(Card card) {
        versionedAnakin.removeCard(card);
        indicateAnakinChanged();
    }

    @Override
    public void addCard(Card card) throws DeckNotFoundException {
        versionedAnakin.addCard(card);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        indicateAnakinChanged();
    }

    @Override
    public void updateCard(Card target, Card editedCard) throws DeckNotFoundException {
        requireAllNonNull(target, editedCard);

        versionedAnakin.updateCard(target, editedCard);
        indicateAnakinChanged();
    }

    @Override
    public void getIntoDeck(Deck deck) {
        requireAllNonNull(deck);
        versionedAnakin.getIntoDeck(deck);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        indicateAnakinChanged();
    }

    @Override
    public void getOutOfDeck() {
        versionedAnakin.getOutOfDeck();
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        indicateAnakinChanged();
    }

    @Override
    public boolean isInsideDeck() {
        return versionedAnakin.isInsideDeck();
    }

    @Override
    public boolean isReviewingDeck() {
        return versionedAnakin.isReviewingDeck();
    }

    @Override
    public void startReview() {
        versionedAnakin.startReview();
        indicateAnakinChanged();
    }

    @Override
    public void endReview() {
        versionedAnakin.endReview();
        indicateAnakinChanged();
    }

    @Override
    public int getIndexOfCurrentCard() {
        return versionedAnakin.getIndexOfCurrentCard();
    }

    @Override
    public void setIndexOfCurrentCard(int newIndex) {
        versionedAnakin.setIndexOfCurrentCard(newIndex);
        indicateAnakinChanged();
    }

    @Override
    public String exportDeck(Deck deck) {
        return versionedAnakin.exportDeck(deck);
    }

    @Override
    public Deck importDeck (String filepath) {
        Deck imported = versionedAnakin.importDeck(filepath);
        addDeck(imported);
        return imported;
    }
    //=========== Filtered Deck List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Deck} backed by the internal list of
     * {@code versionedAnakin}
     */
    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return FXCollections.unmodifiableObservableList(filteredDecks);
    }

    @Override
    public void updateFilteredDeckList(Predicate<Deck> predicate) {
        requireAllNonNull(predicate);
        filteredDecks.setPredicate(predicate);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code currentDeck}
     */
    @Override
    public ObservableList<Card> getFilteredCardList() {
        return FXCollections.unmodifiableObservableList(filteredCards);
    }

    @Override
    public void updateFilteredCardList(Predicate<Card> predicate) {
        requireAllNonNull(predicate);
        filteredCards.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAnakin() {
        return versionedAnakin.canUndo();
    }

    @Override
    public boolean canRedoAnakin() {
        return versionedAnakin.canRedo();
    }

    @Override
    public String undoAnakin() {
        String undoCommand = versionedAnakin.undo();
        indicateAnakinChanged();
        return undoCommand;
    }

    @Override
    public String redoAnakin() {
        String redoCommand = versionedAnakin.redo();
        indicateAnakinChanged();
        return redoCommand;
    }

    @Override
    public void commitAnakin(String command) {
        versionedAnakin.commit(command);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAnakin.equals(other.versionedAnakin)
            && filteredDecks.equals(other.filteredDecks);
    }
}
