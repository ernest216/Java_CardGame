import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a player in the card game.
 */
public class Player extends Thread {
    private int playerIndex;
    private ArrayList<Card> playerCards;
    private Deck draw;
    private Deck discard;
    private String file;
    private CardGame game;
    private volatile int winner;

    /**
     * Constructs a new player with the specified attributes.
     *
     * @param playerIndex Unique identifier for the player
     * @param draw        The deck from which the player draws cards
     * @param discard     The deck to which the player discards cards
     * @param game        Reference to the card game
     */
    public Player(int playerIndex, Deck draw, Deck discard, CardGame game) {
        this.playerIndex = playerIndex;
        this.playerCards = new ArrayList<Card>();
        this.file = "player" + playerIndex + "_output.txt";
        this.draw = draw;
        this.discard = discard;
        this.game = game;
        this.winner = 0;
        createLogFile();
    }

    /**
     * Creates an empty log file for the player.
     */

    private void createLogFile() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close(); // Close immediately to create an empty file
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    /**
     * Logs an action to the player's log file.
     *
     * @param action The action to be logged
     * @throws IOException if an error occurs while writing to the file
     */
    public void log(String action) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true); // Open file in append mode
        fileWriter.write(action + "\n");
        fileWriter.close();
    }


    /**
     * Gets the player ID associated with this instance.
     *
     * @return the player ID
     */
    public int getPlayerIndex() {
        return this.playerIndex;
    }


     /**
     * Gets the cards in the player's hand.
     *
     * @return ArrayList of cards in the player's hand
     */
    public ArrayList<Card> getPlayerCards() {
        return this.playerCards;
    }


    /**
     * Sets the winner of the game.
     *
     * @param winner The player number of the winning player
     */

    public void setWinner(int winner) {
        this.winner = winner;
    }


    /**
     * Gets the winner of the game.
     *
     * @return The player number of the winner
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Returns a string of the values of cards in a player's hand.
     * Format: (1 2 3 4)
     *
     * @return A string representation of the player's hand
     */

    public String playerHands() {
        String playerHands = "";
        for (int i=0; i < getPlayerCards().size(); i++) {
            playerHands += getPlayerCards().get(i).getValue() + " ";
        }
        return playerHands;
    } 

    /**
     * Checks if the player has a winning hand.
     *
     * @throws IOException if an error occurs while writing to the file
     */
    public void checkWin() throws IOException {
        if (winner !=0) {
            return;
        }
        Card n = playerCards.get(0);
        int value = n.getValue();
        for (int i = 0; i < 4; i++) {
            if (value != playerCards.get(i).getValue()) {
                return;
            }
        }

        synchronized(this) {
            System.out.println("Player " + playerIndex + " wins");
            game.declareWin(playerIndex);
            this.winner = playerIndex;
        }
    }

    /**
     * Returns a string representation of the player including player information.
     *
     * @return String representation of the player
     */
    public String toString() {
        return "Player number is " + getPlayerIndex() + " with current hand " + getPlayerCards();
    }

    /**
     * Synchronized method for drawing and discarding cards.
     * In each turn, a player draws a card from the top of the deck and discards to the bottom of the deck.
     *
     * @throws IOException if an error occurs while writing to the file
     */
    public synchronized void CardAction() throws IOException {
        if (draw.getDeck().size() < 4) {
            return;
        }
        // drawing cards and logging the action of players into their own log files 
        Card card = draw.drawCard();
        playerCards.add(card);
        String drawMessage = "Player " + playerIndex + " draws a " + card.getValue() + " from deck " + draw.getDeckIndex();
        log(drawMessage);
        
        ArrayList<Card> unwanted = new ArrayList<>();
        // getting the discard preference of each player

        for (int i=0; i<4; i++) {
            Card n = playerCards.get(i);
            if (n.getValue() != playerIndex) {
                unwanted.add(n);
            }
        }
        // Shuffle the unwanted cards to randomly discard
        Collections.shuffle(unwanted);
    
        if (!unwanted.isEmpty()) {
            Card cardToDiscard = unwanted.get(0);
            // add to arraaylist directly to discard
            discard.addCard(cardToDiscard);
            playerCards.remove(cardToDiscard);
            String discardMessage = "Player " + playerIndex + " discards a " + cardToDiscard.getValue() + " to deck " + discard.getDeckIndex(); 
            log(discardMessage); 
        }
        // outputting the current hands of each player into their log file
        String message = "Player " + playerIndex + " current hand is " + playerHands();
        log(message);

        checkWin();
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to be added to the player's hand
     */
    public void addCard(Card card) {
        playerCards.add(card);
    }

    /**
     * Runs a player thread to play the game.
     * This method is executed when the player thread is started.
     */
    public void run() {
        String initialMessage = "player " + playerIndex + " initial hand " + playerHands() + "\n";
        try {
            log(initialMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //@while there is no winner 
        while (winner == 0) {
            try {
                CardAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // Check if the player is the winner and log the final message
            if (winner == playerIndex) {
                String message = "\nPlayer " + playerIndex + " wins" + "\nPlayer " + playerIndex + " exits" + "\nPlayer " + playerIndex + " final hand: " + playerHands();
                log(message + "\n");
            }

            else {
                // Log the message when informed about another player winning
                String message = "\nPlayer " + winner + " has informed " + playerIndex + " that player " + winner + " has won" + "\nPlayer " + playerIndex + " exits" + "\nPlayer " + playerIndex + " hand: " + playerHands();
                log(message + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    