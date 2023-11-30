package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.concurrent.BrokenBarrierException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Card;
import src.CardDeck;
import src.CardGame;
import src.Player;

public class CardGameTest {

    @Before
    public void testCreatePlayer() {
        CardGame cardGame = new CardGame();
        cardGame.createPlayer(1, new Deck(1), new Deck(2));
        assertEquals(1, cardGame.getPlayers().size());
    }

    @Test
    public void testCreateDeck() {
        CardGame cardGame = new CardGame();
        cardGame.createDeck(1);
        assertEquals(1, cardGame.getDecks().size());
    }

    @Test
    public void testGetPlayers() {
        CardGame cardGame = new CardGame();
        assertNotNull(cardGame.getPlayers());
    }

    @Test
    public void testGetDecks() {
        CardGame cardGame = new CardGame();
        assertNotNull(cardGame.getDecks());
    }

    @Test
    public void testGenerateRandomCardValues() {
        int numPlayers = 2;
        CardGame cardGame = new CardGame();
        assertEquals(8 * numPlayers, cardGame.generateRandomCardValues(numPlayers).size());
    }

    @Test
    public void testDeclareWin() throws IOException {
        CardGame cardGame = new CardGame();
        cardGame.createPlayer(1, new Deck(1), new Deck(2));
        cardGame.declareWin(1);
        // Assuming your Player class has a method to check the winner status
        assertTrue(cardGame.getPlayers().get(0).isWinner());
    }
}