package seedu.address.model.deck;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that a {@code Card}'s {@code Performance} matches any of the Performances given.
 */
public class CardPerformanceMatchesPerformancesPredicate implements Predicate<Card> {
    private final Set<Performance> performances;

    public CardPerformanceMatchesPerformancesPredicate(Set<Performance> performances) {
        this.performances = performances;
    }

    /**
     * Returns the constituent performances as a list of strings
     *
     * @return
     */
    public List<String> performancesAsStrings() {
        return performances.stream().map(Performance::toString).collect(Collectors.toList());
    }

    @Override
    public boolean test(Card card) {
        return performances.contains(card.getPerformance());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof CardPerformanceMatchesPerformancesPredicate)) {
            return false;
        }
        CardPerformanceMatchesPerformancesPredicate predicate = (CardPerformanceMatchesPerformancesPredicate) other;
        return performances.size() == predicate.performances.size()
                && performances.containsAll(predicate.performances);
    }
}
