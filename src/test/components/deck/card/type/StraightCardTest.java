package components.deck.card.type;

import org.junit.jupiter.api.Test;
import tutto.components.deck.card.type.StraightCard;
import tutto.components.dice.DiceDots;
import tutto.components.dice.DiceSet;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StraightCardTest {

    private static final String SEPARATOR = System.getProperty("line.separator");

    @Test
    public void getName() {
        StraightCard card = new StraightCard();
        assertEquals("StraightCard", card.getName());
    }

    @Test
    public void executeTurnTest() {
        StraightCard straight = new StraightCard();
        DiceSet diceSet = new DiceSet();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ArrayList<DiceDots> diceDots = new ArrayList<>(6);

        diceDots.add(DiceDots.FOUR);
        diceDots.add(DiceDots.FIVE);
        diceDots.add(DiceDots.TWO);
        diceDots.add(DiceDots.TWO);
        diceDots.add(DiceDots.THREE);
        diceDots.add(DiceDots.ONE);

        boolean[] keep;
        boolean[] triplet;
        keep = new boolean[]{true, true, true, true, true, true};
        triplet = new boolean[]{false, false, false, false, false, false};
        diceSet.setDice(diceDots, keep, triplet);
        assertEquals(0, straight.executeTurn("Test", diceSet));
        assertEquals("[Test]              ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐" + SEPARATOR +
                        "[Test] Your dice:   | 4 | | 5 | | 2 | | 2 | | 3 | | 1 |" + SEPARATOR +
                        "[Test]              └---┘ └---┘ └---┘ └---┘ └---┘ └---┘" + SEPARATOR +
                        "[Test]               (1)   (2)   (3)   (4)   (5)   (6)\s" + SEPARATOR +
                        "No dice that you can keep for your straight => no points" + SEPARATOR
                , outContent.toString());
    }

}
