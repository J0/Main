package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DECK_DISPLAYED_INDEX = "The deck index provided is invalid";
    public static final String MESSAGE_INVALID_CARD_DISPLAYED_INDEX = "The card index provided is invalid";
    public static final String MESSAGE_NOT_INSIDE_DECK = "Not currently inside any deck";
    public static final String MESSAGE_DECKS_LISTED_OVERVIEW = "%1$d deck(s) listed";
    public static final String MESSAGE_CARDS_LISTED_OVERVIEW = "%1$d card(s) listed";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck already exists in Anakin";
    public static final String MESSAGE_FILEPATH_INVALID = "File at %1$s not found";
    public static final String MESSAGE_IMPORTED_DECK_INVALID = "Target deck contains invalid data";
    public static final String MESSAGE_EDIT_DECK_SUCCESS = "Edited Deck: %1$s";
    public static final String MESSAGE_DECK_NOT_EDITED = "Index of Deck to edit and Name to edit to must be provided.";
    public static final String MESSAGE_NOT_REVIEWING_DECK = "Not currently reviewing deck.";
    public static final String MESSAGE_CURRENTLY_REVIEWING_DECK = "Command disabled while reviewing deck.\n"
        + "Use `endreview` command to exit reviewing";
    public static final String MESSAGE_INVALID_DECK_LEVEL_OPERATION = "This deck-level command is invalid, please"
        + " navigate out of the current deck to perform this command.";
    public static final String MESSAGE_INVALID_CARD_LEVEL_OPERATION = "This card-level command is invalid, please"
        + " navigate into a deck to perform this command.";
}
