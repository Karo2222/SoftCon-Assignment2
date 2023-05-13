package components.deck.card.type;

import org.junit.jupiter.api.Test;
import tutto.components.deck.card.type.PlusMinusCard;
import tutto.components.dice.DiceDots;
import tutto.components.dice.DiceSet;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlusMinusCardTest {

    private static final String SEPARATOR = System.getProperty("line.separator");

    @Test
    public void getName() {
        PlusMinusCard card = new PlusMinusCard();
        assertEquals("PlusMinusCard", card.getName());
    }

    @Test
    public void executeTurnTest() {
        ArrayList<DiceDots> dots = new ArrayList<>(6);
        boolean[] keep;
        boolean[] triplet;
        DiceSet diceSet = new DiceSet();

        dots.add(DiceDots.ONE);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.SIX);
        dots.add(DiceDots.SIX);
        dots.add(DiceDots.SIX);

        keep = new boolean[]{true, true, true, true, true, true};
        triplet = new boolean[]{false, false, false, false, false, false};
        diceSet.setDice(dots, keep, triplet);
        PlusMinusCard plusMinusCard = new PlusMinusCard();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertEquals(0, plusMinusCard.executeTurn("Test", diceSet));
        assertEquals("[Test]              ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐" + SEPARATOR +
                        "[Test] Your dice:   | 1 | | 5 | | 1 | | 6 | | 6 | | 6 |" + SEPARATOR +
                        "[Test]              └---┘ └---┘ └---┘ └---┘ └---┘ └---┘" + SEPARATOR +
                        "[Test]               (1)   (2)   (3)   (4)   (5)   (6)\s" + SEPARATOR +
                        "No valid dice the user could keep => no Tutto, so no points" + SEPARATOR
                , outContent.toString());
    }
}
