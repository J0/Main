package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.deck.Card;

/**
 * An UI component that displays information of a {@code Card}.
 */
public class CardCard extends UiPart<Region> {

    private static final String FXML = "CardListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Card card;

    @FXML
    private HBox cardPane;
    @FXML
    private Label question;
    @FXML
    private Label difficulty;
    @FXML
    private Label id;

    public CardCard(Card card, int displayedIndex) {
        super(FXML);
        this.card = card;
        id.setText(displayedIndex + ". ");
        question.setText(card.getQuestion().fullQuestion);
        difficulty.setText(card.getPerformance().toString());
        switch (card.getPerformance()) {
        case EASY:
            difficulty.setStyle("-fx-background-color: #3dc93e");
            break;
        case NORMAL:
            difficulty.setStyle("-fx-background-color: #c9c235");
            break;
        case HARD:
            difficulty.setStyle("-fx-background-color: #c92c2a; -fx-text-fill: #ffffff");
            break;
        default:
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CardCard)) {
            return false;
        }

        // state check
        CardCard card = (CardCard) other;
        return id.getText().equals(card.id.getText())
                && this.card.equals(card.card);
    }
}
