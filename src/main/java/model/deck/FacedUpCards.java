package model.deck;


import model.card.Card;

import java.util.Map;

public record FacedUpCards(Map<Integer, Card> cards) implements Deck {

    public void put(int position, Card card) {
        cards.put(position, card);
    }

    public Card pick(int position) {
        var card = cards.get(position);
        cards.remove(position);
        return card;
    }

    public int size() {
        return cards.size();
    }

    @Override
    public String toString() {
        return cards.values().toString();
    }

}
