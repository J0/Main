package seedu.address.logic.AnakinParser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.AnakinCommands.AnakinEditDeckCommand;
import seedu.address.logic.AnakinCommands.AnakinEditDeckCommand.EditDeckDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditDeck object
 */
public class AnakinEditDeckCommandParser implements AnakinParserInterface<AnakinEditDeckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDeckCommand
     * and returns an EditDeckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnakinEditDeckCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = AnakinParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AnakinEditDeckCommand.MESSAGE_USAGE), pe);
        }

        EditDeckDescriptor editDeckDescriptor = new EditDeckDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDeckDescriptor.setName(AnakinParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (!editDeckDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AnakinEditDeckCommand.MESSAGE_DECK_NOT_EDITED);
        }

        return new AnakinEditDeckCommand(index, editDeckDescriptor);
    }


}
