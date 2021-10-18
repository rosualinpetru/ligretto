package core.card;

import core.entities.Player;
import org.javatuples.Triplet;

import java.util.Optional;

public class Card {
    public final CardColour colour;
    public final CardNumber number;
    public final Player player;

    public Card(CardColour colour, CardNumber number, Player player) {
        this.colour = colour;
        this.number = number;
        this.player = player;
    }

    public boolean isSuccessor(Card card) {
        Optional<CardNumber> next = card.number.next();
        return next.isPresent() && next.get() == number && card.colour == colour;

    }

    @Override
    public String toString() {
        return "" + colour + number;
    }
}
