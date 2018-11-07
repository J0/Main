package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.CARD_A;
import static seedu.address.testutil.TypicalCards.CARD_B;

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
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAnakin;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;


public class NewCardCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model testModel = new ModelManager();

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new NewCardCommand(null);
    }

    @Test
    public void execute_cardAcceptedByModel_success() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();
        Card validCard = CARD_A;

        CommandResult commandResult = new NewCardCommand(validCard).execute(modelStub, commandHistory);

        assertEquals(String.format(NewCardCommand.MESSAGE_NEW_CARD_SUCCESS, validCard), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCard), modelStub.cardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() throws Exception {
        Card validCard = CARD_B;
        NewCardCommand newCardCommand = new NewCardCommand(validCard);
        ModelStub modelStub = new ModelStubWithCard(validCard);

        thrown.expect(CommandException.class);
        thrown.expectMessage(NewCardCommand.MESSAGE_DUPLICATE_CARD);
        newCardCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_validCardButNotInDeck_throwsRuntimeException() throws Exception {
        Card validCard = CARD_B;
        NewCardCommand newCardCommand = new NewCardCommand(validCard);
        Model model = testModel;
        thrown.expect(CommandException.class);
        newCardCommand.execute(model, commandHistory);

    }

    // Integrated test
    @Test
    public void execute_validCardInDeck_success() throws Exception {
        Card validCard = CARD_B;
        NewCardCommand newCardCommand = new NewCardCommand(validCard);
        Deck validDeck = new DeckBuilder().withName("Deck with Card B").build();

        Model model = testModel;
        model.getIntoDeck(validDeck);

        CommandResult commandResult = newCardCommand.execute(model, commandHistory);

        assertEquals(String.format(
            NewCardCommand.MESSAGE_NEW_CARD_SUCCESS,
            validCard), commandResult.feedbackToUser);

    }


    @Test
    public void equals() {
        Card firstCard = new CardBuilder().withQuestion("Test Card1").withAnswer("A1").build();
        Card secondCard = new CardBuilder().withQuestion("Test Card2").withAnswer("A2").build();
        NewCardCommand addFirstCardCommand = new NewCardCommand(firstCard);
        NewCardCommand addSecondCardCommand = new NewCardCommand(secondCard);

        // same object -> returns true
        assertTrue(addFirstCardCommand.equals(addFirstCardCommand));

        // same values -> returns true
        NewCardCommand addFirstCardCommandCopy = new NewCardCommand(firstCard);
        assertTrue(addFirstCardCommand.equals(addFirstCardCommandCopy));

        // different types -> returns false
        assertFalse(addFirstCardCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstCardCommand.equals(null));

        // different card -> returns false
        assertFalse(addFirstCardCommand.equals(addSecondCardCommand));
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
            return true;
        }

        @Override
        public String exportDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck importDeck(String filepath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sort() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithCard extends ModelStub {
        private final Card card;

        ModelStubWithCard(Card card) {
            requireNonNull(card);
            this.card = card;
        }

        @Override
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return this.card.isSameCard(card);
        }

        @Override
        public boolean isReviewingDeck() {
            return false;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingCardAdded extends ModelStub {
        final ArrayList<Card> cardsAdded = new ArrayList<>();

        @Override
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return cardsAdded.stream().anyMatch(card::isSameCard);
        }

        @Override
        public void addCard(Card card) {
            requireNonNull(card);
            cardsAdded.add(card);
        }

        @Override
        public boolean isReviewingDeck() {
            return false;
        }

        @Override
        public void commitAnakin(String command) {
            // called by {@code NewCardCommand#execute()}
        }

        @Override
        public ReadOnlyAnakin getAnakin() {
            return new Anakin();
        }
    }

}
