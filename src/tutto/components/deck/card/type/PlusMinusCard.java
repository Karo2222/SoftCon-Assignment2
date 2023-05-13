package tutto.components.deck.card.type;

import tutto.components.deck.card.Card;
import tutto.components.deck.card.ConcreteCard;
import tutto.components.dice.DiceSet;

public class PlusMinusCard extends ConcreteCard {

    public PlusMinusCard() {
        super("PlusMinusCard");
    }

    @Override
    public Card copy() {
        return new PlusMinusCard();
    }

    @Override
    public int executeTurn(String name, DiceSet diceSet) {
        while (true) {
            diceSet.rollUnkept();
            com.printDiceDots(name, diceSet.getNumbers());
            if (!diceSet.checkValidRoll()) {
                System.out.println("No valid dice the user could keep => no Tutto, so no points");
                return 0;
            }
            cim.checkKeep(name, diceSet);
            if (diceSet.checkTutto()) {
                System.out.println("You got a Tutto so points will be deducted from the player(s) with the highest points (unless it's [" + name + "]).");
                return 1000;
            }
            System.out.println("No Tutto and no Null roll yet, keep rolling.");
        }
    }

}
