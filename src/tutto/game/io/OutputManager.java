package tutto.game.io;

import tutto.components.dice.DiceDots;
import tutto.game.Player;

import java.util.List;

public interface OutputManager {
    void printWinners(List<Player> winnerPlayers);

    /**
     * Method to print all losers.
     *
     * @param loserPlayers All players which lose the game. Can be empty if all players win.
     */
    void printLosers(List<Player> loserPlayers);

    void printDiceDots(String name, List<DiceDots> diceDotsList);

    void displayScores(List<Player> playerList);

}
