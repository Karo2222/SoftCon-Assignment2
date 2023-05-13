package tutto.components.deck.card.type;

import tutto.components.deck.card.Card;
import tutto.components.deck.card.ConcreteCard;
import tutto.components.dice.DiceSet;
import tutto.game.io.IOManagerFactory;

public class StraightCard extends ConcreteCard {

    public StraightCard() {
        super("StraightCard");
    }

    @Override
    public Card copy() {
        return new StraightCard();
    }

    @Override
    public int executeTurn(String name, DiceSet diceSet) {
        while (true) {
            diceSet.rollUnkept();
            com.printDiceDots(name, diceSet.getNumbers());
            if (!diceSet.StraightCard_checkValidRoll()) {
                System.out.println("No dice that you can keep for your straight => no points");
                return 0;
            }
            IOManagerFactory.getInputManager().checkKeepStraight(name, diceSet);
            if (diceSet.checkTutto()) {
                System.out.println("[" + name + "] has a Straight => 2000 points");
                return 2000;
            }
            System.out.println("No straight or null yet => continue rolling");
        }
    }

}
