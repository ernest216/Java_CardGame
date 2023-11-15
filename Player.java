import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Player extends Thread {
    private int playerIndex;
    private ArrayList<Card> playerCards;
    private CardDeck preferredDeck;
    private String playerOutput;


    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.playerCards = new ArrayList<>();
        this.preferredDeck = decks.get(playerIndex - 1); // Assuming player indices start from 1
    }
    /* Logs the current data to a file
     * @throws IOException if an error occurs while writing to the file
     */
    public void log() throws IOException {
       
        //create a new FileWriter Instance
        FileWriter newFile = new FileWriter();
       
        //Write the log data to the file
        newFile.write(logs);


        //Close the FileWriter
        newFile.close();
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
    public void win(int winner) {
        this.winner = winner;
    }


    /* */
    public int getWinner() {
        return winner;
    }

    /* Function that returna string of the values of cards in a player's hand 
     * With format e.g. (1 2 3 4)
    */
    public String playerCardsTotal() {
        String playerCardsTotal = "";
        for (int i=0; i < getPlayerCards().size(); i++) {
            playerCardsTotal += getPlayerCards().getValue() + " ";
        }
        return playerCardsTotal;
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
        for (int i = 1; i < 4; i++) {
            if (value != playerCards.get(i).getValue()) {
                return;
            }
        }

        synchronized(this) {
            System.out.printIn("Player " + playerIndex + " is the winner");
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
        ArrayList<Card> unwanted = new ArrayList<>();

        for (int i=0; i<4; i++) {
            card n = playerCards.get(i);
            if (n.getValue() != playerIndex) {
                unwanted.add(n);
            }
        }
    
    Collections.shuffle(unwanted);

    Card cardToDiscard = unwanted.get(0);

    discard.addCardDeck(cardToDiscard);

    playerCards.remove(cardToDiscard);

    String discardMessage = "Player " + playerIndex + "discards " + cardToDiscard.getValue() + " to deck " + disc.getDeckNum();
    logs += drawMessage + "\n";

    String message = "Player " + playerIndex + "'s current hand is " + CardAction();
    logs += message + "\n\n";
    }

    /* Function that fills player's hand with cards */
    public void fillHand(Card card) {
        playerCards.add(card);
    }

    /* Player class object constructor */
    public Player(int playerIndex, Pack.CardDeck draw, Pack.CardDeck discard, CardGame game) {
        this.playerIndex = playerIndex;
        this.playerCards = new ArrayList<Card>();
        this.fileName = "player" + playerNum + "_output.txt";
        this.draw = draw;
        this.discard = discard;
        this.game = game;
        this.winner = 0;
        this.logs = "";
    }

    /* Function that runs a player thread to play the game */
    public void run() {
        logs += "player " + playerIndex + " initial hand " + cardAction() + "\n\n";

        //@while there is no winner 
        while (winner == 0) {
            try {
                cardDrawDiscard();
                checkWin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (winner = playerIndex) {
                String message = "Player " + playerIndex + " wins" + "\nPlayer " + playerIndex + " exits" + "\nPlayer " + playerIndex + " final hand: " + cardAction();
                logs += message + "\n";
            }

            else {
                String message = "Player " + winner + " has informed " + playerIndex + " that player " + winner + " has won" + "\nPlayer " + playerIndex + " exits" + "\nPlayer " + playerIndex + " final hand: " + cardAction();
                logs += message + "\n";
            }

            log();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
