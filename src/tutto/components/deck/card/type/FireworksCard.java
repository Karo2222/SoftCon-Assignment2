package tutto.components.deck.card.type;

import tutto.components.deck.card.Card;
import tutto.components.deck.card.ConcreteCard;
import tutto.components.dice.DiceSet;

public class FireworksCard extends ConcreteCard {

    public FireworksCard() {
        super("FireworksCard");
    }

    @Override
    public Card copy() {
        return new FireworksCard();
    }

    @Override
    public int executeTurn(String name, DiceSet diceSet) {
        int currentPoints = 0;
        while (true) {
            diceSet.rollUnkept();
            com.printDiceDots(name, diceSet.getNumbers());
            if (!diceSet.checkValidRoll()) {
                System.out.println("No valid dice the user could keep => keep points you accumulated so far.");
                return currentPoints + diceSet.calcPoints();
            }
            diceSet.FireworksCard_keepValidDice();
            if (diceSet.checkTutto()) {
                int TuttoPoints = diceSet.calcPoints();
                currentPoints += TuttoPoints;
                System.out.println("You have a Tutto and managed to get " + TuttoPoints + " points!");
                diceSet.removeKeeping();
            }
            System.out.println("You have to keep rolling until you roll a Null!");
        }
    }

}
