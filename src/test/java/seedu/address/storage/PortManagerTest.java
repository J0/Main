package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_FILEPATH_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_IMPORTED_DECK_INVALID;
import static seedu.address.testutil.TypicalDecks.DECK_WITH_CARDS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.anakinexceptions.DeckImportException;
import seedu.address.storage.portmanager.PortManager;
import seedu.address.testutil.DeckBuilder;

public class PortManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "PortManagerTest");
    private static final PortManager portManager = new PortManager(TEST_DATA_FOLDER);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    private List<Card> cardList = DECK_WITH_CARDS.getCards().asUnmodifiableObservableList();
    private Deck testDeck = new DeckBuilder().withName("Valid Exported Deck").withCards(cardList).build();

    @Test
    public void exportDeck_success() throws Exception {
        portManager.exportDeck(testDeck);
    }

    @Test
    public void exportImportDeck_success() throws Exception {
        String deckName = testDeck.getName().fullName;
        portManager.exportDeck(testDeck);
        String testFilePath = deckName;
        Deck importedDeck = portManager.importDeck(testFilePath);
        assertEquals(testDeck, importedDeck);
    }

    @Test
    public void importDeckBadFilePath_failure() throws Exception {
        String badFile = "NAN";
        String badFilePath = makeFilePath(badFile).toAbsolutePath().toString();
        thrown.expect(DeckImportException.class);
        thrown.expectMessage(String.format(MESSAGE_FILEPATH_INVALID, badFilePath));

        portManager.importDeck(badFilePath);
    }

    @Test
    public void importDeckCorruptedFile_failure() throws Exception {
        String badFilePath = "InvalidDeck";

        thrown.expect(DeckImportException.class);
        thrown.expectMessage(MESSAGE_IMPORTED_DECK_INVALID);

        portManager.importDeck(badFilePath);
    }

    /**
     * Converts a string to a full Path.
     */

    private Path makeFilePath(String name) {
        Path baseFilePath = Paths.get(portManager.getBfp());
        if (name.length() > 4 && name.substring(name.length() - 4).equals(".xml")) {
            return baseFilePath.resolve(name);
        } else {
            return baseFilePath.resolve(name + ".xml");
        }
    }

}
