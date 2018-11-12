package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.deck.CardPerformanceMatchesPerformancesPredicate;
import seedu.address.model.deck.Performance;

/**
 * Parses input elements and creates a new ListCommand object
 */
public class ListCommandParser implements ParserInterface<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public ListCommand parse(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            String[] keywords = trimmedArgs.split("\\s+");
            Set<Performance> performanceSet =
                    Arrays.stream(keywords).filter(Performance::isValidPerformance)
                            .map(Performance::type).collect(Collectors.toSet());
            if (!performanceSet.isEmpty()) {
                return new ListCommand(new CardPerformanceMatchesPerformancesPredicate(performanceSet));
            }
        }
        return new ListCommand();
    }
}
