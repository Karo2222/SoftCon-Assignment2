package components.dice;

import org.junit.jupiter.api.Test;
import tutto.components.dice.Dice;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiceTest {

    @Test
    public void roll() {
        Dice d = new Dice();
        d.roll();
        int number = d.getNumber().getNumber();
        assert number > 0 && number <= 6;
    }

    @Test
    public void tripletAndKeepingTest() {
        Dice d = new Dice();
        d.roll();
        assertFalse(d.isKept());
        assertFalse(d.isInTriplet());
        d.setKeeping(true, true);
        assertTrue(d.isKept());
        assertTrue(d.isInTriplet());
        d.setKeeping(true, false);
        assertTrue(d.isKept());
        assertFalse(d.isInTriplet());
    }

}
