package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARD_LIST;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.anakinexceptions.DuplicateDeckException;
import seedu.address.testutil.DeckBuilder;

public class AnakinTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Anakin anakin = new Anakin();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), anakin.getDeckList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        anakin.resetData(null);
    }

    @Test
    public void resetData_withValidAnakinReadOnlyAnakin_replacesData() {
        Anakin newData = getTypicalAnakin();
        anakin.resetData(newData);
        assertEquals(newData, anakin);
    }

    @Test
    public void resetData_withDuplicateDecks_throwsDuplicateDeckException() {
        // Two decks with the same identity field
        Deck editedDeckA = new DeckBuilder(DECK_A).withCards(VALID_CARD_LIST).build();
        List<Deck> newDecks = Arrays.asList(DECK_A, editedDeckA);
        AnakinStub newData = new AnakinStub(newDecks);

        thrown.expect(DuplicateDeckException.class);
        anakin.resetData(newData);
    }

    @Test
    public void hasDeck_nullDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        anakin.hasDeck(null);
    }

    @Test
    public void hasDeck_deckNotInAnakin_returnsFalse() {
        assertFalse(anakin.hasDeck(DECK_A));
    }

    @Test
    public void hasDeck_deckInAnakin_returnsTrue() {
        anakin.addDeck(DECK_A);
        assertTrue(anakin.hasDeck(DECK_A));
    }

    @Test
    public void hasDeck_deckWithSameIdentityFieldsInAnakin_returnsTrue() {
        anakin.addDeck(DECK_A);
        Deck editedDeckA = new DeckBuilder(DECK_A).withCards(VALID_CARD_LIST).build();
        assertTrue(anakin.hasDeck(editedDeckA));
    }

    @Test
    public void getDeckList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        anakin.getDeckList().remove(0);
    }

    /**
     * A stub ReadOnlyAnakin whose decks list can violate interface constraints.
     */
    private static class AnakinStub implements ReadOnlyAnakin {
        private final ObservableList<Deck> decks = FXCollections.observableArrayList();
        private final ObservableList<Card> cards = FXCollections.observableArrayList();

        AnakinStub(Collection<Deck> decks) {
            this.decks.setAll(decks);
        }

        @Override
        public ObservableList<Deck> getDeckList() {
            return decks;
        }

        @Override
        public ObservableList<Card> getCardList() {
            return cards;
        }

        @Override
        public boolean isInsideDeck() {
            return false;
        }

        @Override
        public boolean isReviewingDeck() {
            return false;
        }

        @Override
        public String getLastCommand() {
            return null;
        }
    }
}
