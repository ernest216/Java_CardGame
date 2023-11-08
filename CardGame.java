import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CardGame {

    private ArrayList<Player> players;
    private ArrayList<Deck> decks;
        /**
     * Function to create a player in the game
     * 
     * @param n  the number of players
     */
    public void createPlayer(int n) {
        players.add(new Player(n));
    }

    /**
     * Function to create a card deck in the game
     * 
     * @param n the card deck number
     */
    public void createDeck(int n) {
        decks.add(new Deck(n));
    }
}
