package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith (Suite.class)

@Suite.SuiteClasses ({
    TestCard.class,
    TestDeck.class,
    TestPack.class,
    TestPlayer.class,
    TestCardGame.class
})

public class TestSuite{

}