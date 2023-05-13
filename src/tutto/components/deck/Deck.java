package tutto.components.deck;

import tutto.components.deck.card.Card;
import tutto.components.deck.card.type.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck implements CardComposite {

    private final Stack<Card> deck = new Stack<>();
    private final Stack<Card> used = new Stack<>();

    /**
     * Creates a new deck and initializes a new Stack of cards from list.
     * Used for testing.
     */
    public Deck(ArrayList<Card> cards) {
        for (Card card : cards) {
            deck.push(card);
        }
    }

    /**
     * Creates a new deck and initializes a new Stack of cards.
     */
    public Deck() {
        deck.push(new CloverleafCard());

        Card fireworksCard = new FireworksCard();
        Card straightCard = new StraightCard();
        Card plusMinusCard = new PlusMinusCard();
        Card doubleCard = new DoubleCard();
        Card bonus200Card = new BonusCard(200);
        Card bonus300Card = new BonusCard(300);
        Card bonus400Card = new BonusCard(400);
        Card bonus500Card = new BonusCard(500);
        Card bonus600Card = new BonusCard(600);
        for (int i = 0; i < 5; i++) {
            deck.push(fireworksCard);
            deck.push(straightCard);
            deck.push(plusMinusCard);
            deck.push(doubleCard);
            deck.push(bonus200Card);
            deck.push(bonus300Card);
            deck.push(bonus400Card);
            deck.push(bonus500Card);
            deck.push(bonus600Card);
        }

        Card stopCard = new StopCard();
        for (int i = 0; i < 10; i++) deck.push(stopCard);
        Collections.shuffle(deck);
    }

    /**
     * Re-Shuffles the deck: Adds played cards into deck and shuffles it.
     */
    public void reShuffle() {
        System.out.println("Reshuffling deck.");
        while (!used.isEmpty()) {
            deck.push(used.pop());
        }
        Collections.shuffle(deck);
    }

    /**
     * Gets the next card from the stack/deck.
     *
     * @return A copy of the Card.
     */
    public Card drawCard() {
        if (deck.isEmpty()) reShuffle();
        Card next = deck.pop();
        used.push(next);
        return next.copy();
    }

    public Card peekCard() {
        if (deck.isEmpty()) reShuffle();
        return deck.peek().copy();
    }

    /**
     * Returns deck stack.
     * Used for testing.
     */
    public Stack<Card> getDeck() {
        return deck;
    }

}
