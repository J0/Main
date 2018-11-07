package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CURRENTLY_REVIEWING_DECK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Lists all decks / cards in the current list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS_DECK = "Listed all decks";
    public static final String MESSAGE_SUCCESS_CARD = "Listed all cards";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;



    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_CURRENTLY_REVIEWING_DECK);
        }

        if (model.isInsideDeck()) {
            model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
            return new CommandResult(MESSAGE_SUCCESS_CARD);
        } else {
            model.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
            return new CommandResult(MESSAGE_SUCCESS_DECK);
        }
    }
}
