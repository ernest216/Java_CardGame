import org.junit.Test;
import static org.junit.Assert.*;

public class TestCard() {

    private Card tc0;

    @Before
    public void setup() {
        this.tc0 = new Card(1);
    }

    @Test
    public void testGetValue() {
        assertEquals(tc0.getValue(),1);

    }

    @After
    public void clear() {
        tc0 = null;
    }
}