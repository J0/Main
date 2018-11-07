package seedu.address.model.deck;

/**
 * Represents a card's performance.
 */
public enum Performance {
    EASY,
    NORMAL,
    HARD;

    public static final String MESSAGE_PERFORMANCE_CONSTRAINTS =
            "Performance must be one of the strings {easy|normal|hard}";

    /**
     * Converts the provided string to a Performance type
     * @param type the input string
     * @return the converted Performance type
     */
    public static Performance type(String type) {
        return Performance.valueOf(type.toUpperCase());
    }

    /**
     * Returns a boolean indicating whether the given string can be converted to a valid performance type
     * @param type the input string
     * @return True if type is a valid performance, false otherwise
     */
    public static boolean isValidPerformance(String type) {
        if (type == null) {
            return false;
        }
        try {
            Performance dummy = Performance.type(type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
