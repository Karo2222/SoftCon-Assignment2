package tutto.components.dice;

public class Dice {
    private DiceDots number;
    private boolean keep = false;
    private boolean triplet = false;

    public void setDiceVariables(DiceDots number, boolean keep, boolean triplet) {
        this.number = number;
        setKeeping(keep, triplet);
    }

    public Dice() {
        roll();
    }

    public void roll() {
        number = DiceDots.getRandomNumber();
    }

    public DiceDots getNumber() {
        return number;
    }

    public void setKeeping(boolean keep, boolean triplet) {
        this.keep = keep;
        this.triplet = triplet;
    }

    public boolean isKept() {
        return keep;
    }

    public boolean isInTriplet() {
        return triplet;
    }
}
