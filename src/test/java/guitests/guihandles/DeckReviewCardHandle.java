package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.deck.Card;

/**
 * Provides a handle to a deck review card.
 */
public class DeckReviewCardHandle extends NodeHandle<Node> {
    private static final String TEXT_FIELD_ID = "#text";

    private final Label textLabel;
    private final boolean showAnswer;

    public DeckReviewCardHandle(Node cardNode, boolean showAnswer) {
        super(cardNode);

        textLabel = getChildNode(TEXT_FIELD_ID);
        this.showAnswer = showAnswer;
    }

    public String getText() {
        return textLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code card}.
     */
    public boolean equals(Card card) {
        if (showAnswer) {
            return getText().equals(card.getAnswer().fullAnswer);
        } else {
            return getText().equals(card.getQuestion().fullQuestion);
        }
    }
}
