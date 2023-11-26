import org.junit.*;
import static org.junit.Assert.*;

public class TestPlayer {
    private Player player1;
    private Player player2;
    private Player player3;
    private int[] correct1;
    private Deck deck;
    private Deck invalidDeck;
    private CardGame game;

    @Before
    public void setup() {
        Card n1 = new Card(1);
        Card n2 = new Card(2);
        Card n3 = new Card(3);
        Card n4 = new Card(4);
        Card n5 = new Card(5);
        game = new CardGame();

        deck = new Deck(); // Assuming Deck constructor and methods
        deck.addCard(n1);
        deck.addCard(n2);
        deck.addCard(n3);
        deck.addCard(n4);

        invalidDeck = new Deck();
        invalidDeck.addCard(n5);

        this.player1 = new Player(1, invalidDeck, deck, game);
        player1.addCard(n1);
        player1.addCard(n2);
        player1.addCard(n3);
        player1.addCard(n4);
        correct1 = new int[] {1, 2, 3, 4};

        player2 = new Player(2, deck, deck, game);
        for (int i = 0; i < 4; i++) {
            player2.addCard(n5);
        }

        player3 = new Player(3, deck, invalidDeck, game);
        for (int i = 0; i < 3; i++) {
            player3.addCard(n3);
        }
        player3.addCard(n5);
    }

    @Test
    public void testGetPlayerIndex() {
        assertEquals(1, player1.getPlayerIndex());
    }

    @Test
    public void testGetPlayerCards() {
        assertEquals(4, player1.getPlayerCards().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(correct1[i], player1.getPlayerCards().get(i).getValue());
        }
    }

    @Test
    public void testGetWinner() {
        assertEquals(0, player1.getWinner());
    }

    @Test
    public void testSetWinner() {
        player1.setWinner(1);
        assertEquals(1, player1.getWinner());
        player1.setWinner(0); // Resetting for other tests
    }

    @Test
    public void testPlayerHands() {
        assertEquals("1 2 3 4 ", player1.playerHands());
    }

    @Test
    public void testCheckWin() throws IOException {
        assertEquals(0, player1.getWinner());
        assertEquals(0, player2.getWinner());
        player1.checkWin();
        player2.checkWin();
        assertEquals(0, player1.getWinner());
        assertEquals(2, player2.getWinner());
    }

    @Test
    public void testCardAction() throws IOException {
        // Test logic for CardAction method
        // This may require mocking or additional setup to handle interactions with Deck
    }

    @Test
    public void testAddCard() {
        player1.addCard(new Card(3));
        assertEquals("1 2 3 4 3 ", player1.playerHands());
        player1.getPlayerCards().remove(player1.getPlayerCards().size() - 1); // Removing the last card added
    }

    @After
    public void cleanUp() {
        player1 = null;
        player2 = null;
        player3 = null;
        correct1 = null;
        deck = null;
        invalidDeck = null;
        game = null;
    }
}