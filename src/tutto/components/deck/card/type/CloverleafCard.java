package tutto.components.deck.card.type;

import tutto.components.deck.card.Card;
import tutto.components.deck.card.ConcreteCard;
import tutto.components.dice.DiceSet;

public class CloverleafCard extends ConcreteCard {

    public CloverleafCard() {
        super("Cloverleaf");
    }

    @Override
    public Card copy() {
        return new CloverleafCard();
    }

    @Override
    public int executeTurn(String name, DiceSet diceSet) {
        int amountOfTutto = 0;
        while (true) {
            diceSet.rollUnkept();
            com.printDiceDots(name, diceSet.getNumbers());
            if (!diceSet.checkValidRoll()) {
                System.out.println("No valid roll");
                System.out.println("You didn't achieve 2 Tuttos in a row!");
                System.out.println("[" + name + "] Your turn has ended.");
                return amountOfTutto;
            }
            cim.checkKeep(name, diceSet);
            if (diceSet.checkTutto()) {
                amountOfTutto += 1;
                System.out.println("[" + name + "] You have just rolled a Tutto. You have to keep playing, but if you get another Tutto you win the game!");
                diceSet.removeKeeping();
            }
            if (amountOfTutto == 2) {
                return amountOfTutto;
            }
        }
    }

}
