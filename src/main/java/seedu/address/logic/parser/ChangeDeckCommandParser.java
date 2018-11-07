package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeDeckCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ChangeDeckCommandParser implements ParserInterface<ChangeDeckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeDeckCommand
     * and returns an ChangeDeckCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public ChangeDeckCommand parse(String args) throws ParseException {


        try {
            if (args.equals(ChangeDeckCommand.EXIT_DECK_ARGS)) {
                return new ChangeDeckCommand();
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new ChangeDeckCommand(index);
            }


        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeDeckCommand.MESSAGE_USAGE), pe);
        }
    }

}
