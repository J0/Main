package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Anakin;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.AnakinBuilder;

public class PerformanceSortCommandTest {

    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_rankCardList() throws Exception {
        Deck expectedDeck = getTypicalDeck();
        expectedDeck.getCards().perfsort();
        List<Card> expectedCardList = expectedDeck.getCards().asUnmodifiableObservableList();

        Deck deck = getTypicalDeck();
        Anakin anakin = new AnakinBuilder().withDeck(deck).build();
        anakin.getIntoDeck(deck);
        ModelManager model = new ModelManager(anakin, new UserPrefs());

        PerformanceSortCommand command = new PerformanceSortCommand();
        command.execute(model, commandHistory);
        List<Card> cardList = model.getFilteredCardList();

        assertEquals(expectedCardList, cardList);
    }
}
