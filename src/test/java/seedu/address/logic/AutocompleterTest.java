package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.NewDeckCommand;
import seedu.address.logic.parser.Autocompleter;


public class AutocompleterTest {
    private Autocompleter autocompleter;
    @Before
    public void setUp() {
        autocompleter = new Autocompleter();
    }

    @Test
    public void autocompleter_is_completable() {
        final String completableCommand = "newd";
        final String uncompletableCommand = "dwen";
        assertTrue(autocompleter.isAutocompletable(completableCommand));
        assertFalse(autocompleter.isAutocompletable(uncompletableCommand));
    }
    @Test
    public void autocompleter_completion() {
        final String completableCommand = "newd";
        assertEquals(autocompleter.getAutocompletion(completableCommand), NewDeckCommand.AUTOCOMPLETE_TEXT);

    }
}
