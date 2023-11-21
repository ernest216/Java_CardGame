package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class DeckTest {
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testAddAndGetCard() {
        Card card = new Card(5);
        deck.addCard(card);

        ArrayList<Card> currentDeck = deck.getDeck();
        assertNotNull("Deck should not be null", currentDeck);
        assertFalse("Deck should not be empty", currentDeck.isEmpty());
        assertEquals("Deck should contain the added card", card, currentDeck.get(0));
    }

    @Test
    public void testGetIndex() {
        // Assuming deckIndex is correctly managed elsewhere in your Deck class
        int index = deck.getIndex();
        assertEquals("Initial index should be 0", 0, index);
    }

}