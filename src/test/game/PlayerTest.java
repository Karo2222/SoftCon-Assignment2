package game;

import org.junit.jupiter.api.Test;
import tutto.game.Player;

import static org.junit.jupiter.api.Assertions.*;

import tutto.components.dice.Dice;

public class PlayerTest {
    @Test
    public void equals() {
        Player p_one = new Player("Person1");
        Player p_two = new Player("Person1");
        Player p_three = new Player("Person2");
        Player p_four = null;
        assertEquals(p_one, p_two);
        assertNotEquals(p_one, p_three);
        assertNotEquals(p_one, p_four);

        Dice d = new Dice();
        assertNotEquals(p_one, d);
    }

    @Test
    public void getName() {
        Player p_one = new Player("Name1");
        assertEquals("Name1", p_one.getName());
    }

    @Test
    public void getTotalPoints() {
        Player p = new Player("Player");
        assertEquals(0, p.getTotalPoints());
    }

    @Test
    public void hashCodeTest() {
        Player p1 = new Player("Person");
        Player p2 = new Player("Person");
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
