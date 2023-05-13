package tutto.game.io.console;

import tutto.components.dice.DiceDots;
import tutto.game.Player;
import tutto.game.io.OutputManager;

import java.util.List;

public class ConsoleOutputManager implements OutputManager {

    /**
     * Method to print all winners.
     *
     * @param winnerPlayers All players which win the game. !winnerPlayers.size.isEmpty()
     */
    public void printWinners(List<Player> winnerPlayers) {
        assert !winnerPlayers.isEmpty();
        int winners = winnerPlayers.size();
        if (winners > 1) System.out.println("It's a tie! There are " + winners + " winners!");
        for (Player p : winnerPlayers)
            System.out.println(p.getName() + " wins with " + p.getTotalPoints() + " points.");
    }

    /**
     * Method to print all losers.
     *
     * @param loserPlayers All players which lose the game. Can be empty if all players win.
     */
    public void printLosers(List<Player> loserPlayers) {
        for (Player p : loserPlayers)
            System.out.println(p.getName() + " loses with " + p.getTotalPoints() + " points.");
    }

    public void printDiceDots(String name, List<DiceDots> diceDotsList) {
        System.out.println("[" + name + "]              ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐ ┌---┐");
        System.out.print("[" + name + "] Your dice:  ");
        for (DiceDots diceDots : diceDotsList) {
            System.out.print(" | " + diceDots.getNumber() + " |");
        }
        System.out.println();
        System.out.println("[" + name + "]              └---┘ └---┘ └---┘ └---┘ └---┘ └---┘");
        System.out.println("[" + name + "]               (1)   (2)   (3)   (4)   (5)   (6) ");
    }

    public void displayScores(List<Player> playerList) {
        System.out.println("\nCURRENT SCORES:");
        for (Player p : playerList) {
            System.out.println("[" + p.getName() + "] has " + p.getTotalPoints() + " points.");
        }
        System.out.print("\n");
    }
}
