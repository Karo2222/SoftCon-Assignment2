package tutto.game;

import tutto.components.deck.CardComposite;
import tutto.components.deck.Deck;
import tutto.components.deck.card.Card;
import tutto.components.deck.card.type.CloverleafCard;
import tutto.components.dice.DiceSet;
import tutto.game.io.IOManagerFactory;
import tutto.game.io.InputManager;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final List<Player> players = new ArrayList<>();

    /**
     * The points which are needed to win the game.
     */
    private final int winningPoints;

    private final CardComposite deck = new Deck();
    private final DiceSet diceSet = new DiceSet();

    /**
     * Constructor for the Game.
     * Lets the InputManager create the Players.
     * Lets the InputManager set the points needed to win.
     */
    Game() {
        //The List of all Players which play the game.
        int minPlayers = 2;
        int maxPlayers = 4;
        players.addAll(IOManagerFactory.getInputManager().createPlayers(minPlayers, maxPlayers));
        winningPoints = IOManagerFactory.getInputManager().getWinPoints();
    }

    /**
     * Method to start the game.
     * Starts the Game loop.
     */
    void start() {
        boolean cloverleaf_win = false;
        while (true) {
            for (Player p : players) {
                InputManager cim = IOManagerFactory.getInputManager();
                System.out.println("[" + p.getName() + "]'s turn");
                cim.roll_or_Display(players);
                Card peeked = deck.peekCard();
                if (peeked.getClass() == CloverleafCard.class) {
                    Card cloverleaf = deck.drawCard();
                    System.out.println("[" + p.getName() + "] has drawn the Cloverleaf card");
                    if (cloverleaf.executeTurn(p.getName(), diceSet) == 2) {
                        cloverleaf_End(p);
                        cloverleaf_win = true;
                        break;
                    }
                    diceSet.removeKeeping();
                } else {
                    p.turn(deck, diceSet, players);
                }
                IOManagerFactory.getOutputmanager().displayScores(players);
            }
            if (cloverleaf_win) {
                return;
            }
            if (checkEnd()) {
                endGame();
                break;
            }
        }
    }

    /**
     * Checks if a Player's points are greater than the amount of points needed to win.
     *
     * @return True if at least 1 Player has more points than needed to win.
     */
    private boolean checkEnd() {
        for (Player p : players) {
            if (p.getTotalPoints() >= winningPoints) return true;
        }
        return false;
    }

    /**
     * Determines which Player(-s) win the game and which not.
     * Lets the OutputManager print the winners and losers.
     * Gets called only after a Player got more points than needed to win.
     */
    private void endGame() {
        int maxPoints = 0;
        for (Player p : players) {
            int totalPoints = p.getTotalPoints();
            if (totalPoints > maxPoints) {
                maxPoints = totalPoints;
            }
        }
        List<Player> winners = new ArrayList<>();
        List<Player> losers = new ArrayList<>();
        for (Player p : players) {
            if (p.getTotalPoints() == maxPoints) {
                winners.add(p);
            } else {
                losers.add(p);
            }
        }

        IOManagerFactory.getOutputmanager().printWinners(winners);
        IOManagerFactory.getOutputmanager().printLosers(losers);
    }

    private void cloverleaf_End(Player p) {
        System.out.println("[" + p.getName() + "] has successfully accomplished a Tutto twice in a row after drawing the CloverLeaf Card.");
        System.out.println("[" + p.getName() + "] wins the game!");
    }

}
