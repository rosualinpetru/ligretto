package model.deck;

import model.card.Card;

import java.util.List;

public final class ShufflingDeck implements Deck {
    private final static int STEP = 3;

    private int pointer = 0;
    private final List<Card> cards;

    public ShufflingDeck(List<Card> cards) {
        this.cards = cards;
    }

    public void shuffle() {
        pointer += STEP;
        if (pointer >= cards.size()) {
            pointer = 0;
            var aux = cards.get(0);
            cards.remove(aux);
            cards.add(aux);
        }
    }

    public Card pick() {
        var card = cards.get(pointer);
        cards.remove(card);
        return card;
    }

    public void putBack(Card card) {
        cards.add(pointer, card);
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
