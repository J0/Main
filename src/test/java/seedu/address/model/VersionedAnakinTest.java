package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.DECK_B;
import static seedu.address.testutil.TypicalDecks.DECK_C;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.AnakinBuilder;

public class VersionedAnakinTest {

    private static final String DEFAULT_COMMIT_MESSAGE = "Default commit message";

    private final ReadOnlyAnakin anakinWithDeckA = new AnakinBuilder().withDeck(DECK_A).build();
    private final ReadOnlyAnakin anakinWithDeckB = new AnakinBuilder().withDeck(DECK_B).build();
    private final ReadOnlyAnakin anakinWithDeckC = new AnakinBuilder().withDeck(DECK_C).build();
    private final ReadOnlyAnakin emptyAnakin = new AnakinBuilder().build();

    @Test
    public void commit_singleAnakin_noStatesRemovedCurrentStateSaved() {
        VersionedAnakin versionedAnakin = prepareAnakinList(emptyAnakin);

        versionedAnakin.commit(DEFAULT_COMMIT_MESSAGE);
        assertAnakinListStatus(versionedAnakin,
            Collections.singletonList(emptyAnakin),
            emptyAnakin,
            Collections.emptyList());
    }

    @Test
    public void commit_multipleAnakinPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);

        versionedAnakin.commit(DEFAULT_COMMIT_MESSAGE);
        assertAnakinListStatus(versionedAnakin,
            Arrays.asList(emptyAnakin, anakinWithDeckA, anakinWithDeckB),
            anakinWithDeckB,
            Collections.emptyList());
    }

    @Test
    public void commit_multipleAnakinPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 2);

        versionedAnakin.commit(DEFAULT_COMMIT_MESSAGE);
        assertAnakinListStatus(versionedAnakin,
            Collections.singletonList(emptyAnakin),
            emptyAnakin,
            Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAnakinPointerAtEndOfStateList_returnsTrue() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);

        assertTrue(versionedAnakin.canUndo());
    }

    @Test
    public void canUndo_multipleAnakinPointerAtStartOfStateList_returnsTrue() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 1);

        assertTrue(versionedAnakin.canUndo());
    }

    @Test
    public void canUndo_singleAnakin_returnsFalse() {
        VersionedAnakin versionedAnakin = prepareAnakinList(emptyAnakin);

        assertFalse(versionedAnakin.canUndo());
    }

    @Test
    public void canUndo_multipleAnakinPointerAtStartOfStateList_returnsFalse() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 2);

        assertFalse(versionedAnakin.canUndo());
    }

    @Test
    public void canRedo_multipleAnakinPointerNotAtEndOfStateList_returnsTrue() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 1);

        assertTrue(versionedAnakin.canRedo());
    }

    @Test
    public void canRedo_multipleAnakinPointerAtStartOfStateList_returnsTrue() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 2);

        assertTrue(versionedAnakin.canRedo());
    }

    @Test
    public void canRedo_singleAnakin_returnsFalse() {
        VersionedAnakin versionedAnakin = prepareAnakinList(emptyAnakin);

        assertFalse(versionedAnakin.canRedo());
    }

    @Test
    public void canRedo_multipleAnakinPointerAtEndOfStateList_returnsFalse() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);

        assertFalse(versionedAnakin.canRedo());
    }

    @Test
    public void undo_multipleAnakinPointerAtEndOfStateList_success() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);

        versionedAnakin.undo();
        assertAnakinListStatus(versionedAnakin,
            Collections.singletonList(emptyAnakin),
            anakinWithDeckA,
            Collections.singletonList(anakinWithDeckB));
    }

    @Test
    public void undo_multipleAnakinPointerNotAtStartOfStateList_success() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 1);

        versionedAnakin.undo();
        assertAnakinListStatus(versionedAnakin,
            Collections.emptyList(),
            emptyAnakin,
            Arrays.asList(anakinWithDeckA, anakinWithDeckB));
    }

    @Test
    public void undo_singleAnakin_throwsNoUndoableStateException() {
        VersionedAnakin versionedAnakin = prepareAnakinList(emptyAnakin);

        assertThrows(VersionedAnakin.NoUndoableStateException.class, versionedAnakin::undo);
    }

    @Test
    public void undo_multipleAnakinPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 2);

        assertThrows(VersionedAnakin.NoUndoableStateException.class, versionedAnakin::undo);
    }

    @Test
    public void redo_multipleAnakinPointerNotAtEndOfStateList_success() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 1);

        versionedAnakin.redo();
        assertAnakinListStatus(versionedAnakin,
            Arrays.asList(emptyAnakin, anakinWithDeckA),
            anakinWithDeckB,
            Collections.emptyList());
    }

    @Test
    public void redo_multipleAnakinPointerAtStartOfStateList_success() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 2);

        versionedAnakin.redo();
        assertAnakinListStatus(versionedAnakin,
            Collections.singletonList(emptyAnakin),
            anakinWithDeckA,
            Collections.singletonList(anakinWithDeckB));
    }

    @Test
    public void redo_singleAnakin_throwsNoRedoableStateException() {
        VersionedAnakin versionedAnakin = prepareAnakinList(emptyAnakin);

        assertThrows(VersionedAnakin.NoRedoableStateException.class, versionedAnakin::redo);
    }

    @Test
    public void redo_multipleAnakinPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedAnakin versionedAnakin = prepareAnakinList(
            emptyAnakin, anakinWithDeckA, anakinWithDeckB);

        assertThrows(VersionedAnakin.NoRedoableStateException.class, versionedAnakin::redo);
    }

    @Test
    public void equals() {
        VersionedAnakin versionedAnakin = prepareAnakinList(anakinWithDeckA, anakinWithDeckB);

        // same values -> returns true
        VersionedAnakin copy = prepareAnakinList(anakinWithDeckA, anakinWithDeckB);
        assertTrue(versionedAnakin.equals(copy));

        // same object -> returns true
        assertTrue(versionedAnakin.equals(versionedAnakin));

        // null -> returns false
        assertFalse(versionedAnakin.equals(null));

        // different types -> returns false
        assertFalse(versionedAnakin.equals(1));

        // different state list -> returns false
        VersionedAnakin differentAnakinList = prepareAnakinList(anakinWithDeckB, anakinWithDeckC);
        assertFalse(versionedAnakin.equals(differentAnakinList));

        // different current pointer index -> returns false
        VersionedAnakin differentCurrentStatePointer = prepareAnakinList(
            anakinWithDeckA, anakinWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedAnakin, 1);
        assertFalse(versionedAnakin.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedAnakin} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAnakin#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAnakin#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAnakinListStatus(VersionedAnakin versionedAnakin,
        List<ReadOnlyAnakin> expectedStatesBeforePointer,
        ReadOnlyAnakin expectedCurrentState,
        List<ReadOnlyAnakin> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new Anakin(versionedAnakin), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAnakin.canUndo()) {
            versionedAnakin.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyAnakin expectedAnakin : expectedStatesBeforePointer) {
            assertEquals(expectedAnakin, new Anakin(versionedAnakin));
            versionedAnakin.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyAnakin expectedAnakin : expectedStatesAfterPointer) {
            versionedAnakin.redo();
            assertEquals(expectedAnakin, new Anakin(versionedAnakin));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAnakin.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedAnakin.undo());
    }

    /**
     * Creates and returns a {@code VersionedAnakin} with the {@code anakinStates} added into it, and the
     * {@code VersionedAnakin#currentStatePointer} at the end of list.
     */
    private VersionedAnakin prepareAnakinList(ReadOnlyAnakin... anakinStates) {
        assertFalse(anakinStates.length == 0);

        VersionedAnakin versionedAnakin = new VersionedAnakin(anakinStates[0]);
        for (int i = 1; i < anakinStates.length; i++) {
            versionedAnakin.resetData(anakinStates[i]);
            versionedAnakin.commit(DEFAULT_COMMIT_MESSAGE);
        }

        return versionedAnakin;
    }

    /**
     * Shifts the {@code versionedAnakin#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedAnakin versionedAnakin, int count) {
        for (int i = 0; i < count; i++) {
            versionedAnakin.undo();
        }
    }
}
