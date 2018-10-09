package seedu.address.logic.AnakinParser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.anakindeck.AnakinAnswer;
import seedu.address.model.anakindeck.AnakinQuestion;
import seedu.address.model.anakindeck.Name;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class AnakinParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed. NEEDS TO CHANGE TO ANAKIN_NAME
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String question} into a {@code AnakinQuestion}
     *
     * @throws ParseException
     */
    public static AnakinQuestion parseQuestion(String question) throws ParseException {
        requireNonNull(question);
        String trimmed = question.trim();
        if (!AnakinQuestion.isValidQuestion(trimmed)) {
            throw new ParseException(AnakinQuestion.MESSAGE_QUESTION_CONSTRAINTS);
        }
        return new AnakinQuestion(trimmed);
    }

    /**
     * Parses a {@code String answer} into a {@code AnakinAnswer}
     *
     * @throws ParseException
     */
    public static AnakinAnswer parseAnswer(String answer) throws ParseException {
        requireNonNull(answer);
        String trimmed = answer.trim();
        if (!AnakinAnswer.isValidAnswer(trimmed)) {
            throw new ParseException(AnakinAnswer.MESSAGE_ANSWER_CONSTRAINTS);
        }
        return new AnakinAnswer(trimmed);
    }
}
