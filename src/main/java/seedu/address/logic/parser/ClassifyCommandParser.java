package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClassifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Performance;

/**
 * Parses input arguments and create a ClassifyCommand object
 */
public class ClassifyCommandParser implements ParserInterface<ClassifyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClassifyCommand
     * and returns a ClassifyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClassifyCommand parse(String args) throws ParseException {
        try {
            Performance difficulty = ParserUtil.parsePerformance(args);
            return new ClassifyCommand(difficulty);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassifyCommand.MESSAGE_USAGE), pe);
        }
    }
}
