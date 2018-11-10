package seedu.address.logic.parser;

//import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.ChangeDeckCommand;
import seedu.address.logic.commands.ClassifyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.EditDeckCommand;
import seedu.address.logic.commands.EndReviewCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportDeckCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FlipCardCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportDeckCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewCardCommand;
import seedu.address.logic.commands.NewDeckCommand;
import seedu.address.logic.commands.NextCardCommand;
import seedu.address.logic.commands.PreviousCardCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;


/**
 * The logic component that is responsible for performing autocompletion based on user input.
 */

public class Autocompleter {
    public static final String COMMAND_DIRECTORY = "../commands";


    /**
     * Checks if {@code commandBox}'s commandTextField has a potential autocompletable command word,
     * if there exists such a word, it is replaced with the relevant autocompletion command.
     */
    public static boolean isAutocompletable(String input) {
        // Check if the give input string has an existing autocompletion in the list of command words which starts
        // with what the user has currently typed

        return getCommandList().stream().filter(command -> command.startsWith(input)).collect(
                Collectors.toList()).size() > 0;

    }

    /**
     * Filters from list of available autocompletions and retrieves the first matching completion
     */
    public static String getAutocompletion(String input) {
        return getAutocompletionStrings().stream().filter(
            completion -> completion.startsWith(input)).collect(Collectors.toList()).get(0);
    }

    private static List<String> getCommandList() {
        return getCommandField("COMMAND_WORD");

    }

    private static List<String> getAutocompletionStrings() {
        return getCommandField("AUTOCOMPLETE_TEXT");

    }

    /**
     * Get a property/field for a particular command class.
     */
    private static List<String> getCommandField(String field) {
        return getCommandClasses().stream().map(command -> {
            try {
                // Get all fields in the given list of strings
                return (String) command.getField(field).get(null);
            } catch (IllegalAccessException e) {
                return "";

            } catch (NoSuchFieldException e) {
                return "";
            }
        }).collect(Collectors.toList());

    }

    /**
     * Creates a list of all existing command classes and returns it to the user.
     */
    private static List<Class<? extends Command>> getCommandClasses() {
        //        File directory = new File(COMMAND_DIRECTORY);
        //        File[] fList = directory.listFiles();
        List<Class<? extends Command>> commandClasses = new ArrayList<>();
        commandClasses.add(ChangeDeckCommand.class);
        commandClasses.add(ClassifyCommand.class);
        commandClasses.add(ClearCommand.class);
        commandClasses.add(DeleteCardCommand.class);
        commandClasses.add(DeleteDeckCommand.class);
        commandClasses.add(EditCardCommand.class);
        commandClasses.add(EditDeckCommand.class);
        commandClasses.add(EndReviewCommand.class);
        commandClasses.add(ExitCommand.class);
        commandClasses.add(ExportDeckCommand.class);
        commandClasses.add(FindCommand.class);
        commandClasses.add(FlipCardCommand.class);
        commandClasses.add(HelpCommand.class);
        commandClasses.add(HistoryCommand.class);
        commandClasses.add(ImportDeckCommand.class);
        commandClasses.add(ListCommand.class);
        commandClasses.add(NewCardCommand.class);
        commandClasses.add(NewDeckCommand.class);
        commandClasses.add(NextCardCommand.class);
        commandClasses.add(PreviousCardCommand.class);
        commandClasses.add(RedoCommand.class);
        commandClasses.add(ReviewCommand.class);
        commandClasses.add(SortCommand.class);
        commandClasses.add(UndoCommand.class);
        return commandClasses;

    }


}
