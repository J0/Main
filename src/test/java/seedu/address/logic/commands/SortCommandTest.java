package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.DECK_B;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Anakin;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.AnakinBuilder;

public class SortCommandTest {

    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortDeckList() throws Exception {
        Anakin expectedAnakin = new AnakinBuilder().withDeck(DECK_A).withDeck(DECK_B).build();
        List<Deck> expectedDeckList = expectedAnakin.getDeckList();
        ModelManager model = new ModelManager(new AnakinBuilder().withDeck(DECK_B).withDeck(DECK_A).build(),
            new UserPrefs());
        List<Deck> deckList = model.getAnakin().getDeckList();

        SortCommand command = new SortCommand();
        command.execute(model, commandHistory);
        assertEquals(expectedDeckList, deckList);
    }

    @Test
    public void execute_sortCardList() throws Exception {
        Deck expectedDeck = getTypicalDeck();
        expectedDeck.getCards().sort();
        List<Card> expectedCardList = expectedDeck.getCards().asUnmodifiableObservableList();

        Deck deck = getTypicalDeck();
        Anakin anakin = new AnakinBuilder().withDeck(deck).build();
        anakin.getIntoDeck(deck);
        ModelManager model = new ModelManager(anakin, new UserPrefs());

        SortCommand command = new SortCommand();
        command.execute(model, commandHistory);
        List<Card> cardList = model.getFilteredCardList();

        assertEquals(expectedCardList, cardList);
    }
}
