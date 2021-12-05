package core.deck;

import core.card.Card;

import java.util.Optional;
import java.util.Stack;

/**
 * Represents the generally 10-cards deck which one player
 * needs to finish in order to win a round.
 *
 * Upon placing on the table one of the faced up cards,
 * a popped card from the target deck will replace it.
 *
 * It is not meant to be thread safe.
 */
public record TargetDeck(Stack<Card> cards) {

    /**
     * Pops a card from the deck if there are cards present.
     * @return Optional wrapping the popped card from the deck.
     */
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

    public int stackSize(){
        return cards.size();
    }
}
