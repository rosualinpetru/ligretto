package core.deck;


import core.card.Card;

import java.util.Map;
import java.util.Optional;

/**
 * Represents the generally 3 faced up card of each player.
 * The representation uses Map as it is important
 * to know the position of each card.
 *
 * This class is not meant to handle any synchronization.
 */
public record FacedUpCards(Map<Integer, Card> cards) {

    /**
     * Inserts a card at a given position. No validation is made regarding the position.
     * The position has as valid values any in [1, N], where N is the number of faced up cards.
     * No position should be empty during the game.
     * @param position Unchecked position at which a card will pe placed.
     * @param card The card to be placed.
     */
    public void put(int position, Card card) {
        cards.put(position, card);
    }

    /**
     * Picks one of the cards placed at a given position. No validation is made regarding the position.
     * The position has as valid values any in [1, N], where N is the number of faced up cards.
     * After picking, it is removed from the object so it is mandatory to put it back if it was not
     * placed on the table
     * @param position Unchecked position from which a card will be picked.
     * @return The card located at the specified position, if it exists.
     */
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
