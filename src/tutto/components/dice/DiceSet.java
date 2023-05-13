package tutto.components.dice;

import java.util.ArrayList;
import java.util.List;

public class DiceSet {

    private final List<Dice> diceSet = new ArrayList<>();
    private static final int NUMBER_OF_DICE = 6;

    public DiceSet() {
        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            diceSet.add(new Dice());
        }
    }

    public void setDice(ArrayList<DiceDots> names, boolean[] keep, boolean[] triplet) {
        for (int i = 0; i < 6; i++) {
            diceSet.get(i).setDiceVariables(names.get(i), keep[i], triplet[i]);
        }
    }

    public List<DiceDots> getNumbers() {
        List<DiceDots> diceDots = new ArrayList<>();
        for (Dice dice : diceSet) {
            diceDots.add(dice.getNumber());
        }
        return diceDots;
    }

    public DiceDots getNumber(int dice) {
        assert 0 <= dice && dice < 6;
        return diceSet.get(dice).getNumber();
    }

    public void rollUnkept() {
        for (Dice d : diceSet) {
            if (!d.isKept()) {
                d.roll();
            }
        }
    }

    public void keep(int i, boolean triplet) {
        diceSet.get(i).setKeeping(true, triplet);
    }

    public boolean isKept(int i) {
        return diceSet.get(i).isKept();
    }

    public void removeKeeping() {
        for (Dice dice : diceSet) {
            dice.setKeeping(false, false);
        }
    }

    public boolean checkTutto() {
        return allKept();
    }

    public int calcPoints() {
        int points = 0;

        int firstTriplet = 0;
        int firstTripletAmount = 0;
        int secondTripletAmount = 0;

        for (Dice dice : diceSet) {
            DiceDots d = dice.getNumber();
            if (!dice.isKept()) continue;
            if (dice.isInTriplet()) {
                if (firstTriplet == 0) {
                    // first dice of 1st triplet
                    firstTriplet = d.getNumber();
                    firstTripletAmount = 1;
                    points += d.getTripletValue();
                } else if (firstTriplet == d.getNumber()) { // first triplet is already set
                    if (firstTripletAmount >= 3) {
                        if (secondTripletAmount == 0) {
                            // first dice of 2nd triplet
                            points += d.getTripletValue();
                        }
                        secondTripletAmount++;
                    } else {
                        firstTripletAmount++;
                    }
                } else {
                    if (secondTripletAmount == 0) {
                        // first dice of 2nd triplet
                        points += d.getTripletValue();
                    }
                    secondTripletAmount++;
                }
            } else {
                if (d.equals(DiceDots.ONE)) {
                    points += 100;
                    continue;
                }
                if (d.equals(DiceDots.FIVE)) {
                    points += 50;
                }
            }
        }
        return points;
    }

    public boolean allKept() {
        for (Dice d : diceSet) {
            if (!d.isKept()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkValidRoll() {
        boolean valid_roll = false;
        int[] arr = new int[6];
        for (Dice d : diceSet) {
            if (!d.isKept()) {
                arr[d.getNumber().getNumber() - 1] += 1;
            }
        }
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                if (arr[i] > 0) {
                    valid_roll = true;
                }
            } else if (i == 4) {
                if (arr[i] > 0) {
                    valid_roll = true;
                }
            } else {
                if (arr[i] >= 3) {
                    valid_roll = true;
                }
            }
        }
        return valid_roll;
    }

    public void FireworksCard_keepValidDice() {
        int[] arr = new int[6];
        for (Dice d : diceSet) {
            if (!d.isKept()) {
                arr[d.getNumber().getNumber() - 1] += 1;
            }
        }
        while (!allTripletFound(arr)) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] >= 3) {
                    int count = 0;
                    for (Dice d : diceSet) {
                        if (!d.isKept()) {
                            if (count < 3) {
                                if (d.getNumber().getNumber() == (i + 1)) {
                                    d.setKeeping(true, true);
                                    count += 1;
                                }
                            }
                        }
                    }
                    arr[i] -= 3;
                }
            }
        }
        while (!allSingleFound(arr)) {
            for (Dice d : diceSet) {
                if (!d.isKept()) {
                    if (d.getNumber() == DiceDots.ONE || d.getNumber() == DiceDots.FIVE) {
                        d.setKeeping(true, false);
                        arr[d.getNumber().getNumber() - 1] -= 1;
                    }
                }
            }

        }

    }

    private boolean allTripletFound(int[] arr) {
        boolean allTripletFound = true;
        for (int i : arr) {
            if (i >= 3) {
                allTripletFound = false;
                break;
            }
        }
        return allTripletFound;
    }

    private boolean allSingleFound(int[] arr) {
        return arr[0] < 1 && arr[4] < 1;
    }

    public boolean StraightCard_checkValidRoll() {
        boolean atLeastOne = false;
        for (Dice d : diceSet) {
            if (!d.isKept()) {
                boolean allNotKept = true;
                for (Dice x : diceSet) {
                    if (d.getNumber() == x.getNumber()) {
                        if (x.isKept()) {
                            allNotKept = false;
                        }
                    }
                }
                if (allNotKept) {
                    atLeastOne = true;
                }
            }
        }
        return atLeastOne;
    }
}
