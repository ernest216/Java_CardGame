import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import 

public class TestPack {
    private Pack pack;

    @Before
    public void setup() {
        pack = new Pack();
    }

    @Test
    public void testPack() {
        assertNotNull("Pack should be created", pack);

    }
}