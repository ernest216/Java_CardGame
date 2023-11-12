import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player{
    private int playerIndex;
    private ArrayList<Card> playerCards;
    private Deck drawDeck;
    private Deck discardDeck;
    private String playerOutput;


    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.playerCards = new ArrayList<>();

    }

    /*public void setDrawDeck(Deck drawDeck) {
        this.drawDeck = drawDeck;
    }

    public void setDiscardDeck(Deck discardDeck) {
        this.discardDeck = discardDeck;
    }*/

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

    public void addCard(Card card) {
        playerCards.add(card);
    }


    /* */
    public void win(int winner) {
        this.winner = win;
    }


    /* */
    public int getWinner() {
        return win;
    }


}



