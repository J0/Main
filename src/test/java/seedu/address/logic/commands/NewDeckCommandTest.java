package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_DECK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Anakin;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAnakin;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.DeckBuilder;


public class NewDeckCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new NewDeckCommand(null);
    }

    @Test
    public void execute_deckAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDeckAdded modelStub = new ModelStubAcceptingDeckAdded();
        Deck validDeck = new DeckBuilder().build();

        CommandResult commandResult = new NewDeckCommand(validDeck).execute(modelStub, commandHistory);

        assertEquals(String.format(NewDeckCommand.MESSAGE_SUCCESS, validDeck), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validDeck), modelStub.decksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateDeck_throwsCommandException() throws Exception {
        Deck validDeck = new DeckBuilder().build();
        NewDeckCommand newDeckCommand = new NewDeckCommand(validDeck);
        ModelStub modelStub = new ModelStubWithDeck(validDeck);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_DECK);
        newDeckCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Deck firstDeck = new DeckBuilder().withName("Test Deck1").build();
        Deck secondDeck = new DeckBuilder().withName("Test Deck2").build();
        NewDeckCommand addFirstDeckCommand = new NewDeckCommand(firstDeck);
        NewDeckCommand addSecondDeckCommand = new NewDeckCommand(secondDeck);

        // same object -> returns true
        assertTrue(addFirstDeckCommand.equals(addFirstDeckCommand));

        // same values -> returns true
        NewDeckCommand addFirstDeckCommandCopy = new NewDeckCommand(firstDeck);
        assertTrue(addFirstDeckCommand.equals(addFirstDeckCommandCopy));

        // different types -> returns false
        assertFalse(addFirstDeckCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstDeckCommand.equals(null));

        // different person -> returns false
        assertFalse(addFirstDeckCommand.equals(addSecondDeckCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyAnakin anakin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAnakin getAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sort() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeck(Deck target, Deck newdeck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCard(Card target, Card newcard) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void getOutOfDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void getIntoDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isReviewingDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void startReview() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void endReview() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getIndexOfCurrentCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIndexOfCurrentCard(int newIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeckList(Predicate<Deck> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCardList(Predicate<Card> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean canUndoAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String undoAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String redoAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAnakin(String command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isInsideDeck() {
            return false;
        }

        @Override
        public String exportDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck importDeck(String filepath) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single deck.
     */
    private class ModelStubWithDeck extends ModelStub {
        private final Deck deck;

        ModelStubWithDeck(Deck deck) {
            requireNonNull(deck);
            this.deck = deck;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return this.deck.isSameDeck(deck);
        }

        @Override
        public boolean isReviewingDeck() {
            return false;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingDeckAdded extends ModelStub {
        final ArrayList<Deck> decksAdded = new ArrayList<>();

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return decksAdded.stream().anyMatch(deck::isSameDeck);
        }

        @Override
        public void addDeck(Deck deck) {
            requireNonNull(deck);
            decksAdded.add(deck);
        }

        @Override
        public boolean isReviewingDeck() {
            return false;
        }

        @Override
        public void commitAnakin(String command) {
            // called by {@code NewDeckCommand#execute()}
        }

        @Override
        public ReadOnlyAnakin getAnakin() {
            return new Anakin();
        }
    }

}
