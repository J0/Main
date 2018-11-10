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
        LOGGER.info("Deleted a deck.");
        versionedAnakin.removeDeck(deck);
        indicateAnakinChanged();
    }

    @Override
    public void addDeck(Deck deck) {
        LOGGER.info("Added a new deck to Anakin.");
        versionedAnakin.addDeck(deck);
        updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        indicateAnakinChanged();
    }

    @Override
    public void updateDeck(Deck target, Deck editedDeck) {
        requireAllNonNull(target, editedDeck);
        LOGGER.info("Updated a deck's name in Anakin.");
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
        LOGGER.info("Deleted a card in the current deck.");
        versionedAnakin.removeCard(card);
        indicateAnakinChanged();
    }

    @Override
    public void addCard(Card card) throws DeckNotFoundException {
        versionedAnakin.addCard(card);
        LOGGER.info("Added a card to the current deck.");
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        indicateAnakinChanged();
    }

    @Override
    public void updateCard(Card target, Card editedCard) throws DeckNotFoundException {
        requireAllNonNull(target, editedCard);
        versionedAnakin.updateCard(target, editedCard);
        LOGGER.info("Updated a card in the current deck.");
        indicateAnakinChanged();
    }

    @Override
    public void getIntoDeck(Deck deck) {
        requireAllNonNull(deck);
        LOGGER.info("Got into a deck.");
        versionedAnakin.getIntoDeck(deck);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        indicateAnakinChanged();
    }

    @Override
    public void getOutOfDeck() {
        LOGGER.info("Got out of the current deck, back to Anakin's deck list.");
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
        LOGGER.info("Starting reviewing...");
        versionedAnakin.startReview();
        indicateAnakinChanged();
    }

    @Override
    public void endReview() {
        LOGGER.info("Ended review mode.");
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
        LOGGER.info("Exported a deck.");
        return versionedAnakin.exportDeck(deck);
    }

    @Override
    public Deck importDeck (String filepath) {
        LOGGER.info("Imported a deck from xml file.");
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
        LOGGER.info("Undo Anakin, reset the application to the previous state.");
        String undoCommand = versionedAnakin.undo();
        indicateAnakinChanged();
        return undoCommand;
    }

    @Override
    public String redoAnakin() {
        LOGGER.info("Redo Anakin, reset the application to the state before the previous `undo` command.");
        String redoCommand = versionedAnakin.redo();
        indicateAnakinChanged();
        return redoCommand;
    }

    @Override
    public void commitAnakin(String command) {
        LOGGER.info("Committed Anakin");
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
