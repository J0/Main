package seedu.address.model.deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import seedu.address.testutil.CardBuilder;

public class CardPerformanceMatchesPerformancesTest {
    private Set<Performance> hardPerformanceSet = Set.of(Performance.HARD);
    private Set<Performance> easyHardPerformancesSet = Set.of(Performance.HARD, Performance.EASY);
    private Set<Performance> hardEasyPerformancesSet = Set.of(Performance.EASY, Performance.HARD);
    private Set<Performance> allPerformancesSet = Set.of(Performance.values());
    private CardPerformanceMatchesPerformancesPredicate hardPerformancesPredicate = new
            CardPerformanceMatchesPerformancesPredicate(hardPerformanceSet);
    private CardPerformanceMatchesPerformancesPredicate easyHardPerformancesPredicate = new
            CardPerformanceMatchesPerformancesPredicate(easyHardPerformancesSet);
    private CardPerformanceMatchesPerformancesPredicate hardEasyPerformancesPredicate = new
            CardPerformanceMatchesPerformancesPredicate(hardEasyPerformancesSet);
    private CardPerformanceMatchesPerformancesPredicate allPerformanecsPredicate = new
            CardPerformanceMatchesPerformancesPredicate(allPerformancesSet);

    private Card easyCard = new CardBuilder().withPerformance(Performance.EASY).build();
    private Card normalCard = new CardBuilder().withPerformance(Performance.NORMAL).build();
    private Card hardCard = new CardBuilder().withPerformance(Performance.HARD).build();

    @Test
    public void equals() {
        assertEquals(easyHardPerformancesPredicate, hardEasyPerformancesPredicate);
        assertEquals(allPerformanecsPredicate, allPerformanecsPredicate);
        assertNotEquals(easyHardPerformancesPredicate, hardPerformancesPredicate);
    }

    @Test
    public void matchingPerformance_returnsTrue() {
        assertTrue(hardPerformancesPredicate.test(hardCard));
        assertTrue(easyHardPerformancesPredicate.test(easyCard));
        assertTrue(easyHardPerformancesPredicate.test(hardCard));
        assertTrue(allPerformanecsPredicate.test(easyCard));
        assertTrue(allPerformanecsPredicate.test(normalCard));
        assertTrue(allPerformanecsPredicate.test(hardCard));
    }

    @Test
    public void notMatchingPerformance_returnsFalse() {
        assertFalse(hardPerformancesPredicate.test(normalCard));
        assertFalse(easyHardPerformancesPredicate.test(normalCard));
    }
}
