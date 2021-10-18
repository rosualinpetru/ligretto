package core.entities;

import core.deck.FacedUpCards;
import core.deck.ShufflingDeck;
import core.deck.TargetDeck;
import util.DeckShuffler;

public abstract sealed class Player implements Runnable permits Bot  {
    public final String name;

    protected final FacedUpCards facedUpCards;
    protected final TargetDeck targetDeck;
    protected final ShufflingDeck shufflingDeck;

    protected Table table;

    public Player(String name) {
        this.name = name;

        var decks = DeckShuffler.shuffleStartingDecks(this);
        facedUpCards = decks.getValue0();
        targetDeck = decks.getValue1();
        shufflingDeck = decks.getValue2();
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return name;
    }
}