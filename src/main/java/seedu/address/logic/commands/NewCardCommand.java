package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CURRENTLY_REVIEWING_DECK;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CARD_LEVEL_OPERATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;


/**
 * Edits the details of an existing card in a deck.
 */
public class NewCardCommand extends Command {

    public static final String COMMAND_WORD = "newcard";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new card to the current deck\n"
        + "Parameters: "
        + "[" + PREFIX_QUESTION + "QUESTION] "
        + "[" + PREFIX_ANSWER + "ANSWER]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_QUESTION + "What does Socrates know? "
        + PREFIX_ANSWER + "Nothing";

    public static final String MESSAGE_NEW_CARD_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the deck.";
    public static final String DEFAULT_QUESTION = "What does Socrates know?";
    public static final String DEFAULT_ANSWER = "Nothing";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PREFIX_QUESTION + " "
            + DEFAULT_QUESTION + " " + PREFIX_ANSWER + " " + DEFAULT_ANSWER;



    private final Card toAdd;

    /**
     * Creates a newcard command to add the specified {@code Card}
     *
     * @param card
     */
    public NewCardCommand(Card card) {
        requireNonNull(card);
        this.toAdd = card;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_CURRENTLY_REVIEWING_DECK);
        }

        if (!model.isInsideDeck()) {
            throw new CommandException(MESSAGE_INVALID_CARD_LEVEL_OPERATION);
        }

        if (model.hasCard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        model.addCard(toAdd);
        model.commitAnakin(COMMAND_WORD);
        return new CommandResult(String.format(MESSAGE_NEW_CARD_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NewCardCommand // instanceof handles nulls
            && toAdd.equals(((NewCardCommand) other).toAdd));
    }
}
