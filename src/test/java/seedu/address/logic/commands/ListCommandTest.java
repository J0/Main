package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeckAtIndex;
import static seedu.address.logic.commands.ListCommand.MESSAGE_SUCCESS_PERFORMANCE_CARDS;
import static seedu.address.testutil.TypicalCards.CARD_EASY;
import static seedu.address.testutil.TypicalCards.CARD_HARD;
import static seedu.address.testutil.TypicalDecks.DECK_WITH_CARDS;
import static seedu.address.testutil.TypicalDecks.THERE;
import static seedu.address.testutil.TypicalDecks.getTypicalAnakin;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.CardPerformanceMatchesPerformancesPredicate;
import seedu.address.model.deck.Performance;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAnakin(), new UserPrefs());
        expectedModel = new ModelManager(model.getAnakin(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_DECK, expectedModel);

        model.getIntoDeck(DECK_WITH_CARDS);
        expectedModel.getIntoDeck(DECK_WITH_CARDS);
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_ALL_CARDS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_DECK, expectedModel);

        model.getIntoDeck(DECK_WITH_CARDS);
        expectedModel.getIntoDeck(DECK_WITH_CARDS);
        //showCardAtIndexOfCurrentDeck(model, INDEX_FIRST_CARD);
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS_ALL_CARDS, expectedModel);
    }

    @Test
    public void execute_hard_showsHard() {
        model.getIntoDeck(THERE);
        expectedModel.getIntoDeck(THERE);
        String expectedMessage = String.format(MESSAGE_SUCCESS_PERFORMANCE_CARDS, "hard");
        CardPerformanceMatchesPerformancesPredicate hardPredicate = preparePerformancePredicate(Set.of(Performance
                .HARD));
        ListCommand command = new ListCommand(hardPredicate);
        expectedModel.updateFilteredCardList(hardPredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARD_HARD), model.getFilteredCardList());
    }

    @Test
    public void execute_easyHard_showsEasyHard() {
        model.getIntoDeck(THERE);
        expectedModel.getIntoDeck(THERE);
        CardPerformanceMatchesPerformancesPredicate easyHardPredicate = preparePerformancePredicate(Set.of(Performance
                .HARD, Performance.EASY));
        String displayPerformanceStrings = convertPerformanceStringsForDisplay(easyHardPredicate
                .performancesAsStrings());
        String expectedMessage = String.format(MESSAGE_SUCCESS_PERFORMANCE_CARDS, displayPerformanceStrings);
        ListCommand command = new ListCommand(easyHardPredicate);
        expectedModel.updateFilteredCardList(easyHardPredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        Set<Card> expectedCards = Set.of(CARD_EASY, CARD_HARD);
        Set<Card> actualCards = new HashSet<>(model.getFilteredCardList());
        assertEquals(expectedCards.size(), actualCards.size());
        assertTrue(expectedCards.containsAll(actualCards));
    }

    private CardPerformanceMatchesPerformancesPredicate preparePerformancePredicate(Set<Performance> performances) {
        return new CardPerformanceMatchesPerformancesPredicate(performances);
    }

    public String convertPerformanceStringsForDisplay(List<String> performanceStrings) {
        return performanceStrings.stream().collect(Collectors.joining(", ")).toLowerCase();
    }
}
