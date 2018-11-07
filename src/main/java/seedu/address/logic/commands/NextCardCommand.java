package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_REVIEWING_DECK;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ReviewNextCardEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;

/**
 * Advances to next card during deck review.
 */
public class NextCardCommand extends Command {

    public static final String COMMAND_WORD = "nextcard";
    public static final String MESSAGE_SUCCESS = "Moving to next question";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_NOT_REVIEWING_DECK);
        }

        ObservableList<Card> cardList = model.getFilteredCardList();
        int newIndex = model.getIndexOfCurrentCard() + 1;
        if (newIndex == cardList.size()) {
            newIndex = 0;
        }
        Card nextCard = cardList.get(newIndex);

        EventsCenter.getInstance().post(new ReviewNextCardEvent(nextCard));
        model.setIndexOfCurrentCard(newIndex);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
