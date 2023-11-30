/**
 * Represents a card in the card game.
 */
public class Card {

    private int value; // The numerical value of the card

    /**
     * Gets the value of the card.
     *
     * @return The numerical value of the card
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Constructs a new Card with the specified value.
     *
     * @param value The numerical value of the card
     */
    public Card(int value) {
        this.value = value;
    }
}

