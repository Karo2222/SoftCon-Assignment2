package tutto.components.deck.card.type;

import tutto.components.deck.card.Card;
import tutto.components.deck.card.ConcreteCard;
import tutto.components.dice.DiceSet;

public class DoubleCard extends ConcreteCard implements Card {

    public DoubleCard() {
        super("DoubleCard");
    }

    @Override
    public Card copy() {
        return new DoubleCard();
    }

    @Override
    public int executeTurn(String name, DiceSet diceSet) {
        while (true) {
            diceSet.rollUnkept();
            com.printDiceDots(name, diceSet.getNumbers());
            if (!diceSet.checkValidRoll()) {
                System.out.println("No valid dice the user could keep => lost your points");
                return 0;
            }
            cim.checkKeep(name, diceSet);
            if (diceSet.checkTutto()) {
                return diceSet.calcPoints() * 2;
            }
            if (!cim.roll_or_End()) {
                return diceSet.calcPoints();
            }
        }
    }

}
