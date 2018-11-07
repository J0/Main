package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ChangeDeckCommand;
import seedu.address.logic.commands.ClassifyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.EditDeckCommand;
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
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case NewDeckCommand.COMMAND_WORD:
            return new NewDeckCommandParser().parse(arguments);

        case EditDeckCommand.COMMAND_WORD:
            return new EditDeckCommandParser().parse(arguments);

        case DeleteDeckCommand.COMMAND_WORD:
            return new DeleteDeckCommandParser().parse(arguments);

        case NewCardCommand.COMMAND_WORD:
            return new NewCardCommandParser().parse(arguments);

        case EditCardCommand.COMMAND_WORD:
            return new EditCardCommandParser().parse(arguments);

        case DeleteCardCommand.COMMAND_WORD:
            return new DeleteCardCommandParser().parse(arguments);

        case ChangeDeckCommand.COMMAND_WORD:
            return new ChangeDeckCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExportDeckCommand.COMMAND_WORD:
            return new ExportDeckCommandParser().parse(arguments);

        case ImportDeckCommand.COMMAND_WORD:
            return new ImportDeckCommandParser().parse(arguments);

        case ReviewCommand.COMMAND_WORD:
            return new ReviewCommandParser().parse(arguments);

        case EndReviewCommand.COMMAND_WORD:
            return new EndReviewCommand();

        case FlipCardCommand.COMMAND_WORD:
            return new FlipCardCommand();

        case NextCardCommand.COMMAND_WORD:
            return new NextCardCommand();

        case PreviousCardCommand.COMMAND_WORD:
            return new PreviousCardCommand();

        case ClassifyCommand.COMMAND_WORD:
            return new ClassifyCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
