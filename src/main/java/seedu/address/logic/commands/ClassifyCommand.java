package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_REVIEWING_DECK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Performance;

/**
 * Classifies the currently reviewed card to a particular difficulty
 */
public class ClassifyCommand extends Command {
    public static final String COMMAND_WORD = "classify";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns to this card one of the four "
            + "difficulty classification: {easy, normal, hard}.\n"
            + "Existing difficulty classification will be overwritten by the input value.\n"
            + "Parameters: RATING (easy/normal/hard) "
            + "Example: " + COMMAND_WORD + " easy ";

    public static final String MESSAGE_CLASSIFICATION_SUCCESS = "Assigned %1$s difficulty to card %2$s";
    public static final String DEFAULT_DIFFICULTY = "easy";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + DEFAULT_DIFFICULTY;


    private final Performance difficulty;

    public ClassifyCommand(Performance difficulty) {
        requireNonNull(difficulty);
        this.difficulty = difficulty;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isReviewingDeck()) {
            throw new CommandException(MESSAGE_NOT_REVIEWING_DECK);
        }
        Card card = model.getFilteredCardList().get(model.getIndexOfCurrentCard());
        Card editedCard = Card.classifyCard(card, difficulty);
        model.updateCard(card, editedCard);
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        model.commitAnakin(COMMAND_WORD);

        return new CommandResult(String.format(MESSAGE_CLASSIFICATION_SUCCESS, this.difficulty, card));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ClassifyCommand // instanceof handles nulls
            && difficulty.equals(((ClassifyCommand) other).difficulty)); // state check
    }
}
