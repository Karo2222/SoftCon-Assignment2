package tutto.components.deck.card.type;

import tutto.components.deck.card.Card;
import tutto.components.deck.card.ConcreteCard;
import tutto.components.dice.DiceSet;

public class StopCard extends ConcreteCard {

    public StopCard() {
        super("StopCard");
    }

    @Override
    public Card copy() {
        return new StopCard();
    }

    @Override
    public int executeTurn(String name, DiceSet diceSet) {
        System.out.println("[" + name + "] Your turn has ended because you drew the " + getName());
        return 0;
    }

}
