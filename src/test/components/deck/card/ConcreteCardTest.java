package components.deck.card;

import org.junit.jupiter.api.Test;
import tutto.components.deck.Deck;
import tutto.components.deck.card.type.BonusCard;
import tutto.components.deck.card.type.DoubleCard;
import tutto.components.deck.card.type.FireworksCard;

import static org.junit.jupiter.api.Assertions.*;

public class ConcreteCardTest {
    @Test
    public void equals() {
        BonusCard bonus200 = new BonusCard(200);
        BonusCard bonus300 = new BonusCard(300);
        BonusCard bonus400 = new BonusCard(400);

        DoubleCard doubleCard = new DoubleCard();
        FireworksCard fireworksCard = null;
        Deck deck = new Deck();

        assertEquals(bonus200, bonus200);
        assertNotEquals(bonus200, bonus300);
        assertNotEquals(doubleCard, fireworksCard);
        assertNotEquals(bonus300, doubleCard);
        assertNotEquals(bonus400, deck);

    }
}
