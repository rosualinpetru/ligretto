package model.deck;

import model.card.Card;

import java.util.Stack;

public record TargetDeck(Stack<Card> cards) implements Deck {

    public Card pop() {
        return cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
