package seedu.address.storage.portmanager;

import static seedu.address.commons.core.Messages.MESSAGE_FILEPATH_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_IMPORTED_DECK_INVALID;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;
import seedu.address.model.deck.anakinexceptions.DeckImportException;
import seedu.address.storage.XmlExportableDeck;


/**
 * Manages the import and exports of decks
 */

public class PortManager implements Porter {

    private static final Logger logger = LogsCenter.getLogger(PortManager.class);

    private Path baseFilePath;

    public PortManager() {
        baseFilePath = Paths.get("");
    }

    public PortManager(Path bfp) {
        baseFilePath = bfp;
    }

    public String getBfp() {
        return baseFilePath.toAbsolutePath().toString();
    }

    @Override
    public String exportDeck(Deck deck) {
        Name deckName = deck.getName();
        Path filePath = makeFilePath(deckName.fullName);

        XmlExportableDeck adaptedDeck = new XmlExportableDeck(deck);

        try {
            //If file doesn't exist, create it
            FileUtil.createIfMissing(filePath);

            //Write to file.
            XmlUtil.saveDataToFile(filePath, adaptedDeck);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }

        return filePath.toAbsolutePath().toString();
    }

    @Override
    public Deck importDeck(String stringPath) throws DeckImportException {
        Path filepath = makeFilePath(stringPath);
        XmlExportableDeck xmlDeck = loadDeckFromFile(filepath);
        return convertDeck(xmlDeck);
    }

    /**
     * Attempts to load the data from the file at filepath.
     * Returns a XmlExportableDeck object.
     */

    private XmlExportableDeck loadDeckFromFile(Path filepath) throws DeckImportException {
        XmlExportableDeck xmlDeck;
        try {
            xmlDeck = XmlUtil.getDataFromFile(filepath, XmlExportableDeck.class);
            return xmlDeck;
        } catch (javax.xml.bind.JAXBException e) {
            logger.info("Illegal values found in " + filepath + ": " + e.getMessage());
            throw new DeckImportException(MESSAGE_IMPORTED_DECK_INVALID);
        } catch (FileNotFoundException e) {
            throw new DeckImportException(String.format(MESSAGE_FILEPATH_INVALID, filepath));
        }
    }

    /**
     * Converts the XmlExportableDeck to a Deck
     * Returns a deck object
     * If contents are invalid, throw DataConversionException
     */

    private Deck convertDeck(XmlExportableDeck targetDeck) {
        try {
            return targetDeck.toModelType();
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + targetDeck + ": " + e.getMessage());
            throw new DeckImportException(MESSAGE_IMPORTED_DECK_INVALID);
        }

    }

    /**
     * Convert the string into a file path.
     *
     * @param name The name of the file, can be the absolute or relative file path
     * @return a Path that represents the file path
     */

    private Path makeFilePath(String name) {
        if (name.length() > 4 && name.substring(name.length() - 4).equals(".xml")) {
            return baseFilePath.resolve(name);
        } else {
            return baseFilePath.resolve(name + ".xml");
        }
    }

}
