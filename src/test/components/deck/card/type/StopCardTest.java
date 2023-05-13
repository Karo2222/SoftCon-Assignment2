package components.deck.card.type;

import org.junit.jupiter.api.Test;
import tutto.components.deck.card.type.StopCard;
import tutto.components.dice.DiceSet;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class StopCardTest {

    private static final String SEPARATOR = System.getProperty("line.separator");

    @Test
    public void getName() {
        StopCard card = new StopCard();
        assertEquals("StopCard", card.getName());
    }

    @Test
    public void executeTurnTest() {
        DiceSet diceSet = new DiceSet();
        StopCard stopCard = new StopCard();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertEquals(0, stopCard.executeTurn("Test", diceSet));
        assertEquals("[Test] Your turn has ended because you drew the StopCard" + SEPARATOR
                , outContent.toString());
    }
}
