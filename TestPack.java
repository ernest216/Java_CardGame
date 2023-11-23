import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class PackTest {
    private Pack pack;

    @Before
    public void setUp() {
        pack = new Pack();
        // Assuming you have a method to add cards to the pack
        // for example: pack.addCards();
    }

    @Test
    public void testPackInitialization() {
        assertNotNull("Pack should be initialized", pack.getPack());
        // Replace 52 with the expected number of cards in a new pack
        assertEquals("Pack should have 52 cards initially", 52, pack.getPackSize());
    }

    @Test
    public void testDeliverCard() {
        int initialSize = pack.getPackSize();
        Card card = pack.deliverCard();
        assertNotNull("Delivered card should not be null", card);
        assertEquals("Pack size should decrease by 1", initialSize - 1, pack.getPackSize());
    }

    @Test
    public void testDeliverCardFromEmptyPack() {
        // Remove all cards from the pack
        while (!pack.getPack().isEmpty()) {
            pack.deliverCard();
        }
        assertNull("Delivering a card from an empty pack should return null", pack.deliverCard());
    }

    // Additional tests can be added as needed
}
