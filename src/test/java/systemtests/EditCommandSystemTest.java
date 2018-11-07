package systemtests;

public class EditCommandSystemTest extends AnakinSystemTest {
//
//    @Test
//    public void edit() {
//        Model addressbookModel = getModel();
//
//        /* ----------------- Performing edit operation while an unfiltered list is being shown
// ---------------------- */
//
//        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
//         * -> edited
//         */
//        Index index = INDEX_FIRST_DECK;
//        String command = " " + EditDeck.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
//                + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
//        Person editedPerson = new DeckBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
//        assertCommandSuccess(command, index, editedPerson);
//
//        /* Case: undo editing the last deck in the list -> last deck restored */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, addressbookModel, expectedResultMessage);
//
//        /* Case: redo editing the last deck in the list -> last deck edited again */
//        command = RedoCommand.COMMAND_WORD;
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        addressbookModel.updateDeck(
//                getModel().getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased()), editedPerson);
//        assertCommandSuccess(command, addressbookModel, expectedResultMessage);
//
//        /* Case: edit a deck with new values same as existing values -> edited */
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB +
// EMAIL_DESC_BOB
//                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
//        assertCommandSuccess(command, index, BOB);
//
//        /* Case: edit a deck with new values same as another deck's values but with different name -> edited */
//        assertTrue(getModel().getAnakin().getDeckList().contains(BOB));
//        index = INDEX_SECOND_DECK;
//        assertNotEquals(getModel().getFilteredDeckList().get(index.getZeroBased()), BOB);
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB +
// EMAIL_DESC_BOB
//                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
//        editedPerson = new DeckBuilder(BOB).withName(VALID_NAME_AMY).build();
//        assertCommandSuccess(command, index, editedPerson);
//
//        /* Case: edit a deck with new values same as another deck's values but with different phone and email
//         * -> edited
//         */
//        index = INDEX_SECOND_DECK;
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY +
// EMAIL_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
//        editedPerson = new DeckBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
// .build();
//        assertCommandSuccess(command, index, editedPerson);
//
//        /* Case: clear tags -> cleared */
//        index = INDEX_FIRST_DECK;
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
//        Person deckToEdit = getModel().getFilteredDeckList().get(index.getZeroBased());
//        editedPerson = new DeckBuilder(deckToEdit).withTags().build();
//        assertCommandSuccess(command, index, editedPerson);
//
//        /* ------------------ Performing edit operation while a filtered list is being shown
// ------------------------ */
//
//        /* Case: filtered deck list, edit index within bounds of address book and deck list -> edited */
//        showPersonsWithName(KEYWORD_MATCHING_JOHN);
//        index = INDEX_FIRST_DECK;
//        assertTrue(index.getZeroBased() < getModel().getFilteredDeckList().size());
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
//        deckToEdit = getModel().getFilteredDeckList().get(index.getZeroBased());
//        editedPerson = new DeckBuilder(deckToEdit).withName(VALID_NAME_BOB).build();
//        assertCommandSuccess(command, index, editedPerson);
//
//        /* Case: filtered deck list, edit index within bounds of address book but out of bounds of deck list
//         * -> rejected
//         */
//        showPersonsWithName(KEYWORD_MATCHING_JOHN);
//        int invalidIndex = getModel().getAnakin().getDeckList().size();
//        assertCommandFailure(EditDeck.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
//                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//
//        /* --------------------- Performing edit operation while a deck card is selected
// -------------------------- */
//
//        /* Case: selects first card in the deck list, edit a deck -> edited, card selection remains unchanged but
//         * browser url changes
//         */
//        showAllDecks();
//        index = INDEX_FIRST_DECK;
//        selectPerson(index);
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY +
// EMAIL_DESC_AMY
//                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
//        // this can be misleading: card selection actually remains unchanged but the
//        // browser's url is updated to reflect the new deck's name
//        assertCommandSuccess(command, index, AMY, index);
//
//        /* --------------------------------- Performing invalid edit operation
// -------------------------------------- */
//
//        /* Case: invalid index (0) -> rejected */
//        assertCommandFailure(EditDeck.COMMAND_WORD + " 0" + NAME_DESC_BOB,
//                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//
//        /* Case: invalid index (-1) -> rejected */
//        assertCommandFailure(EditDeck.COMMAND_WORD + " -1" + NAME_DESC_BOB,
//                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//
//        /* Case: invalid index (size + 1) -> rejected */
//        invalidIndex = getModel().getFilteredDeckList().size() + 1;
//        assertCommandFailure(EditDeck.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
//                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//
//        /* Case: missing index -> rejected */
//        assertCommandFailure(EditDeck.COMMAND_WORD + NAME_DESC_BOB,
//                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//
//        /* Case: missing all fields -> rejected */
//        assertCommandFailure(EditDeck.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased(),
//                EditCommand.MESSAGE_NOT_EDITED);
//
//        /* Case: invalid question -> rejected */
//        assertCommandFailure(EditDeck.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased() + INVALID_ADDRESS_DESC,
//                Address.MESSAGE_ADDRESS_CONSTRAINTS);
//
//        /* Case: invalid answer -> rejected */
//        assertCommandFailure(EditDeck.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased() + INVALID_TAG_DESC,
//                Tag.MESSAGE_TAG_CONSTRAINTS);
//
//        /* Case: edit a deck with new values same as another deck's values -> rejected */
//        executeCommand(AddressbookPersonUtil.getAddCommand(BOB));
//        assertTrue(getModel().getAnakin().getDeckList().contains(BOB));
//        index = INDEX_FIRST_DECK;
//        assertFalse(getModel().getFilteredDeckList().get(index.getZeroBased()).equals(BOB));
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB +
// EMAIL_DESC_BOB
//                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
//        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: edit a deck with new values same as another deck's values but with different tags -> rejected */
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB +
// EMAIL_DESC_BOB
//                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
//        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: edit a deck with new values same as another deck's values but with different address ->
// rejected */
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB +
// EMAIL_DESC_BOB
//                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
//        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: edit a deck with new values same as another deck's values but with different phone -> rejected */
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY +
// EMAIL_DESC_BOB
//                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
//        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: edit a deck with new values same as another deck's values but with different email -> rejected */
//        command = EditDeck.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB +
// EMAIL_DESC_AMY
//                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
//        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except that
//     * the browser url and selected card remain unchanged.
//     * @param toEdit the index of the current model's filtered list
//     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Person, Index)
//     */
//    private void assertCommandSuccess(String command, Index toEdit, Person editedPerson) {
//        assertCommandSuccess(command, toEdit, editedPerson, null);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and
// in addition,<br>
//     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
//     * 2. Asserts that the model related components are updated to reflect the deck at index {@code toEdit} being
//     * updated to values specified {@code editedPerson}.<br>
//     * @param toEdit the index of the current model's filtered list.
//     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
//     */
//    private void assertCommandSuccess(String command, Index toEdit, Person editedPerson,
//            Index expectedSelectedCardIndex) {
//        Model expectedModel = getModel();
//        expectedModel.updateDeck(expectedModel.getFilteredDeckList().get(toEdit
// .getZeroBased()), editedPerson);
//        expectedModel.updateFilteredDeckList(PREDICATE_SHOW_ALL_PERSONS);
//
//        assertCommandSuccess(command, expectedModel,
//                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson), expectedSelectedCardIndex);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)}
// except that the
//     * browser url and selected card remain unchanged.
//     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String
// expectedResultMessage) {
//        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
//    }
//
//    /**
//     * Executes {@code command} and in addition,<br>
//     * 1. Asserts that the command box displays an empty string.<br>
//     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
//     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
//     * {@code expectedSelectedCardIndex}.<br>
//     * 4. Asserts that the status bar's sync status changes.<br>
//     * 5. Asserts that the command box has the default style class.<br>
//     * Verifications 1 and 2 are performed by
//     * {@code AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     * @see AnakinSystemTest#assertSelectedCardChanged(Index)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String
// expectedResultMessage,
//                                      Index expectedSelectedCardIndex) {
//        executeCommand(command);
//        expectedModel.updateFilteredDeckList(PREDICATE_SHOW_ALL_PERSONS);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertCommandBoxShowsDefaultStyle();
//        if (expectedSelectedCardIndex != null) {
//            assertSelectedCardChanged(expectedSelectedCardIndex);
//        } else {
//            assertSelectedCardUnchanged();
//        }
//        assertStatusBarUnchangedExceptSyncStatus();
//    }
//
//    /**
//     * Executes {@code command} and in addition,<br>
//     * 1. Asserts that the command box displays {@code command}.<br>
//     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
//     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
//     * 4. Asserts that the command box has the error style.<br>
//     * Verifications 1 and 2 are performed by
//     * {@code AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see AnakinSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
}
