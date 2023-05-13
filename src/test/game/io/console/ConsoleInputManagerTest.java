package game.io.console;

import org.junit.jupiter.api.Test;
import tutto.components.dice.DiceDots;
import tutto.components.dice.DiceSet;
import tutto.game.Player;
import tutto.game.io.IOManagerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleInputManagerTest {

    private final String SEPARATOR = System.getProperty("line.separator");

    @Test
    public void createPlayersTest_Test1() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream(
                """
                        Bplayer
                        Bplayer
                                        
                        Aplayer
                        """
                        .getBytes()));

        List<Player> createdPlayers = IOManagerFactory.getInputManager().createPlayers(2, 2);

        assertEquals("Please enter the name of the first player:" + SEPARATOR +
                        "Bplayer (Player #1) added." + SEPARATOR +
                        "Please enter the next name." + SEPARATOR +
                        "Please enter a name which hasn't been used yet." + SEPARATOR +
                        "Please enter a non-empty name." + SEPARATOR +
                        "Aplayer (Player #2) added." + SEPARATOR +
                        "Players saved." + SEPARATOR,
                outContent.toString());

        assertEquals(2, createdPlayers.size()); // Double names should not be added; should not end with less than 2 players
        assertEquals("Aplayer", createdPlayers.get(0).getName()); // Player 0 (#1) should be Aplayer (sorted)
        assertEquals("Bplayer", createdPlayers.get(1).getName()); // Player 1 (#2) should be Bplayer (sorted)

    }

    @Test
    public void createPlayersTest_Test2() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream(
                """
                        Cplayer
                        Dplayer
                        Dplayer
                        Eplayer
                                        
                        """
                        .getBytes()));
        List<Player> createdPlayers = IOManagerFactory.getInputManager().createPlayers(2, 4);
        assertEquals("Please enter the name of the first player:" + SEPARATOR +
                        "Cplayer (Player #1) added." + SEPARATOR +
                        "Please enter the next name." + SEPARATOR +
                        "Dplayer (Player #2) added." + SEPARATOR +
                        "Press enter to play with 2 players or enter the next name." + SEPARATOR +
                        "Please enter a name which hasn't been used yet." + SEPARATOR +
                        "Eplayer (Player #3) added." + SEPARATOR +
                        "Press enter to play with 3 players or enter the next name." + SEPARATOR +
                        "Players saved." + SEPARATOR,
                outContent.toString());

        assertEquals(3, createdPlayers.size());

    }

    @Test
    public void getWinPoints() {

        System.setIn(new ByteArrayInputStream(
                """
                        Abc
                                        
                        Zero
                        -1
                        0
                        200
                        """
                        .getBytes()));

        assertEquals(200, IOManagerFactory.getInputManager().getWinPoints());

        System.setIn(new ByteArrayInputStream(Integer.toString(Integer.MAX_VALUE).getBytes()));
        assertEquals(Integer.MAX_VALUE, IOManagerFactory.getInputManager().getWinPoints());

    }

    @Test
    public void checkKeep_Test1() {
        DiceSet diceSet = new DiceSet();
        ArrayList<DiceDots> dots = new ArrayList<>();
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.SIX);
        dots.add(DiceDots.FIVE);
        diceSet.setDice(dots,
                new boolean[]{false, false, false, false, false, false},
                new boolean[]{false, false, false, false, false, false}
        );

        System.setIn(new ByteArrayInputStream(
                """
                        Abc
                        Zero
                        -1
                        0
                        9
                        200
                        1
                        2,3,4
                        1
                        2,3,4
                        1,2,3
                        5
                        2
                        6
                                        
                        """
                        .getBytes()));

        IOManagerFactory.getInputManager().checkKeep("", diceSet);

        assertTrue(diceSet.isKept(0));
        assertTrue(diceSet.isKept(1));
        assertTrue(diceSet.isKept(2));
        assertTrue(diceSet.isKept(3));
        assertFalse(diceSet.isKept(4));
        assertTrue(diceSet.isKept(5));

        diceSet.keep(4, false);
        assertTrue(diceSet.allKept());
    }

    @Test
    public void checkKeep_Test2() {
        DiceSet diceSet = new DiceSet();
        ArrayList<DiceDots> dots = new ArrayList<>();
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.SIX);
        dots.add(DiceDots.FIVE);
        diceSet.setDice(dots,
                new boolean[]{false, false, false, false, false, false},
                new boolean[]{false, true, true, true, false, false});

        System.setIn(new ByteArrayInputStream(
                """
                                        
                        7,1,2
                        2,3,5
                        2,2,3
                        2,3,4
                        """
                        .getBytes()));

        IOManagerFactory.getInputManager().checkKeep("", diceSet);
        assertFalse(diceSet.isKept(0));
        assertTrue(diceSet.isKept(1));
        assertTrue(diceSet.isKept(2));
        assertTrue(diceSet.isKept(3));
        assertFalse(diceSet.isKept(4));
        assertFalse(diceSet.isKept(5));
    }

    @Test
    public void checkKeepStraight() {
        DiceSet ds = new DiceSet();

        ArrayList<DiceDots> dots = new ArrayList<>();
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.ONE);
        dots.add(DiceDots.FIVE);
        dots.add(DiceDots.FOUR);
        dots.add(DiceDots.SIX);

        boolean[] b = new boolean[]{false, false, false, false, false, false};
        ds.setDice(dots, b, b);

        System.setIn(new ByteArrayInputStream(
                """
                        Abc
                        Zero
                                        
                        -1
                        0
                        9
                        200
                        1
                        2,3,4
                        1
                        2,3,4
                        1,2,3
                        5
                        2
                        6
                                        
                        """
                        .getBytes()));

        IOManagerFactory.getInputManager().checkKeepStraight("Aplayer", ds);

        assertTrue(ds.isKept(0));
        assertFalse(ds.isKept(1));
        assertFalse(ds.isKept(2));
        assertFalse(ds.isKept(3));
        assertTrue(ds.isKept(4));
        assertTrue(ds.isKept(5));

    }

    @Test
    public void roll_or_Display() {
        List<Player> playerList = new ArrayList<>();

        System.setIn(new ByteArrayInputStream(
                """
                                        
                        asd
                        r
                        D
                        R
                        asd
                        peter
                        """
                        .getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        IOManagerFactory.getInputManager().roll_or_Display(playerList);

        assertEquals("Enter an 'R' to start rolling the dice or 'D' to display the current scores" + SEPARATOR +
                        "Input was blank, please enter either 'R' or 'D'" + SEPARATOR +
                        "Input was more than just one letter, please enter either 'R' or 'D'" + SEPARATOR +
                        "only input either 'R' or 'D'" + SEPARATOR +
                        "\nCURRENT SCORES:" + SEPARATOR +
                        "\n",
                outContent.toString());
    }

    @Test
    public void roll_or_End_Test1() {
        System.setIn(new ByteArrayInputStream(
                """
                                        
                        asd
                        R
                        """
                        .getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertTrue(IOManagerFactory.getInputManager().roll_or_End());

        assertEquals("Enter an 'R' to continue rolling the remaining dice or 'E' to end your turn and keep the current points." + SEPARATOR +
                        "Input was blank, please enter either 'R' or 'E'" + SEPARATOR +
                        "Input was more than just one letter, please enter either 'R' or 'E'" + SEPARATOR,
                outContent.toString());

    }

    @Test
    public void roll_or_End_Test2() {
        System.setIn(new ByteArrayInputStream(
                """
                        E
                        """
                        .getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertFalse(IOManagerFactory.getInputManager().roll_or_End());

        assertEquals("Enter an 'R' to continue rolling the remaining dice or 'E' to end your turn and keep the current points." + SEPARATOR,
                outContent.toString());
    }

    @Test
    public void roll_or_End_Test3() {
        System.setIn(new ByteArrayInputStream(
                """
                        T
                                        
                        R
                        """
                        .getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertTrue(IOManagerFactory.getInputManager().roll_or_End());

        assertEquals("Enter an 'R' to continue rolling the remaining dice or 'E' to end your turn and keep the current points." + SEPARATOR +
                        "only input either 'R' or 'E'" + SEPARATOR +
                        "Input was blank, please enter either 'R' or 'E'" + SEPARATOR
                ,
                outContent.toString());
    }
}
