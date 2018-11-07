package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_REVIEWING_DECK;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.EndReviewRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Ends a review for a deck.
 */
public class EndReviewCommand extends Command {

    public static final String COMMAND_WORD = "endreview";
    public static final String MESSAGE_SUCCESS = "Deck review ended.";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_NOT_REVIEWING_DECK);
        }

        EventsCenter.getInstance().post(new EndReviewRequestEvent());
        model.endReview();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
