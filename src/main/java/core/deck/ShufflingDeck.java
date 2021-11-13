package core.deck;

import core.card.Card;

import java.util.List;
import java.util.Optional;

/**
 * Represents the decks which is constantly shuffled by
 * a player.
 * <p>
 * It is not meant to be thread safe.
 */
public final class ShufflingDeck {
    private final static int STEP = 3;

    private int pointer = 0;
    private final List<Card> cards;

    public ShufflingDeck(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Puts a card back from where it was picked.
     *
     * @param card The card to be placed back.
     */
    public void put(Card card) {
        cards.add(pointer, card);
    }

    /**
     * Picks a card from the deck if it is not empty. Otherwise, None is returned.
     *
     * @return Optional wrapping the card placed at a certain position in the array.
     */
    public Optional<Card> pick() {
        if (isEmpty()) {
            return Optional.empty();
        }
        // todo review why pointer can be equal to cards.size()
        if (pointer >= cards.size()) {
            pointer = 0;
        }
        var card = cards.get(pointer);
        cards.remove(card);
        return Optional.of(card);
    }

    public boolean isEmpty() {
        return cards.size() == 0;
    }

    /**
     * Pseudo-implements the Ligretto shuffling algorithm. Each player must
     * pick every third card from the deck. Upon reaching the end, the deck
     * is shifted to left by one.
     * <p>
     */
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

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
