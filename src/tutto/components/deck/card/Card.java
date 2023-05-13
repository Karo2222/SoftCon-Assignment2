package tutto.components.deck.card;

import tutto.components.dice.DiceSet;
import tutto.game.io.IOManagerFactory;
import tutto.game.io.InputManager;
import tutto.game.io.OutputManager;

public interface Card {

    InputManager cim = IOManagerFactory.getInputManager();
    OutputManager com = IOManagerFactory.getOutputmanager();

    Card copy();

    int executeTurn(String name, DiceSet diceSet);

    String getName();
}
