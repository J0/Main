package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_FILEPATH_INVALID;
import static seedu.address.storage.XmlSerializableAnakin.MESSAGE_DUPLICATE_DECK;
import static seedu.address.testutil.TypicalDecks.DECK_WITH_CARDS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAnakin;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.anakinexceptions.DeckImportException;
import seedu.address.storage.portmanager.PortManager;
import seedu.address.storage.portmanager.Porter;

public class ImportDeckCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void importDeck_success() throws Exception {
        Model testModel = new ModelAlwaysImports();
        Deck deckToImport = DECK_WITH_CARDS;
        ImportDeckCommand importCommand = new ImportDeckCommand("Unused");
        CommandResult commandResult = importCommand.execute(testModel, commandHistory);

        assertEquals(String.format(ImportDeckCommand.MESSAGE_IMPORT_DECK_SUCCESS, deckToImport),
            commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void importFileCantBeFound_throwsException() throws Exception {
        Model testModel = new ModelCantFindFile();
        String filepath = "test filepath.xml";
        ImportDeckCommand importCommand = new ImportDeckCommand(filepath);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_FILEPATH_INVALID, filepath));

        importCommand.execute(testModel, commandHistory);
    }

    @Test
    public void importDuplicate_throwsException() throws Exception {
        Model testModel = new ModelThrowsDe();
        Deck deckToImport = DECK_WITH_CARDS;
        ImportDeckCommand importCommand = new ImportDeckCommand("Unused");

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_DECK);

        importCommand.execute(testModel, commandHistory);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyAnakin anakin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAnakin getAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeck(Deck target, Deck newdeck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCard(Card target, Card newcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void getOutOfDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void getIntoDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isReviewingDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void startReview() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void endReview() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getIndexOfCurrentCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIndexOfCurrentCard(int newIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeckList(Predicate<Deck> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCardList(Predicate<Card> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String undoAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String redoAnakin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAnakin(String command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isInsideDeck() {
            return false;
        }

        @Override
        public String exportDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck importDeck(String filepath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sort() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that always imports the same deck.
     */

    private class ModelAlwaysImports extends ModelStub {
        private final Porter porter = new PortManagerStub();

        @Override
        public Deck importDeck(String filepath) {
            return porter.importDeck(filepath);
        }

        @Override
        public boolean isReviewingDeck() {
            return false;
        }

        @Override
        public void commitAnakin(String command) {
            // called by {@code ImportDeckCommand#execute()}
        }

    }

    private class ModelCantFindFile extends ModelStub {
        final Porter porter = new PortManagerFileNotFound();

        @Override
        public Deck importDeck(String filepath) {
            return porter.importDeck(filepath);
        }

        @Override
        public boolean isReviewingDeck() {
            return false;
        }

        @Override
        public void commitAnakin(String command) {
            // called by {@code ImportDeckCommand#execute()}
        }
    }

    private class ModelThrowsDe extends ModelStub {
        final Porter porter = new PortManager();

        @Override
        public Deck importDeck(String filepath) {
            throw new DeckImportException(MESSAGE_DUPLICATE_DECK);
        }

        @Override
        public boolean isReviewingDeck() {
            return false;
        }

        @Override
        public void commitAnakin(String command) {
            // called by {@code ImportDeckCommand#execute()}
        }
    }


    private static class PortManagerStub implements Porter {
        @Override
        public String exportDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck importDeck(String stringPath) throws DeckImportException {
            return DECK_WITH_CARDS;
        }
    }

    private static class PortManagerFileNotFound implements Porter {

        private static Path baseFilePath = Paths.get("");

        @Override
        public String exportDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck importDeck(String stringPath) throws DeckImportException {
            Path filepath = makeFilePath(stringPath);
            throw new DeckImportException(String.format(MESSAGE_FILEPATH_INVALID, filepath));
        }

        /**
         * Makes a string into a path.
         */

        private Path makeFilePath(String name) {
            if (name.substring(name.length() - 4).equals(".xml")) {
                return baseFilePath.resolve(name);
            } else {
                return baseFilePath.resolve(name + ".xml");
            }
        }
    }
}

