package components.deck;

import org.junit.jupiter.api.Test;
import tutto.components.deck.CardComposite;
import tutto.components.deck.Deck;
import tutto.components.deck.card.Card;
import tutto.components.deck.card.type.BonusCard;
import tutto.components.deck.card.type.PlusMinusCard;
import tutto.components.deck.card.type.StraightCard;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


public class DeckTest {

    @Test
    public void reShuffle() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new BonusCard(200));
        cards.add(new PlusMinusCard());
        cards.add(new StraightCard());
        CardComposite deck = new Deck(cards);

        deck.drawCard();
        deck.drawCard();

        deck.reShuffle();
        Stack deckStack = ((Deck) deck).getDeck();
        assertEquals(cards.size(), deckStack.size());
    }

    @Test
    public void drawCardNonEmpty() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new BonusCard(200));
        cards.add(new PlusMinusCard());
        StraightCard straight = new StraightCard();
        cards.add(straight);
        CardComposite deck = new Deck(cards);

        assertEquals(straight, deck.drawCard());
    }

    @Test
    public void drawCardEmpty() {
        ArrayList<Card> cards = new ArrayList<>();
        BonusCard bonus = new BonusCard(200);
        cards.add(bonus);
        CardComposite deck = new Deck(cards);
        //make it empty and then draw once from an empty deck
        deck.drawCard();
        assertEquals(bonus, deck.drawCard());

    }

    @Test
    public void fullDeck() {
        CardComposite deck = new Deck();
        Stack deckStack = ((Deck) deck).getDeck();
        assertEquals(56, deckStack.size());
    }

    @Test
    public void sizeDeckAfterDrawingCards() {
        int number_of_tests = 200;
        for (int i = 0; i < number_of_tests; i++) {
            CardComposite deck = new Deck();
            Random random = new Random();
            int x = random.nextInt(57);
            for (int j = 0; j < x; j++) {
                deck.drawCard();
            }
            Stack deckStack = ((Deck) deck).getDeck();
            assertEquals(56 - x, deckStack.size());
        }
    }

    @Test
    public void peekCard() {
        ArrayList<Card> cards = new ArrayList<>();
        PlusMinusCard plusminus = new PlusMinusCard();
        cards.add(plusminus);
        Deck deck = new Deck(cards);
        assertEquals(plusminus, deck.peekCard());
        deck.drawCard();
        assertEquals(plusminus, deck.peekCard());

    }

}
