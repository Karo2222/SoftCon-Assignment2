package components.dice;

import org.junit.jupiter.api.Test;
import tutto.components.dice.DiceDots;
import tutto.components.dice.DiceSet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiceSetTest {
    ArrayList<DiceDots> dots = new ArrayList<>(6);
    boolean[] keep = new boolean[6];
    boolean[] triplet = new boolean[6];
    DiceSet diceSet = new DiceSet();

    @Test
    public void calcPoints_Test1() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.SIX);

        keep = new boolean[]{true, true, true, true, true, false};
        triplet = new boolean[]{true, true, true, false, false, false};

        diceSet.setDice(dots,
                keep,
                triplet
        );
        int correct_points = 1150;
        assertEquals(correct_points, diceSet.calcPoints());
    }

    @Test
    public void calcPoints_Test2() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.FIVE);

        keep = new boolean[]{true, true, true, true, true, true};
        triplet = new boolean[]{true, true, true, true, true, true};

        diceSet.setDice(dots,
                keep,
                triplet
        );
        assertEquals(1500, diceSet.calcPoints());
    }

    @Test
    public void calcPoints_Test3() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);

        keep = new boolean[]{true, true, true, true, true, true};
        triplet = new boolean[]{true, true, true, true, true, true};

        diceSet.setDice(dots,
                keep,
                triplet
        );
        assertEquals(2000, diceSet.calcPoints());
    }

    @Test
    public void FireworksCard_keepValidDiceTest() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.SIX);
        keep = new boolean[]{false, false, false, false, true, false};
        triplet = new boolean[]{false, true, true, true, false, false};
        diceSet.setDice(dots, keep, triplet);
        diceSet.FireworksCard_keepValidDice();
        assertEquals(350, diceSet.calcPoints());

        keep = new boolean[]{false, false, false, false, false, false};
        triplet = new boolean[]{false, true, true, true, false, false};
        diceSet.setDice(dots, keep, triplet);
        diceSet.FireworksCard_keepValidDice();
        assertEquals(350, diceSet.calcPoints());
    }

    @Test
    public void checkTutto() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.FIVE);
        diceSet.setDice(dots, keep, triplet);
        for (int i = 0; i < 6; i++) {
            assertFalse(diceSet.checkTutto());
            keep[i] = true;
        }
        for (int i = 1; i < 4; i++) {
            triplet[i] = true;
        }

        diceSet.setDice(dots, keep, triplet);
        assertTrue(diceSet.checkTutto());

    }

    @Test
    public void getNumbers() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.FIVE);

        diceSet.setDice(dots, keep, triplet);
        List<DiceDots> list_numbers = diceSet.getNumbers();
        for (int i = 0; i < 6; i++) {
            assertEquals(dots.get(i), list_numbers.get(i));
        }
    }

    @Test
    public void getNumberOfDice() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.THREE);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.SIX);

        diceSet.setDice(dots, keep, triplet);
        for (int i = 0; i < 6; i++) {
            assertEquals(dots.get(i), diceSet.getNumber(i));
        }
    }

    @Test
    public void checkValidRoll() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.THREE);
        dots.add(DiceDots.THREE);
        dots.add(DiceDots.THREE);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.SIX);
        keep = new boolean[]{false, false, false, false, false, false};
        triplet = new boolean[]{false, false, false, false, false, false};
        diceSet.setDice(dots, keep, triplet);
        assertTrue(diceSet.checkValidRoll());
    }

    @Test
    public void StraightCard_checkValidRoll_Test1() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.THREE);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        keep = new boolean[]{false, true, false, false, false, false};
        triplet = new boolean[]{false, false, false, false, false, false};
        diceSet.setDice(dots, keep, triplet);
        assertTrue(diceSet.StraightCard_checkValidRoll());
    }

    @Test
    public void StraightCard_checkValidRoll_Test2() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.THREE);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        keep = new boolean[]{false, true, true, true, true, true};
        triplet = new boolean[]{false, false, false, false, false, false};
        diceSet.setDice(dots, keep, triplet);
        assertFalse(diceSet.StraightCard_checkValidRoll());
    }

    @Test
    public void removeKeeping() {
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.THREE);
        dots.add(DiceDots.TWO);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        keep = new boolean[]{true, true, true, true, true, true};
        triplet = new boolean[]{true, true, true, true, true, true};
        diceSet.removeKeeping();
        assertFalse(diceSet.allKept());
    }
}
