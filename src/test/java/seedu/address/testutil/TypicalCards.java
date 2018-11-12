package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;
import seedu.address.model.deck.Performance;

/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalCards {

    public static final Card CARD_A = new CardBuilder().withQuestion("An").withAnswer("Empty")
            .withReviewScore(4.0).build();
    public static final Card CARD_B = new CardBuilder().withQuestion("Street").withAnswer("An")
            .withReviewScore(5.0).build();
    public static final Card CARD_C = new CardBuilder().withQuestion("Empty").withAnswer("House")
            .withReviewScore(3.0).build();
    public static final Card CARD_D = new CardBuilder().withQuestion("A").withAnswer("Hole").build();
    public static final Card CARD_E = new CardBuilder().withQuestion("Inside").withAnswer("My").build();
    public static final Card CARD_F = new CardBuilder().withQuestion("Heart").withAnswer("I am").build();
    public static final Card CARD_G = new CardBuilder().withQuestion("All").withAnswer("Alone").build();

    public static final Card MILLION = new CardBuilder().withQuestion("A million").build();
    public static final Card IS = new CardBuilder().withQuestion("dreams is").build();
    public static final Card ALL = new CardBuilder().withQuestion("all it's gonna take").build();

    public static final Card CARD_EASY = new CardBuilder().withQuestion("An easy card")
            .withPerformance(Performance.EASY).build();
    public static final Card CARD_HARD = new CardBuilder().withQuestion("A hard card")
            .withPerformance(Performance.HARD).build();

    public static final Card CARD_A_WITH_META = new CardBuilder().withQuestion("An").withAnswer("Empty")
            .withPerformance(Performance.EASY).withTimesReviewed(5).build();

    private TypicalCards() {
    } // prevents instantiation

    /**
     * Returns a {@code Deck} with all the typical cards.
     */
    public static Deck getTypicalDeck() {
        Deck ad = new Deck(new Name("HEYYY"), getTypicalCards());
        return ad;
    }

    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(CARD_A, CARD_B, CARD_C, CARD_D, CARD_E, CARD_F, CARD_G,
                CARD_EASY, CARD_HARD,
                MILLION, IS, ALL));
    }
}
