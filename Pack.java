import java.util.ArrayList;

public class Pack {
    private ArrayList<Card> pack;

    public Pack() {
        // Initialize your pack and add cards to it
        pack = new ArrayList<>();
        // Add cards to the pack based on your game logic
    }

    public ArrayList<Card> getPack() {
        return pack;
    }

    public int getPackSize() {
        return pack.size();
    }

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
