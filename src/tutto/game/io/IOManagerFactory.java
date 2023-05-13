package tutto.game.io;

import tutto.game.io.console.ConsoleInputManager;
import tutto.game.io.console.ConsoleOutputManager;

public class IOManagerFactory {

    private static final InputManager INPUTMANAGER = new ConsoleInputManager();
    private static final OutputManager OUTPUTMANAGER = new ConsoleOutputManager();

    public static InputManager getInputManager() {
        return INPUTMANAGER;
    }

    public static OutputManager getOutputmanager() {
        return OUTPUTMANAGER;
    }
}
