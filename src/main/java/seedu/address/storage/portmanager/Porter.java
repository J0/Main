package seedu.address.storage.portmanager;

import seedu.address.model.deck.Deck;
import seedu.address.model.deck.anakinexceptions.DeckImportException;

public interface Porter {

    String exportDeck(Deck deck);

    Deck importDeck(String stringPath) throws DeckImportException;
}
