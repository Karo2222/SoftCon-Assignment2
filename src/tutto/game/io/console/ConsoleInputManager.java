package tutto.game.io.console;

import tutto.components.dice.DiceSet;
import tutto.game.Player;
import tutto.game.io.IOManagerFactory;
import tutto.game.io.InputManager;

import java.util.*;

public class ConsoleInputManager implements InputManager {

    /**
     * Gets input from the console to get 2-4 Player names to create a player list.
     *
     * @return The List of the created Players.
     */
    public List<Player> createPlayers(int minPlayers, int maxPlayers) {
        List<Player> playerList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the name of the first player:");
        while (scanner.hasNextLine()) {

            // empty input given
            String name = scanner.nextLine();
            if (name.isBlank()) {
                if (playerList.size() >= minPlayers) {
                    System.out.println("Players saved.");
                    break;
                }
                System.out.println("Please enter a non-empty name.");
                continue;
            }

            // existing name given
            Player nextPlayer = new Player(name);
            if (playerList.contains(nextPlayer)) {
                System.out.println("Please enter a name which hasn't been used yet.");
                continue;
            }

            playerList.add(nextPlayer);
            System.out.println(name + " (Player #" + playerList.size() + ") added.");

            // maximum number of players reached
            if (playerList.size() == maxPlayers) {
                System.out.println("Players saved.");
                break;
            }

            if (playerList.size() >= minPlayers) {
                System.out.println("Press enter to play with " + playerList.size() + " players or enter the next name.");
                continue;
            }

            System.out.println("Please enter the next name.");
        }
        playerList.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));

        return playerList;
    }

    /**
     * Asks how many points should be needed to win the game and gets the input from the console.
     *
     * @return The entered amount of points needed to win.
     */
    public int getWinPoints() {
        System.out.println("Please enter the amount of points needed for a player to win:");
        Scanner scanner = new Scanner(System.in);
        int points = 0;
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (!nextLine.matches("\\d+")) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            points = Integer.parseInt(nextLine);
            if (points <= 0) {
                System.out.println("Please enter a number greater than 0.");
                continue;
            }
            System.out.println(points + " set.");
            break;
        }
        return points;
    }

    public void checkKeep(String name, DiceSet diceSet) {
        System.out.println("[" + name + "] Which dice do you want to keep? (enter the index/indices of the dice that you want to keep)");
        System.out.println("[" + name + "] Please enter the index/indices of a valid single number or a triplet, or press enter to skip. (Example: 1 or 1,2,3)");
        System.out.println("[" + name + "] You need to keep at least one dice per roll");
        Scanner scanner = new Scanner(System.in);
        boolean keptSomething = false;
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();

            // Keeping single
            if (nextLine.matches("\\d")) {
                int keeping = Integer.parseInt(nextLine);

                if (notBetweenOneAndSix(name, keeping)) {
                    continue;
                }

                if (keptAlready(name, diceSet, keeping)) {
                    continue;
                }

                int number = diceSet.getNumber(keeping - 1).getNumber();
                if (number != 1 && number != 5) {
                    System.out.println("[" + name + "] You can only keep a dice with a 1 or 5.");
                    continue;
                }

                diceSet.keep(keeping - 1, false);
                System.out.println("[" + name + "] Keeping dice " + keeping + ".");
                keptSomething = true;
                continue;
            }

            // Keeping triplet
            if (nextLine.matches("\\d,\\d,\\d")) {

                int[] keeping = {Integer.parseInt(nextLine.substring(0, 1)), Integer.parseInt(nextLine.substring(2, 3)), Integer.parseInt(nextLine.substring(4, 5))};
                boolean validTriplet = true;
                int tripletNumber = 0;

                for (int i : keeping) {

                    if (notBetweenOneAndSix(name, i)) {
                        validTriplet = false;
                        break;
                    }

                    if (tripletNumber == 0) {
                        tripletNumber = diceSet.getNumber(i - 1).getNumber();
                    } else {
                        if (tripletNumber != diceSet.getNumber(i - 1).getNumber()) {
                            System.out.println("[" + name + "] All three dice of the triplet need to have the same number/face.");
                            validTriplet = false;
                            break;
                        }
                    }

                    if (keptAlready(name, diceSet, i)) {
                        validTriplet = false;
                        break;
                    }

                    int cont = 0;
                    for (int j = 0; j < 3; j++) {
                        if (keeping[j] == i) cont++;
                    }
                    if (cont > 1) {
                        System.out.println("[" + name + "] You cannot keep the same dice twice in a triplet.");
                        validTriplet = false;
                        break;
                    }

                }

                if (validTriplet) {
                    for (int i : keeping) {
                        diceSet.keep(i - 1, true);
                    }
                    keptSomething = true;
                    System.out.println("[" + name + "] Keeping triplet " + nextLine + ".");
                }

                continue;
            }

            // Keeping no more dice
            if (nextLine.isBlank()) {
                if (!keptSomething) {
                    System.out.println("[" + name + "] You have to keep at least one dice!");
                    continue;
                }
                break;
            }

            // If this point is reached, the input is invalid.
            System.out.println("[" + name + "] Invalid input. Please enter a single number or a triplet, or press enter to skip. (Example: 1 or 1,2,3)");
        }
    }

    public void checkKeepStraight(String name, DiceSet diceSet) {
        System.out.println("[" + name + "] Which dice do you want to keep? (enter the index of the dice that you want to keep)");
        System.out.println("[" + name + "] Please enter the index of a valid single number, which you haven't kept already, to form a straight");
        Scanner scanner = new Scanner(System.in);
        boolean keptSomething = false;
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine.matches("\\d")) {
                int keeping = Integer.parseInt(nextLine);

                if (notBetweenOneAndSix(name, keeping)) {
                    continue;
                }

                if (keptAlready(name, diceSet, keeping)) {
                    continue;
                }

                int number = diceSet.getNumber(keeping - 1).getNumber();
                boolean alreadyKept = false;
                for (int i = 0; i < 6; i++) {
                    if (number == diceSet.getNumber(i).getNumber()) {
                        if (diceSet.isKept(i)) {
                            System.out.println("[" + name + "] This face is already kept (all six different faces are needed to form a straight)");
                            alreadyKept = true;
                            break;
                        }
                    }
                }
                if (alreadyKept) {
                    continue;
                }

                diceSet.keep(keeping - 1, false);
                System.out.println("[" + name + "] Keeping dice " + keeping + ".");
                keptSomething = true;
            } else {
                //no more dice are kept
                if (nextLine.isBlank()) {
                    if (!keptSomething) {
                        System.out.println("[" + name + "] You have to keep at least one dice!");
                        continue;
                    }
                    break;
                }
                System.out.println("[" + name + "] Invalid input. Please enter a single number or press enter to skip. (Example: 3)");
            }
        }
    }

    public void roll_or_Display(List<Player> playerList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an 'R' to start rolling the dice or 'D' to display the current scores");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                System.out.println("Input was blank, please enter either 'R' or 'D'");
            } else if (line.length() > 1) {
                System.out.println("Input was more than just one letter, please enter either 'R' or 'D'");
            } else if (line.equals("D")) {
                IOManagerFactory.getOutputmanager().displayScores(playerList);
            } else if (line.equals("R")) {
                break;
            } else {
                System.out.println("only input either 'R' or 'D'");
            }
        }
    }

    public boolean roll_or_End() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an 'R' to continue rolling the remaining dice or 'E' to end your turn and keep the current points.");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                System.out.println("Input was blank, please enter either 'R' or 'E'");
            } else if (line.length() > 1) {
                System.out.println("Input was more than just one letter, please enter either 'R' or 'E'");
            } else if (line.equals("E")) {
                break;
            } else if (line.equals("R")) {
                return true;
            } else {
                System.out.println("only input either 'R' or 'E'");
            }
        }
        return false;
    }

    private boolean notBetweenOneAndSix(String name, int num) {
        if (num < 1 || num > 6) {
            System.out.println("[" + name + "] Please only enter numbers between 1 and 6.");
            return true;
        }
        return false;
    }

    private boolean keptAlready(String name, DiceSet diceSet, int i) {
        if (diceSet.isKept(i - 1)) {
            System.out.println("[" + name + "] Already keeping dice " + i + ".");
            return true;
        }
        return false;
    }
}
