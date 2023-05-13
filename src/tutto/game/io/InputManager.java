package tutto.game.io;

import tutto.components.dice.DiceSet;
import tutto.game.Player;

import java.util.List;

public interface InputManager {

    List<Player> createPlayers(int minPlayers, int maxPlayers);

    /**
     * Asks how many points should be needed to win the game and gets the input from the console.
     *
     * @return The entered amount of points needed to win.
     */
    int getWinPoints();

    void checkKeep(String name, DiceSet diceSet);

    void checkKeepStraight(String name, DiceSet diceSet);

    void roll_or_Display(List<Player> playerList);

    boolean roll_or_End();

}
