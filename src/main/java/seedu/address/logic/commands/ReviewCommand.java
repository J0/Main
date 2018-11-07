package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.StartReviewRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * Starts a review for a deck.
 */
public class ReviewCommand extends Command {

    public static final String COMMAND_WORD = "review";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reviews a deck inside Anakin.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Deck review started: %1$s";
    public static final String MESSAGE_DECK_NO_CARDS = "Cannot review deck with no cards";
    public static final String MESSAGE_ALREADY_REVIEWING_DECK = "End current deck review before reviewing another deck";
    public static final String DEFAULT_INDEX = "1";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + DEFAULT_INDEX;


    private final Index index;

    /**
     * Creates a ReviewCommand to play a selected deck.
     * @param index of the deck in the filtered deck list to review
     */
    public ReviewCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_ALREADY_REVIEWING_DECK);
        }

        List<Deck> currentDeckList = model.getFilteredDeckList();

        if (index.getZeroBased() >= currentDeckList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Deck targetDeck = currentDeckList.get(index.getZeroBased());
        model.getIntoDeck(targetDeck);

        ObservableList<Card> cardList = model.getFilteredCardList();
        if (cardList.size() == 0) {
            throw new CommandException(MESSAGE_DECK_NO_CARDS);
        }

        model.startReview();
        Card cardToShow = cardList.get(model.getIndexOfCurrentCard());
        EventsCenter.getInstance().post(new StartReviewRequestEvent(cardToShow));
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetDeck));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReviewCommand // instanceof handles nulls
                && this.index.equals(((ReviewCommand) other).index));
    }
}
