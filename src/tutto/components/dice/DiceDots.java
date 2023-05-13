package tutto.components.dice;

import java.util.Random;

public enum DiceDots {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

    private final int number;
    private static final DiceDots[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    DiceDots(int number) {
        this.number = number;
    }

    public int getTripletValue() {
        int retInt = number * 100;
        if (number == 1) {
            retInt = 1000;
        }
        return retInt;
    }

    public int getNumber() {
        return number;
    }

    public static DiceDots getRandomNumber() {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}
