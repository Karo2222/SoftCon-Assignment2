package tutto.components.deck;

import tutto.components.deck.card.Card;

public interface CardComposite {
    void reShuffle();

    Card drawCard();

    Card peekCard();
}
