package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CURRENTLY_REVIEWING_DECK;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CARD_LEVEL_OPERATION;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;


/**
 * Edits the details of an existing card in a deck.
 */
public class DeleteCardCommand extends Command {

    public static final String COMMAND_WORD = "delcard";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the card identified by the index number in the current deck.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CARD_SUCCESS = "Deleted card: %1$s";
    public static final String DEFAULT_INDEX = "1";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + DEFAULT_INDEX;


    private final Index targetIndex;

    /**
     * Creates a delcard command to delete the {@code Card} at the specified index
     *
     * @param targetIndex
     */
    public DeleteCardCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isInsideDeck()) {
            throw new CommandException(MESSAGE_INVALID_CARD_LEVEL_OPERATION);
        }

        if (model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_CURRENTLY_REVIEWING_DECK);
        }

        List<Card> currentCardList = model.getFilteredCardList();
        if (targetIndex.getZeroBased() >= currentCardList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }
        Card cardToDelete = currentCardList.get(targetIndex.getZeroBased());
        model.deleteCard(cardToDelete);
        model.commitAnakin(COMMAND_WORD);

        return new CommandResult(String.format(MESSAGE_DELETE_CARD_SUCCESS, cardToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteCardCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteCardCommand) other).targetIndex));
    }
}
