package components.deck.card.type;

import org.junit.jupiter.api.Test;
import tutto.components.deck.card.type.CloverleafCard;
import tutto.components.dice.DiceDots;
import tutto.components.dice.DiceSet;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CloverleafCardTest {

    private final String SEPARATOR = System.getProperty("line.separator");

    @Test
    public void getName() {
        CloverleafCard card = new CloverleafCard();
        assertEquals("Cloverleaf", card.getName());

    }

    @Test
    public void executeTurnTest() {
        ArrayList<DiceDots> dots = new ArrayList<>(6);
        boolean[] keep;
        boolean[] triplet;
        DiceSet diceSet = new DiceSet();

        dots.add(DiceDots.TWO);
        dots.add(DiceDots.THREE);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.SIX);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.SIX);

        keep = new boolean[]{true, true, true, true, true, true};
        triplet = new boolean[]{false, false, false, false, false, false};
        diceSet.setDice(dots, keep, triplet);
        CloverleafCard clover = new CloverleafCard();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertEquals(0, clover.executeTurn("Test", diceSet));

        assertEquals("[Test]              ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐" + SEPARATOR +
                "[Test] Your dice:   | 2 | | 3 | | 4 | | 6 | | 4 | | 6 |" + SEPARATOR +
                "[Test]              └---┘ └---┘ └---┘ └---┘ └---┘ └---┘" + SEPARATOR +
                "[Test]               (1)   (2)   (3)   (4)   (5)   (6) " + SEPARATOR +
                "No valid roll" + SEPARATOR +
                "You didn't achieve 2 Tuttos in a row!" + SEPARATOR +
                "[Test] Your turn has ended." + SEPARATOR, outContent.toString());


    }
}
