package seedu.address.model.deck;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PerformanceTest {
    private static final String INVALID_PERFORMANCE_STRING = "tough";
    private static final Performance VALID_PERFORMANCE = Performance.HARD;
    private static final String VALID_PERFORMANCE_STRING = "hard";
    private static final String VALID_PERFORMANCE_STRING_MIXED_CASE = "hArD";

    @Test
    public void type_validPerformanceToString_returnsPerformance() {
        Performance expectedPerformance = Performance.HARD;
        Performance actualPerformance = Performance.type(expectedPerformance.toString());
        assertEquals(expectedPerformance, actualPerformance);
    }

    @Test
    public void type_validPerformanceString_returnsPerformance() {
        assertEquals(VALID_PERFORMANCE, Performance.type(VALID_PERFORMANCE_STRING));
    }

    @Test
    public void type_validPerformanceStringMixedCase_returnsPerformance() {
        assertEquals(VALID_PERFORMANCE, Performance.type(VALID_PERFORMANCE_STRING_MIXED_CASE));
    }

    @Test
    public void type_invalidPerformanceString_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> Performance.type(INVALID_PERFORMANCE_STRING));
    }

    @Test
    public void type_nullString_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Performance.type(null));
    }
}
