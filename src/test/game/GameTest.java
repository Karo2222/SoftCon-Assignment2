package game;

import org.junit.jupiter.api.Test;
import tutto.game.Game;
import tutto.game.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private static final String SEPARATOR = System.getProperty("line.separator");

    @Test
    public void checkEnd() {

        try {

            System.setIn(new ByteArrayInputStream(
                    """
                            Aplayer
                            Bplayer
                                                
                            20000
                            """
                            .getBytes()));

            Constructor<Game> gameConstructor = (Constructor<Game>) Game.class.getDeclaredConstructors()[0];
            gameConstructor.setAccessible(true);

            Game game = gameConstructor.newInstance();

            Field winningPointsField = Game.class.getDeclaredField("winningPoints");
            winningPointsField.setAccessible(true);
            winningPointsField.set(game, 1000);

            Method checkEndMethod = Game.class.getDeclaredMethod("checkEnd");
            checkEndMethod.setAccessible(true);

            assertFalse((boolean) checkEndMethod.invoke(game));

            Field players = Game.class.getDeclaredField("players");
            players.setAccessible(true);

            Object obj = players.get(game);
            List<Player> pL = (List<Player>) obj;
            Player a_player = pL.get(0);

            Field totalPointsField = Player.class.getDeclaredField("totalPoints");
            totalPointsField.setAccessible(true);
            totalPointsField.setInt(a_player, 20000);

            assertTrue((boolean) checkEndMethod.invoke(game));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void endGame() {
        try {
            System.setIn(new ByteArrayInputStream(
                    """
                            Aplayer
                            Bplayer
                                                
                            20000
                            """
                            .getBytes()));

            Constructor<Game> gameConstructor = (Constructor<Game>) Game.class.getDeclaredConstructors()[0];
            gameConstructor.setAccessible(true);

            Game game = gameConstructor.newInstance();

            Field winningPointsField = Game.class.getDeclaredField("winningPoints");
            winningPointsField.setAccessible(true);

            Field players = Game.class.getDeclaredField("players");
            players.setAccessible(true);

            Object obj = players.get(game);
            List<Player> pL = (List<Player>) obj;
            Player a_player = pL.get(0);

            Field totalPointsField = Player.class.getDeclaredField("totalPoints");
            totalPointsField.setAccessible(true);
            totalPointsField.setInt(a_player, 20000);

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            Method endGameMethod = Game.class.getDeclaredMethod("endGame");
            endGameMethod.setAccessible(true);
            endGameMethod.invoke(game);

            String out = "Aplayer wins with 20000 points." + SEPARATOR +
                    "Bplayer loses with 0 points." + SEPARATOR;
            assertEquals(out, outContent.toString());

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void cloverleaf_End() {

        try {
            System.setIn(new ByteArrayInputStream(
                    """
                            Aplayer
                            Bplayer
                                                
                            20000
                            """
                            .getBytes()));

            Constructor<Game> gameConstructor = (Constructor<Game>) Game.class.getDeclaredConstructors()[0];
            gameConstructor.setAccessible(true);

            Game game = gameConstructor.newInstance();

            Player p = new Player("Aplayer");


            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            Method cloverleaf_EndMethod = Game.class.getDeclaredMethod("cloverleaf_End", Player.class);
            cloverleaf_EndMethod.setAccessible(true);
            cloverleaf_EndMethod.invoke(game, p);

            String out = "[" + p.getName() + "] has successfully accomplished a Tutto twice in a row after drawing the CloverLeaf Card." + SEPARATOR +
                    "[" + p.getName() + "] wins the game!" + SEPARATOR;
            assertEquals(out, outContent.toString());

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
