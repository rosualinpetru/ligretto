package core.deck;

import core.card.Card;

import java.util.Optional;
import java.util.Stack;

public record TargetDeck(Stack<Card> cards) {

    public Optional<Card> pop()  {
        if(isEmpty())
            return Optional.empty();
        return Optional.of(cards.pop());
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
