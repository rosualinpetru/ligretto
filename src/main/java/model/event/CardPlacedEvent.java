package model.event;

import model.card.Card;

public class CardPlacedEvent {
    public final Card card;
    public final int position;

    public CardPlacedEvent(Card card, int position) {
        this.card = card;
        this.position = position;
    }

    @Override
    public String toString() {
        return "CardPlacedEvent{" +
                "card=" + card +
                ", position=" + position +
                '}';
    }
}
