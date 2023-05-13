package tutto.game;

import tutto.components.deck.CardComposite;
import tutto.components.deck.card.Card;
import tutto.components.deck.card.type.PlusMinusCard;
import tutto.components.dice.DiceSet;
import tutto.game.io.IOManagerFactory;
import tutto.game.io.InputManager;

import java.util.List;


public class Player {

    /**
     * The Player's name
     */
    private final String name;

    /**
     * The points the player got in total.
     * Used to check for whether the player has won the game.
     */
    private int totalPoints;

    public Player(String name) {
        this.name = name;
        totalPoints = 0;
    }

    /**
     * @return Name of the Player.
     */
    public String getName() {
        return name;
    }

    /**
     * Method which gets called when the player begins the turn.
     * Package-private.
     */
    void turn(CardComposite deck, DiceSet diceSet, List<Player> playerList) {
        InputManager cim = IOManagerFactory.getInputManager();
        int currentPoints = 0;
        int returnPoints;

        // loop to play as many times as wanted; 1 loop per Tutto
        while (true) {
            Card nextCard = deck.drawCard();
            System.out.println("[" + name + "] " + nextCard.getName() + " drawn.");
            returnPoints = nextCard.executeTurn(name, diceSet);

            // no points scored => all previously earned points lost & turn is over.
            if (returnPoints == 0) {
                currentPoints = 0;
                break;
            }//
            // PlusMinus card drawn
            else if (nextCard.getClass() == PlusMinusCard.class) {
                if (returnPoints == 1000) {
                    int max = findMaxPoints(playerList);
                    for (Player p : playerList) {
                        if (this != p && p.getTotalPoints() == max) {
                            System.out.println("[" + p.getName() + "] has lost " + 1000 + " points.");
                            p.addTotalPoints(-1000);
                        }
                    }
                    addTotalPoints(1000);
                }
            }
            // update score
            else {
                currentPoints += returnPoints;
            }
            if (!diceSet.checkTutto()) {
                break;
            }
            System.out.println("[" + name + "] It's a Tutto! You can play again.");
            System.out.println("Points you've accumulated so far: " + currentPoints);
            diceSet.removeKeeping();
            //True: if Roll, False: if End turn
            if (!cim.roll_or_End()) {
                break;
            }
        }
        diceSet.removeKeeping();
        addTotalPoints(currentPoints);
        System.out.println("[" + name + "] Your turn has ended.");
    }

    /**
     * Helper Method to find highest player score
     *
     * @param playerList List of players.
     */
    private int findMaxPoints(List<Player> playerList) {
        int max = 0;
        for (Player p : playerList) {
            if (p.getTotalPoints() > max) {
                max = p.getTotalPoints();
            }
        }
        return max;
    }

    /**
     * Method to add points to the Player's total points.
     *
     * @param amount Amount of points to add.
     */
    private void addTotalPoints(int amount) {
        totalPoints += amount;
        System.out.println("[" + name + "] You have gained " + amount + " points (total: " + totalPoints + ")!");
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;
        return this.name.equals(((Player) obj).getName());
    }

    /**
     * Getter method for total points.
     *
     * @return The (total) points the Player has.
     */
    public int getTotalPoints() {
        return totalPoints;
    }

}
