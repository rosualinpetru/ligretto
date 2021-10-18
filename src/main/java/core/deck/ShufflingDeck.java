package core.deck;

import core.card.Card;

import java.util.List;
import java.util.Optional;

public final class ShufflingDeck {
    private final static int STEP = 3;

    private int pointer = 0;
    private final List<Card> cards;

    public ShufflingDeck(List<Card> cards) {
        this.cards = cards;
    }

    public void shuffle() {
        if (isEmpty()) {
            return;
        }
        pointer += STEP;
        if (pointer >= cards.size()) {
            pointer = 0;
            var aux = cards.get(0);
            cards.remove(aux);
            cards.add(aux);
        }
    }

    public boolean isEmpty() {
        return cards.size() == 0;
    }

    public Optional<Card> pick() {
        if (isEmpty()) {
            return Optional.empty();
        }
        var card = cards.get(pointer);
        cards.remove(card);
        return Optional.of(card);
    }

    public void put(Card card) {
        cards.add(pointer, card);
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
