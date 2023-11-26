import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Deck extends Pack{
    private ArrayList<Card> deck;
    private int deckIndex;
    private String file;

    public Deck(int deckIndex){
        this.deck = new ArrayList<>();
        this.deckIndex = deckIndex;
        this.file = "deck" + deckIndex + "_output.txt";
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


    public int getDeckIndex(){
        return deckIndex;
    }

    public ArrayList<Card> getDeck(){
        return deck;
    }

    public void addCard(Card card){
        deck.add(card);
    }

    /**
    * Function that takes the top card from the the deck and returns it
    * 
    * @return the top card of the card deck
    */
    public Card drawCard(){
        Card card = deck.get(0);
        deck.remove(card);
        return card;
    }
    /**
     * Log the current state of the deck to the output file
     * 
     * @throws IOException if an error occurs while writing to the file
     */
    public void logDeck() throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("deck" + deckIndex + " contents: " + deckToString());
        }
    }

    /**
     * Convert the deck to a string for logging purposes
     * 
     * @return string representation of the deck
     */
    private String deckToString() {
        StringBuilder result = new StringBuilder();
        for (Card card : deck) {
            result.append(card.getValue()).append(" ");
        }
        return result.toString().trim();
    }

}
