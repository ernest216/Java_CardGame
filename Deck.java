import java.util.ArrayList;

public class Deck{
    private ArrayList<Card> deck;
    private int deckIndex;
    private String fileName;

    public int getIndex() {
        return deckIndex;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> addCard(){
        deck.add(card);
    }

}
