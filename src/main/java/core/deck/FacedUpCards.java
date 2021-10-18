package core.deck;


import core.card.Card;

import java.util.Map;
import java.util.Optional;

public record FacedUpCards(Map<Integer, Card> cards) {

    public void put(int position, Card card) {
        cards.put(position, card);
    }

    public Optional<Card> pick(int position) {
        var card = cards.get(position);
        if(card == null) {
            return Optional.empty();
        }
        cards.remove(position);
        return Optional.of(card);
    }

    public int size() {
        return cards.size();
    }

    @Override
    public String toString() {
        return cards.values().toString();
    }

}
