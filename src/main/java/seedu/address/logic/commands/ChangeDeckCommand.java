package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CURRENTLY_REVIEWING_DECK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

/**
 * Navigates into a deck identified using its displayed index from Anakin.
 */

public class ChangeDeckCommand extends Command {

    public static final String COMMAND_WORD = "cd";
    public static final String EXIT_DECK_ARGS = " ..";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Navigates into the deck identified by the index number used in the displayed deck list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CD_SUCCESS = "Successfully navigated into %1$s";
    public static final String MESSAGE_EXIT_SUCCESS = "Successfully exited deck";
    public static final String DEFAULT_INDEX = "1";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + DEFAULT_INDEX;


    private final Index targetIndex;

    private boolean isCdOut;

    public ChangeDeckCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.isCdOut = false;
    }

    /**
     * Alternate constructor for ChangeDeckCommand that does not require an Index
     */
    public ChangeDeckCommand() {
        //Set targetIndex as 0.
        this.targetIndex = Index.fromZeroBased(0);
        this.isCdOut = true;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Block `cd` when user is reviewing deck
        if (model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_CURRENTLY_REVIEWING_DECK);
        }

        List<Deck> currentDeckList = model.getFilteredDeckList();

        if (this.isCdOut) {
            if (!model.isInsideDeck()) {
                throw new CommandException(Messages.MESSAGE_NOT_INSIDE_DECK);
            }
            model.getOutOfDeck();
            model.commitAnakin(COMMAND_WORD + EXIT_DECK_ARGS);
            return new CommandResult(String.format(MESSAGE_EXIT_SUCCESS));
        } else {
            if (targetIndex.getZeroBased() >= currentDeckList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
            }

            Deck deckToEnter = currentDeckList.get(targetIndex.getZeroBased());
            model.getIntoDeck(deckToEnter);
            model.commitAnakin(COMMAND_WORD);
            return new CommandResult(String.format(MESSAGE_CD_SUCCESS, deckToEnter));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ChangeDeckCommand // instanceof handles nulls
            && targetIndex.equals(((ChangeDeckCommand) other).targetIndex)); // state check
    }
}

