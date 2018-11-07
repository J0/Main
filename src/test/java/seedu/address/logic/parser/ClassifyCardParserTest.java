package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ClassifyCommand;
import seedu.address.model.deck.Performance;

public class ClassifyCardParserTest {

    private static final String MESSAGE_INVALID_CLASSIFY_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassifyCommand.MESSAGE_USAGE);

    private ClassifyCommandParser parser = new ClassifyCommandParser();

    @Test
    public void parse_validArgs_success() {
        // EASY
        assertParseSuccess(parser, String.valueOf(Performance.EASY), new ClassifyCommand(Performance.EASY));

        // NORMAL
        assertParseSuccess(parser, String.valueOf(Performance.NORMAL), new ClassifyCommand(Performance.NORMAL));

        // HARD
        assertParseSuccess(parser, String.valueOf(Performance.HARD), new ClassifyCommand(Performance.HARD));
    }


    @Test
    public void parse_invalidValue_failure() {
        // Invalid Rating
        assertParseFailure(parser, "Terrible", MESSAGE_INVALID_CLASSIFY_FORMAT);

        // Blank
        assertParseFailure(parser, "", MESSAGE_INVALID_CLASSIFY_FORMAT);
    }

}
