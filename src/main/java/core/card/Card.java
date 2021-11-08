package core.card;

import core.entities.Player;

import java.util.Optional;

public record Card(CardColour colour, CardNumber number, Player player) {

    public boolean isSuccessor(Card card) {
        Optional<CardNumber> next = card.number.next();
        return next.isPresent() && next.get() == number && card.colour == colour;

    }

    @Override
    public String toString() {
        return "" + colour + number;
    }
}
