package model.player;

import model.deck.FacedUpCards;
import model.deck.ShufflingDeck;
import model.deck.TargetDeck;
import util.DeckShuffler;

public abstract class Player {
    protected final String id;

    protected final FacedUpCards facedUpCards;
    protected final TargetDeck targetDeck;
    protected final ShufflingDeck shufflingDeck;

    public Player(String id) {
        this.id = id;

        var decks = DeckShuffler.shuffleStartingDecks(this);
        facedUpCards = decks.getValue0();
        targetDeck = decks.getValue1();
        shufflingDeck = decks.getValue2();
    }

    @Override
    public String toString() {
        return id;
    }
}