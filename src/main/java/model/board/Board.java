package model.board;

import model.card.Card;
import model.deck.OnTableDeck;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final Set<OnTableDeck> decks;

    public Board() {
        decks = new HashSet<>();
    }

    public void startDeck(Card card) {

    }
}
