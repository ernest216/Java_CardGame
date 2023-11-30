
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestDeck {
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testAddAndDrawCard() {
        Card card = new Card();
        deck.addCard(card);

        ArrayList<Card> currentDeck = deck.getDeck();
        assertEquals("Deck should contain the added card", card, currentDeck.get(0));
        assertNotNull("Deck should not be null", currentDeck);
        assertFalse("Deck should not be empty", currentDeck.isEmpty());
    }

    @Test
    public void testGetIndex() {
        // Assuming deckIndex is correctly managed elsewhere in your Deck class
        int index = deck.getIndex();
        assertEquals("Index should be 0", 0, index);
    }

}import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestDeck {
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testAddAndDrawCard() {
        Card card = new Card();
        deck.addCard(card);

        ArrayList<Card> currentDeck = deck.getDeck();
        assertEquals("Deck should contain the added card", card, currentDeck.get(0));
        assertNotNull("Deck should not be null", currentDeck);
        assertFalse("Deck should not be empty", currentDeck.isEmpty());
    }

    @Test
    public void testGetIndex() {
        // Assuming deckIndex is correctly managed elsewhere in your Deck class
        int index = deck.getIndex();
        assertEquals("Index should be 0", 0, index);
    }

}