package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CURRENTLY_REVIEWING_DECK;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.CardQuestionContainsKeywordsPredicate;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;

/**
 * Finds and lists all decks or cards in the current list, which has identity field contains
 * any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all decks / cards in the current list,"
            + " which has identity field contains any of the argument keywords.\n"
            + "The specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Donald Duck";
    public static final String DEFAULT_STRING = "Donald Duck";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + DEFAULT_STRING;

    private final DeckNameContainsKeywordsPredicate deckPredicate;
    private final CardQuestionContainsKeywordsPredicate cardPredicate;



    // Constructor to be called by FindCommandParser
    public FindCommand(DeckNameContainsKeywordsPredicate deckPredicate,
                       CardQuestionContainsKeywordsPredicate cardPredicate) {
        this.deckPredicate = deckPredicate;
        this.cardPredicate = cardPredicate;
    }

    public FindCommand(DeckNameContainsKeywordsPredicate deckPredicate) {
        this.deckPredicate = deckPredicate;
        this.cardPredicate = null;
    }

    public FindCommand(CardQuestionContainsKeywordsPredicate cardPredicate) {
        this.cardPredicate = cardPredicate;
        this.deckPredicate = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_CURRENTLY_REVIEWING_DECK);
        }

        if (model.isInsideDeck()) {
            model.updateFilteredCardList(cardPredicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_CARDS_LISTED_OVERVIEW, model.getFilteredCardList().size()));
        } else {
            model.updateFilteredDeckList(deckPredicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_DECKS_LISTED_OVERVIEW, model.getFilteredDeckList().size()));
        }
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof FindCommand)) {
            return false;
        }

        if (deckPredicate == null) {
            return cardPredicate.equals(((FindCommand) other).cardPredicate);
        }

        if (cardPredicate == null) {
            return deckPredicate.equals(((FindCommand) other).deckPredicate);
        }

        return deckPredicate.equals(((FindCommand) other).deckPredicate)
                && cardPredicate.equals(((FindCommand) other).cardPredicate);
    }
}
