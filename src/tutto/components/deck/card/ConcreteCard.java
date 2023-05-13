package tutto.components.deck.card;

public abstract class ConcreteCard implements Card {
    private final String NAME;

    public ConcreteCard(String name) {
        this.NAME = name;
    }

    public String getName() {
        return NAME;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        return this.NAME.equals(((ConcreteCard) obj).getName());
    }
}
