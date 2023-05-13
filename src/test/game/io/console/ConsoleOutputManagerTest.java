package game.io.console;

import org.junit.jupiter.api.Test;
import tutto.components.dice.DiceDots;
import tutto.game.Player;
import tutto.game.io.IOManagerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleOutputManagerTest {

    private static final String SEPARATOR = System.getProperty("line.separator");

    @Test
    public void printWinnersAssertion() {
        List<Player> winnerPlayers = new ArrayList<>();

        assertThrows(AssertionError.class, () -> IOManagerFactory.getOutputmanager().printWinners(winnerPlayers));
    }

    @Test
    public void printWinnersOutputOne() {
        List<Player> winnerPlayers = new ArrayList<>();
        winnerPlayers.add(new Player("Bplayer"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        IOManagerFactory.getOutputmanager().printWinners(winnerPlayers);

        assertEquals("Bplayer wins with 0 points." + SEPARATOR, outContent.toString());

    }

    @Test
    public void printWinnersOutputTie() {
        List<Player> winnerPlayers = new ArrayList<>();
        winnerPlayers.add(new Player("Aplayer"));
        winnerPlayers.add(new Player("Bplayer"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        IOManagerFactory.getOutputmanager().printWinners(winnerPlayers);

        assertEquals("It's a tie! There are 2 winners!" + SEPARATOR +
                        "Aplayer wins with 0 points." + SEPARATOR +
                        "Bplayer wins with 0 points." + SEPARATOR,
                outContent.toString());

    }

    @Test
    public void printLosers() {
        List<Player> loserPlayers = new ArrayList<>();
        loserPlayers.add(new Player("Aplayer"));
        loserPlayers.add(new Player("Bplayer"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        IOManagerFactory.getOutputmanager().printLosers(loserPlayers);

        assertEquals("Aplayer loses with 0 points." + SEPARATOR +
                        "Bplayer loses with 0 points." + SEPARATOR,
                outContent.toString());
    }

    @Test
    public void printDiceDots() {
        List<DiceDots> diceDotsList = new ArrayList<>();
        diceDotsList.add(DiceDots.ONE);
        diceDotsList.add(DiceDots.TWO);
        diceDotsList.add(DiceDots.THREE);
        diceDotsList.add(DiceDots.FOUR);
        diceDotsList.add(DiceDots.FIVE);
        diceDotsList.add(DiceDots.SIX);


        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        IOManagerFactory.getOutputmanager().printDiceDots("Aplayer", diceDotsList);

        assertEquals("[Aplayer]              ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐" + SEPARATOR +
                        "[Aplayer] Your dice:   | 1 | | 2 | | 3 | | 4 | | 5 | | 6 |" + SEPARATOR +
                        "[Aplayer]              └---┘ └---┘ └---┘ └---┘ └---┘ └---┘" + SEPARATOR +
                        "[Aplayer]               (1)   (2)   (3)   (4)   (5)   (6) " + SEPARATOR,
                outContent.toString());
    }

    @Test
    public void displayScores() {
        List<Player> playerList = new ArrayList<>();

        try {
            Player p_one = new Player("Aplayer");
            Field scoreField = Player.class.getDeclaredField("totalPoints");
            scoreField.setAccessible(true);
            scoreField.set(p_one, 2500);

            playerList.add(p_one);
            playerList.add(new Player("Bplayer"));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        IOManagerFactory.getOutputmanager().displayScores(playerList);

        assertEquals("\nCURRENT SCORES:" + SEPARATOR +
                        "[Aplayer] has 2500 points." + SEPARATOR +
                        "[Bplayer] has 0 points." + SEPARATOR + "\n"
                , outContent.toString());
    }

}
