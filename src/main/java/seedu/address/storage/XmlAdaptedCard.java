package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deck.Answer;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Performance;
import seedu.address.model.deck.Question;

/**
 * JAXB-friendly version of the Card.
 */
public class XmlAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    @XmlElement(required = true)
    private String question;
    @XmlElement(required = true)
    private String answer;
    @XmlElement
    private String performance;
    @XmlElement
    private int timesReviewed = 0;


    /**
     * Constructs an XmlAdaptedCard.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCard() {
    }

    /**
     * Constructs an {@code XmlAdaptedCard} with the given person details.
     */
    public XmlAdaptedCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public XmlAdaptedCard(String question, String answer, String performance, int timesReviewed) {
        this.question = question;
        this.answer = answer;
        this.performance = performance;
        this.timesReviewed = timesReviewed;
    }

    /**
     * Converts a given Card into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCard
     */
    public XmlAdaptedCard(Card source) {
        question = source.getQuestion().fullQuestion;
        answer = source.getAnswer().fullAnswer;
        performance = source.getPerformance().toString();
        timesReviewed = source.getTimesReviewed();
    }

    /**
     * Converts this jaxb-friendly adapted card object into the model's Card object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Card toModelType() throws IllegalValueException {
        if (question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_QUESTION_CONSTRAINTS);
        }

        final Question cardQuestion = new Question(question);

        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_ANSWER_CONSTRAINTS);
        }

        final Answer cardAnswer = new Answer(answer);

        Performance cardPerformance = Performance.NORMAL;
        if (Performance.isValidPerformance(performance)) {
            cardPerformance = Performance.type(performance);
        }

        return new Card(cardQuestion, cardAnswer, cardPerformance, timesReviewed);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCard)) {
            return false;
        }

        XmlAdaptedCard otherCard = (XmlAdaptedCard) other;
        return Objects.equals(question, otherCard.question)
                && Objects.equals(answer, otherCard.answer)
                && Objects.equals(performance, otherCard.performance)
                && Objects.equals(timesReviewed, otherCard.timesReviewed);
    }
}
