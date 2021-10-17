package model.card;

import model.player.Player;
import org.javatuples.Triplet;

public class Card {
    public final CardColour colour;
    public final CardNumber cardNumber;
    private Player player;

    public Card(CardColour colour, CardNumber cardNumber, Player player) {
        this.colour = colour;
        this.cardNumber = cardNumber;
        this.player = player;
    }

    @Override
    public String toString() {
        return Triplet.with(colour, cardNumber, player).toString();
    }
}
