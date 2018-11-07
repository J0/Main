package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_REVIEWING_DECK;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ReviewPreviousCardEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;

/**
 * Moves to previous card during deck review.
 */
public class PreviousCardCommand extends Command {

    public static final String COMMAND_WORD = "prevcard";
    public static final String MESSAGE_SUCCESS = "Moving to previous question";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_NOT_REVIEWING_DECK);
        }

        ObservableList<Card> cardList = model.getFilteredCardList();
        int newIndex = model.getIndexOfCurrentCard() - 1;
        if (newIndex < 0) {
            newIndex = cardList.size() - 1;
        }
        Card prevCard = cardList.get(newIndex);

        EventsCenter.getInstance().post(new ReviewPreviousCardEvent(prevCard));
        model.setIndexOfCurrentCard(newIndex);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
