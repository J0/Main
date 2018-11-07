package seedu.address.model.deck;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Card inside a Deck.
 */
public class Card {

    private final Performance performance;
    private final int timesReviewed;

    // Identity fields
    private final Question question;
    private final Answer answer;

    public Card(Question question, Answer answer) {
        requireAllNonNull(question, answer);
        this.question = question;
        this.answer = answer;
        performance = Performance.NORMAL;
        timesReviewed = 0;
    }

    public Card(Question question, Answer answer, Performance performance, int timesReviewed) {
        requireAllNonNull(question, answer, performance, timesReviewed);
        this.question = question;
        this.answer = answer;
        this.performance = performance;
        this.timesReviewed = timesReviewed;
    }

    public Card(Card other) {
        this.question = new Question(other.getQuestion().toString());
        this.answer = new Answer(other.getAnswer().toString());
        this.performance = other.performance;
        this.timesReviewed = other.timesReviewed;
    }

    public static Card classifyCard(Card card, Performance performance) {
        return new Card(card.question, card.answer, performance, card.timesReviewed + 1);
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Performance getPerformance() {
        return performance;
    }

    public int getTimesReviewed() {
        return timesReviewed;
    }

    /**
     * Returns true if 2 cards are the same, or have same question.
     */
    public boolean isSameCard(Card otherCard) {
        if (otherCard == this) {
            return true;
        }

        if (otherCard == null) {
            return false;
        }

        return otherCard.getQuestion().equals(question);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Card)) {
            return false;
        }

        return isSameCard((Card) other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Question: ")
                .append(question)
                .append(" Answer: ")
                .append(answer);
        return builder.toString();
    }
}
