
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARD_A_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_A_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_A;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ChangeDeckCommand;
import seedu.address.logic.commands.ClassifyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.EditDeckCommand;
import seedu.address.logic.commands.EditDeckCommand.EditDeckDescriptor;
import seedu.address.logic.commands.EndReviewCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportDeckCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FlipCardCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportDeckCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewCardCommand;
import seedu.address.logic.commands.NewDeckCommand;
import seedu.address.logic.commands.NextCardCommand;
import seedu.address.logic.commands.PreviousCardCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;
import seedu.address.model.deck.Performance;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;
import seedu.address.testutil.EditCardDescriptorBuilder;
import seedu.address.testutil.EditDeckDescriptorBuilder;

public class ParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Parser parser = new Parser();

    @Test
    public void parseCommand_newDeck() throws Exception {
        Deck deck = new DeckBuilder().withName(VALID_NAME_DECK_A).build();
        NewDeckCommand command = (NewDeckCommand) parser
            .parseCommand(NewDeckCommand.COMMAND_WORD + VALID_DECK_NAME_A_ARGS);
        assertEquals(new NewDeckCommand(deck), command);
    }

    @Test
    public void parseCommand_editDeck() throws Exception {
        EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder().withName(VALID_NAME_DECK_A).build();
        EditDeckCommand command = (EditDeckCommand) parser.parseCommand(EditDeckCommand.COMMAND_WORD + " "
            + INDEX_FIRST_DECK.getOneBased() + " " + VALID_DECK_NAME_A_ARGS);
        assertEquals(new EditDeckCommand(INDEX_FIRST_DECK, descriptor), command);
    }

    @Test
    public void parseCommand_deleteDeck() throws Exception {
        DeleteDeckCommand command = (DeleteDeckCommand) parser.parseCommand(
            DeleteDeckCommand.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased());
        assertEquals(new DeleteDeckCommand(INDEX_FIRST_DECK), command);
    }


    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_newCard() throws Exception {
        Card card = new CardBuilder().withQuestion(VALID_QUESTION_A).withAnswer(VALID_ANSWER_A).build();
        NewCardCommand command = (NewCardCommand) parser.parseCommand(NewCardCommand.COMMAND_WORD + VALID_CARD_A_ARGS);
        assertEquals(new NewCardCommand(card), command);
    }

    @Test
    public void parseCommand_editCard() throws Exception {
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A)
            .withAnswer(VALID_ANSWER_A).build();
        EditCardCommand command = (EditCardCommand) parser.parseCommand(EditCardCommand.COMMAND_WORD + " "
            + INDEX_FIRST_CARD.getOneBased() + " " + VALID_CARD_A_ARGS);
        assertEquals(new EditCardCommand(INDEX_FIRST_CARD, descriptor), command);
    }


    @Test
    public void parseCommand_deleteCard() throws Exception {
        DeleteCardCommand command = (DeleteCardCommand) parser.parseCommand(
            DeleteCardCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased());
        assertEquals(new DeleteCardCommand(INDEX_FIRST_CARD), command);
    }


    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new DeckNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_changeDeck() throws Exception {
        ChangeDeckCommand command = (ChangeDeckCommand) parser.parseCommand(
            ChangeDeckCommand.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased());
        assertEquals(new ChangeDeckCommand(INDEX_FIRST_DECK), command);
    }

    @Test
    public void parseCommand_exportDeck() throws Exception {
        ExportDeckCommand command = (ExportDeckCommand) parser.parseCommand(
            ExportDeckCommand.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased());
        assertEquals(new ExportDeckCommand(INDEX_FIRST_DECK), command);
    }

    @Test
    public void parseCommand_importDeck() throws Exception {
        ImportDeckCommand command = (ImportDeckCommand) parser.parseCommand(
            ImportDeckCommand.COMMAND_WORD + " " + VALID_NAME_DECK_A);
        assertEquals(new ImportDeckCommand(VALID_NAME_DECK_A), command);
    }

    @Test
    public void parseCommand_changeDeckExit() throws Exception {
        ChangeDeckCommand command = (ChangeDeckCommand) parser.parseCommand(
            ChangeDeckCommand.COMMAND_WORD + ChangeDeckCommand.EXIT_DECK_ARGS);
        assertEquals(new ChangeDeckCommand(), command);
    }


    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_review() throws Exception {
        ReviewCommand command = (ReviewCommand) parser.parseCommand(
            ReviewCommand.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased());
        assertEquals(new ReviewCommand(INDEX_FIRST_DECK), command);
    }

    @Test
    public void parseCommand_endReview() throws Exception {
        EndReviewCommand command = (EndReviewCommand) parser.parseCommand(EndReviewCommand.COMMAND_WORD);
        assertTrue(command instanceof EndReviewCommand);
    }

    @Test
    public void parseCommand_nextCard() throws Exception {
        NextCardCommand command = (NextCardCommand) parser.parseCommand(NextCardCommand.COMMAND_WORD);
        assertTrue(command instanceof NextCardCommand);
    }

    @Test
    public void parseCommand_prevCard() throws Exception {
        PreviousCardCommand command = (PreviousCardCommand) parser.parseCommand(PreviousCardCommand.COMMAND_WORD);
        assertTrue(command instanceof PreviousCardCommand);
    }

    @Test
    public void parseCommand_flipCard() throws Exception {
        FlipCardCommand command = (FlipCardCommand) parser.parseCommand(FlipCardCommand.COMMAND_WORD);
        assertTrue(command instanceof FlipCardCommand);
    }

    @Test
    public void parseCommand_classify() throws Exception {
        ClassifyCommand command = (ClassifyCommand) parser
            .parseCommand(ClassifyCommand.COMMAND_WORD + " " + String.valueOf(Performance.EASY));
        assertEquals(new ClassifyCommand(Performance.EASY), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
