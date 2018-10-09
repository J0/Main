package seedu.address.logic.AnakinCommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.AnakinModel.PREDICATE_SHOW_ALL_DECKS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.AnakinMessages;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.AnakinModel;
import seedu.address.model.anakindeck.AnakinCard;
import seedu.address.model.anakindeck.AnakinDeck;
import seedu.address.model.anakindeck.Name;


/**
 * Edits the details of an existing deck in the address book.
 */
public class AnakinEditDeckCommand extends AnakinCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deck identified "
            + "by the index number used in the displayed deck list. "
            + "Changes its name to NAME.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME;

    public static final String MESSAGE_EDIT_DECK_SUCCESS = "Edited Deck: %1$s";
    public static final String MESSAGE_DECK_NOT_EDITED = "Index of Deck to edit and Name to edit to must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This deck already exists in the address book.";

    private final Index index;
    private final EditDeckDescriptor editDeckDescriptor;

    /**
     * @param index of the deck in the filtered deck list to edit
     * @param editDeckDescriptor details to edit the deck with
     */
    public AnakinEditDeckCommand(Index index, EditDeckDescriptor editDeckDescriptor) {
        requireNonNull(index);
        requireNonNull(editDeckDescriptor);

        this.index = index;
        this.editDeckDescriptor = new EditDeckDescriptor(editDeckDescriptor);
    }

    @Override
    public CommandResult execute(AnakinModel anakinModel, CommandHistory history) throws CommandException {
        requireNonNull(anakinModel);
        List<AnakinDeck> lastShownList = anakinModel.getFilteredDeckList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(AnakinMessages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        AnakinDeck deckToEdit = lastShownList.get(index.getZeroBased());
        AnakinDeck editedDeck = createEditedDeck(deckToEdit, editDeckDescriptor);

        if (!deckToEdit.isSameDeck(editedDeck) && anakinModel.hasDeck(editedDeck)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        anakinModel.updateDeck(deckToEdit, editedDeck);
        anakinModel.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        anakinModel.commitAnakin();
        return new CommandResult(String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck));
    }

    /**
     * Creates and returns a {@code Deck} with the details of {@code deckToEdit}
     * edited with {@code editDeckDescriptor}.
     */
    private static AnakinDeck createEditedDeck(AnakinDeck deckToEdit, EditDeckDescriptor editDeckDescriptor) {
        assert deckToEdit != null;

        Name updatedName = editDeckDescriptor.getName().orElse(deckToEdit.getName());
        //List<AnakinCard> updatedList = editDeckDescriptor.getCards().orElse(deckToEdit.getCards());

        return new AnakinDeck(updatedName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnakinEditDeckCommand)) {
            return false;
        }

        // state check
        AnakinEditDeckCommand e = (AnakinEditDeckCommand) other;
        return index.equals(e.index)
                && editDeckDescriptor.equals(e.editDeckDescriptor);
    }

    /**
     * Stores the details to edit the deck with. Each non-empty field value will replace the
     * corresponding field value of the deck.
     */
    public static class EditDeckDescriptor {
        private Name name;
        private List<AnakinCard> cards;

        public EditDeckDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDeckDescriptor(EditDeckDescriptor toCopy) {
            setName(toCopy.name);
            setCards(toCopy.cards);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, cards);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }


        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setCards(List<AnakinCard> cards) {
            this.cards = (cards != null) ? new ArrayList<>(cards) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<List<AnakinCard>> getCards() {
            return (cards != null) ? Optional.of(Collections.unmodifiableList(cards)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDeckDescriptor)) {
                return false;
            }

            // state check
            EditDeckDescriptor e = (EditDeckDescriptor) other;

            return getName().equals(e.getName())
                    && getCards().equals(e.getCards());
        }
    }
}
