package model.player;

import model.board.Table;
import model.deck.FacedUpCards;
import model.deck.ShufflingDeck;
import model.deck.TargetDeck;
import util.DeckShuffler;

import java.util.Objects;

public abstract class Player {
    protected final String id;

    protected final FacedUpCards facedUpCards;
    protected final TargetDeck targetDeck;
    protected final ShufflingDeck shufflingDeck;

    protected final Table table;

    public Player(String id, Table table) {
        this.id = id;
        this.table = table;

        var decks = DeckShuffler.shuffleStartingDecks(this);
        facedUpCards = decks.getValue0();
        targetDeck = decks.getValue1();
        shufflingDeck = decks.getValue2();
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}