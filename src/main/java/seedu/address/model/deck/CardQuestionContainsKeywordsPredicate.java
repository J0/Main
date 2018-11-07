package seedu.address.model.deck;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Card}'s {@code Question} matches any of the keywords given.
 */
public class CardQuestionContainsKeywordsPredicate implements Predicate<Card> {
    private final List<String> keywords;

    public CardQuestionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Card card) {
        if (keywords.size() == 1) {
            return card.getQuestion().toString().toLowerCase().contains(keywords.get(0).toLowerCase());
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(card.getQuestion().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CardQuestionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CardQuestionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
