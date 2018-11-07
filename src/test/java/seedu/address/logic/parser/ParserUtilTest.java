package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ANSWER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUESTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_A;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Answer;
import seedu.address.model.deck.Name;
import seedu.address.model.deck.Performance;
import seedu.address.model.deck.Question;
import seedu.address.testutil.Assert;

public class ParserUtilTest {

    private static final String WHITESPACE = " \t\r\n";

    private static final String INVALID_PERFORMANCE_STRING = "tough";
    private static final Performance VALID_PERFORMANCE = Performance.HARD;
    private static final String VALID_PERFORMANCE_STRING = VALID_PERFORMANCE.toString();
    private static final String VALID_PERFORMANCE_STRING_MIXED_CASE = "hArD";


    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_DECK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_DECK, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseQuestion_validValueWithoutWhitespace_returnsQuestion() throws Exception {
        Question expectedQuestion = new Question(VALID_QUESTION_A);
        assertEquals(expectedQuestion, ParserUtil.parseQuestion(VALID_QUESTION_A));
    }

    @Test
    public void parseQuestion_validValueWithWhitespace_returnsTrimmedQuestion() throws Exception {
        String questionWithWhitespace = WHITESPACE + VALID_QUESTION_A + WHITESPACE;
        Question expectedQuestion = new Question(VALID_QUESTION_A);
        assertEquals(expectedQuestion, ParserUtil.parseQuestion(questionWithWhitespace));
    }

    @Test
    public void parseQuestion_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseQuestion(null));
    }

    @Test
    public void parseQuestion_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseQuestion(INVALID_QUESTION));
    }

    @Test
    public void parseAnswer_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAnswer(null));
    }

    @Test
    public void parseAnswer_validValueWithoutWhitespace_returnsAnswer() throws Exception {
        Answer expectedAnswer = new Answer(VALID_ANSWER_A);
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(VALID_ANSWER_A));
    }

    @Test
    public void parseAnswer_validValueWithWhitespace_returnsTrimmedAnswer() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ANSWER_A + WHITESPACE;
        Answer expectedAnswer = new Answer(VALID_ANSWER_A);
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(addressWithWhitespace));
    }

    @Test
    public void parseAnswer_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAnswer(INVALID_ANSWER));
    }


    @Test
    public void parsePerformance_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePerformance(null));
    }

    @Test
    public void parsePerformance_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePerformance(INVALID_PERFORMANCE_STRING));
    }

    @Test
    public void parsePerformance_validValueWithoutWhitespace_returnsPerformance() throws Exception {
        assertEquals(VALID_PERFORMANCE, ParserUtil.parsePerformance(VALID_PERFORMANCE_STRING));
    }

    @Test
    public void parsePerformance_validValueWithWhitespace_returnsTrimmedPerformance() throws Exception {
        String performanceWithWhitespace = WHITESPACE + VALID_PERFORMANCE_STRING + WHITESPACE;
        assertEquals(VALID_PERFORMANCE, ParserUtil.parsePerformance(performanceWithWhitespace));
    }

    @Test
    public void parsePerformance_validValueWithMixedCase_returnsTrimmedPerformance() throws Exception {
        assertEquals(VALID_PERFORMANCE, ParserUtil.parsePerformance(VALID_PERFORMANCE_STRING_MIXED_CASE));
    }
}
