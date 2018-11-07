package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CURRENTLY_REVIEWING_DECK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success: ";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_CURRENTLY_REVIEWING_DECK);
        }

        if (!model.canRedoAnakin()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String redoCommand = model.redoAnakin();
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        model.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);

        return new CommandResult(MESSAGE_SUCCESS + redoCommand);
    }
}
