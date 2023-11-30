import java.util.ArrayList;

/**
 * Represents a pack of cards in the card game.
 */
public class Pack {
    private ArrayList<Card> pack; // List to store cards in the pack

    /**
     * Constructs a new pack and initializes it with cards based on the game logic.
     */
    public Pack() {
        pack = new ArrayList<>(); // Initialize the pack as an empty list
        // Add cards to the pack based on your game logic
        // You might want to add a loop here to add cards to the pack
    }

    /**
     * Gets the list of cards in the pack.
     *
     * @return The list of cards in the pack
     */
    public ArrayList<Card> getPack() {
        return pack;
    }

    /**
     * Gets the size of the pack.
     *
     * @return The size of the pack
     */
    public int getPackSize() {
        return pack.size();
    }

    /**
     * Delivers a card from the top of the pack.
     *
     * @return The card delivered from the pack, or null if the pack is empty
     */
    public Card deliverCard() {
        if (!pack.isEmpty()) {
            Card card = pack.get(0);
            pack.remove(0);
            return card;
        } else {
            // Handle the case when the pack is empty, e.g., by returning null
            return null;
        }
    }
}
