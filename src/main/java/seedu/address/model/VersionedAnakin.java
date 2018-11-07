package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Anakin} that keeps track of its own history.
 */
public class VersionedAnakin extends Anakin {

    private final List<ReadOnlyAnakin> anakinStateList;
    private int currentStatePointer;

    // The command that causes Anakin's state to be changed
    private String lastCommand;

    public VersionedAnakin(ReadOnlyAnakin initialState) {
        super(initialState);

        anakinStateList = new ArrayList<>();
        anakinStateList.add(new Anakin(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Anakin} state at the end of the state list.
     * Undone states are removed from the state list.
     * @param command
     */
    public void commit(String command) {
        lastCommand = command;
        removeStatesAfterCurrentPointer();
        anakinStateList.add(new Anakin(this));
        currentStatePointer++;
    }

    @Override
    public String getLastCommand() {
        return lastCommand;
    }

    private void removeStatesAfterCurrentPointer() {
        anakinStateList.subList(currentStatePointer + 1, anakinStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public String undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        String undoCommand = anakinStateList.get(currentStatePointer).getLastCommand();
        currentStatePointer--;
        resetData(anakinStateList.get(currentStatePointer));
        updateDisplayedCards();
        return undoCommand;
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public String redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(anakinStateList.get(currentStatePointer));
        updateDisplayedCards();
        return anakinStateList.get(currentStatePointer).getLastCommand();
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < anakinStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAnakin)) {
            return false;
        }

        VersionedAnakin otherVersionedAnakin = (VersionedAnakin) other;

        // state check
        return super.equals(otherVersionedAnakin)
            && anakinStateList.equals(otherVersionedAnakin.anakinStateList)
            && currentStatePointer == otherVersionedAnakin.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of anakinState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of anakinState list, unable to redo.");
        }
    }
}
