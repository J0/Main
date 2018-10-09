package seedu.address.logic.AnakinParser;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.AnakinCommands.AnakinEditDeckCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditDeck object
 */
public class AnakinEditDeckParser implements AnakinParserInterface<AnakinEditDeckCommand> {

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnakinEditDeckCommand.MESSAGE_USAGE), pe);
        }

        AnakinEditDeckDescriptor editDeckDescriptor = new AnakinEditDeckDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDeckDescriptor.setName(AnakinParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editDeckDescriptor::setTags);

        if (!editDeckDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AnakinEditDeckCommand.MESSAGE_NOT_EDITED);
        }

        return new AnakinEditDeckCommand(index, editDeckDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(AnakinParserUtil.parseIndex(tagSet));
    }

}
