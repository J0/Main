package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CURRENTLY_REVIEWING_DECK;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sort the list of deck in lexicographical order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS_1 = "decks are sorted in alphabetical order";
    public static final String MESSAGE_SUCCESS_2 = "cards are sorted in alphabetical order";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_CURRENTLY_REVIEWING_DECK);
        }

        model.sort();
        model.commitAnakin(COMMAND_WORD);
        if (model.isInsideDeck()) {
            return new CommandResult(MESSAGE_SUCCESS_2);
        } else {
            return new CommandResult(MESSAGE_SUCCESS_1);
        }
    }
}
