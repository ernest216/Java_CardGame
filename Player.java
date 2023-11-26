import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Player extends Thread {
    private int playerIndex;
    private ArrayList<Card> playerCards;
    private Deck draw;
    private Deck discard;
    private String file;
    private CardGame game;
    private volatile int winner;

    /* Player class object constructor */
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

    private void createLogFile() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.close(); // Close immediately to create an empty file
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    /* Logs the current data to a file
     * @throws IOException if an error occurs while writing to the file
     */
    public void log(String action) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true); // Open file in append mode
        fileWriter.write(action + "\n");
        fileWriter.close();
    }


    /* Gets the player ID associated with this instance
     * @return the player ID
     */
    public int getPlayerIndex() {
        return this.playerIndex;
    }


    /* Gets  */
    public ArrayList<Card> getPlayerCards() {
        return this.playerCards;
    }


    /* */
    public void setWinner(int winner) {
        this.winner = winner;
    }


    /* */
    public int getWinner() {
        return winner;
    }

    /* Function that returna string of the values of cards in a player's hand 
     * With format e.g. (1 2 3 4)
    */
    public String playerHands() {
        String playerHands = "";
        for (int i=0; i < getPlayerCards().size(); i++) {
            playerHands += getPlayerCards().get(i).getValue() + " ";
        }
        return playerHands;
    } 

    /* Function that checks if player has a winning hand
     * 
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

    /* toString method to include player information */
    public String toString() {
        return "Player number is " + getPlayerIndex() + " with current hand " + getPlayerCards();
    }

    /* Synchronised method to draw/discard cards */
    // Each turn a player draws a card from the top of the deck and discards to the bottom of the deck
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

    
    public void addCard(Card card) {
        playerCards.add(card);
    }

    /* Function that runs a player thread to play the game */
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
            if (winner == playerIndex) {
                String message = "\nPlayer " + playerIndex + " wins" + "\nPlayer " + playerIndex + " exits" + "\nPlayer " + playerIndex + " final hand: " + playerHands();
                log(message + "\n");
            }

            else {
                String message = "\nPlayer " + winner + " has informed " + playerIndex + " that player " + winner + " has won" + "\nPlayer " + playerIndex + " exits" + "\nPlayer " + playerIndex + " hand: " + playerHands();
                log(message + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    