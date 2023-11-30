import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Deck extends Pack{
    private ArrayList<Card> deck;
    private int deckIndex;
    private String file;

    /**
     * Constructs a new deck with the specified index.
     *
     * @param deckIndex The index of the deck
     */
    public Deck(int deckIndex){
        this.deck = new ArrayList<>();
        this.deckIndex = deckIndex;
        this.file = "deck" + deckIndex + "_output.txt"; // Create the output file name
        createLogFile(); // Create an empty log file for the deck
    }
    
    /**
     * Creates an empty log file for the deck.
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
     * Gets the index of the deck.
     *
     * @return The index of the deck
     */
    public int getDeckIndex() {
        return deckIndex;
    }

    /**
     * Gets the list of cards in the deck.
     *
     * @return The list of cards in the deck
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Adds a card to the deck.
     *
     * @param card The card to add to the deck
     */
    public void addCard(Card card) {
        deck.add(card);
    }

    /**
     * Draws the top card from the deck.
     *
     * @return The top card of the deck
     */
    public Card drawCard() {
        Card card = deck.get(0);
        deck.remove(card);
        return card;
    }

    /**
     * Logs the current state of the deck to the output file.
     *
     * @throws IOException if an error occurs while writing to the file
     */
    public void logDeck() throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("deck" + deckIndex + " contents: " + deckToString());
        }
    }

    /**
     * Converts the deck to a string for logging purposes.
     *
     * @return String representation of the deck
     */
    private String deckToString() {
        StringBuilder result = new StringBuilder();
        for (Card card : deck) {
            result.append(card.getValue()).append(" ");
        }
        return result.toString().trim();
    }
}