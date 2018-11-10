package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditDeckCommand.EditDeckDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Anakin;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.CardQuestionContainsKeywordsPredicate;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;
import seedu.address.model.deck.Performance;
import seedu.address.testutil.DeckBuilder;
import seedu.address.testutil.EditDeckDescriptorBuilder;
import seedu.address.testutil.TypicalCards;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME = "My Deck";
    public static final String INVALID_NAME = "B@d_Deck_Name";
    public static final String VALID_NAME_JOHN = "John Phua";

    public static final String VALID_NAME_DECK_A = "My Deck A";
    public static final String VALID_NAME_DECK_B = "Your deck";
    public static final Deck VALID_DECK_A = new DeckBuilder().withName(VALID_NAME_DECK_A).build();
    public static final Deck VALID_DECK_B = new DeckBuilder().withName(VALID_NAME_DECK_B).build();

    public static final String VALID_DECK_NAME_A_ARGS = " " + PREFIX_NAME + VALID_NAME_DECK_A;
    public static final String VALID_DECK_NAME_B_ARGS = " " + PREFIX_NAME + VALID_NAME_DECK_B;
    public static final String INVALID_DECK_NAME_ARGS = " " + PREFIX_NAME + " Bad_Deck_Name!";

    public static final String VALID_QUESTION_A = "Another valid stuff";
    public static final String VALID_QUESTION_B = "Need another valid question";
    public static final Performance VALID_PERFORMANCE_A = Performance.EASY;
    public static final String VALID_ANSWER_A = "Valid question needs a valid answer";
    public static final String VALID_ANSWER_B = "Whateverrrrrrr";
    public static final String INVALID_QUESTION = " ";
    public static final String INVALID_ANSWER = " ";


    public static final String VALID_CARD_A_ARGS = " " + PREFIX_QUESTION + VALID_QUESTION_A + " " + PREFIX_ANSWER
        + VALID_ANSWER_A;

    public static final String VALID_CARD_QUESTION_ARGS = " " + PREFIX_QUESTION + VALID_QUESTION_A;
    public static final String VALID_CARD_ANSWER_ARGS = " " + PREFIX_ANSWER + VALID_ANSWER_A;
    public static final String INVALID_CARD_QUESTION_ARGS = " " + PREFIX_QUESTION + INVALID_QUESTION;
    public static final String INVALID_CARD_ANSWER_ARGS = " " + PREFIX_ANSWER + INVALID_ANSWER;


    public static final List<Card> VALID_CARD_LIST = new ArrayList<>();
    public static final List<Card> TYPICAL_CARD_LIST = TypicalCards.getTypicalCards();


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final EditDeckDescriptor DESC_AMY;
    public static final EditDeckDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditDeckDescriptorBuilder().withName(VALID_NAME_DECK_A)
            .withCards(TYPICAL_CARD_LIST).build();
        DESC_BOB = new EditDeckDescriptorBuilder().withName(VALID_NAME_DECK_B)
            .withCards(TYPICAL_CARD_LIST).build();
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command,
        Model actualModel, CommandHistory actualCommandHistory,
        String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered deck list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command,
        Model actualModel, CommandHistory actualCommandHistory,
        String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Anakin expectedAnakin = new Anakin(actualModel.getAnakin());
        List<Deck> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDeckList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAnakin, actualModel.getAnakin());
            assertEquals(expectedFilteredList, actualModel.getFilteredDeckList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the deck at the given {@code targetIndex} in the
     * {@code model}'s Anakin.
     */

    public static void showDeckAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDeckList().size());

        Deck deck = model.getFilteredDeckList().get(targetIndex.getZeroBased());

        final String[] splitName = deck.getName().fullName.split("\\s+");
        model.updateFilteredDeckList(
            new DeckNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDeckList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the CARD at the given {@code targetIndex} in the
     * {@code model}'s current deck.
     */

    public static void showCardAtIndexOfCurrentDeck(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCardList().size());

        Card card = model.getFilteredCardList().get(targetIndex.getZeroBased());

        final String[] splitName = card.getQuestion().fullQuestion.split("\\s+");
        model.updateFilteredCardList(
                new CardQuestionContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCardList().size());
    }

    /**
     * Deletes the first deck in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstDeck(Model model) {
        Deck deck = model.getFilteredDeckList().get(0);
        model.deleteDeck(deck);
        model.commitAnakin(DeleteDeckCommand.COMMAND_WORD);
    }

}
