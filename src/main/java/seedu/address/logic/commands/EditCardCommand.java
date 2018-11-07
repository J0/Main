package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CURRENTLY_REVIEWING_DECK;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CARD_LEVEL_OPERATION;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_INSIDE_DECK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Answer;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Question;
import seedu.address.model.deck.anakinexceptions.DeckNotFoundException;

/**
 * Edits the details of an existing card in a deck.
 */
public class EditCardCommand extends Command {

    public static final String COMMAND_WORD = "editcard";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the card "
        + "by the index number used in the deck. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_QUESTION + "QUESTION] "
        + "[" + PREFIX_ANSWER + "ANSWER]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_QUESTION + "Why is Earth round?";

    public static final String MESSAGE_EDIT_CARD_SUCCESS = "Edited Card: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the deck.";
    public static final String DEFAULT_INDEX = "1";
    public static final String DEFAULT_QUESTION = "Why is Earth round?";


    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + DEFAULT_INDEX + " "
            + PREFIX_QUESTION + DEFAULT_QUESTION;


    private final Index index;
    private final EditCardDescriptor editCardDescriptor;

    /**
     * @param index of the card in the deck to edit
     * @param editCardDescriptor details to edit the card with
     */
    public EditCardCommand(Index index, EditCardDescriptor editCardDescriptor) {
        requireNonNull(index);
        requireNonNull(editCardDescriptor);

        this.index = index;
        this.editCardDescriptor = new EditCardDescriptor(editCardDescriptor);
    }

    /**
     * Creates and returns a {@code Card} with the details of {@code cardToEdit}
     * edited with {@code editCardDescriptor}.
     */
    private static Card createEditedCard(Card cardToEdit, EditCardDescriptor editCardDescriptor) {
        assert cardToEdit != null;

        Question updatedQuestion = editCardDescriptor.getQuestion().orElse(cardToEdit.getQuestion());
        Answer updatedAnswer = editCardDescriptor.getAnswer().orElse(cardToEdit.getAnswer());

        return new Card(updatedQuestion, updatedAnswer, cardToEdit.getPerformance(), cardToEdit.getTimesReviewed());
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

        List<Card> lastShownList = model.getFilteredCardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        Card cardToEdit = lastShownList.get(index.getZeroBased());
        Card editedCard = createEditedCard(cardToEdit, editCardDescriptor);

        if (!cardToEdit.isSameCard(editedCard) && model.hasCard(editedCard)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        try {
            model.updateCard(cardToEdit, editedCard);
            model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
            model.commitAnakin(COMMAND_WORD);
        } catch (DeckNotFoundException e) {
            throw new CommandException(MESSAGE_NOT_INSIDE_DECK);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_CARD_SUCCESS, editedCard));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCardCommand)) {
            return false;
        }

        // state check
        EditCardCommand e = (EditCardCommand) other;
        return index.equals(e.index)
            && editCardDescriptor.equals(e.editCardDescriptor);
    }

    /**
     * Stores the details to edit the card with. Each non-empty field value will replace the
     * corresponding field value of the card.
     */
    public static class EditCardDescriptor {
        private Question question;
        private Answer answer;

        public EditCardDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditCardDescriptor(EditCardDescriptor toCopy) {
            setQuestion(toCopy.question);
            setAnswer(toCopy.answer);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, answer);
        }

        public Optional<Question> getQuestion() {
            return Optional.ofNullable(question);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCardDescriptor)) {
                return false;
            }

            // state check
            EditCardDescriptor e = (EditCardDescriptor) other;

            return getQuestion().equals(e.getQuestion())
                && getAnswer().equals(e.getAnswer());
        }
    }
}
