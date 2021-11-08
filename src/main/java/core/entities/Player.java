package core.entities;

import core.card.Card;
import core.deck.FacedUpCards;
import core.deck.ShufflingDeck;
import core.deck.TargetDeck;
import core.util.DeckShuffler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

/**
 * Sealed class for either Bot or Human player.
 */
public abstract class Player implements Runnable {
    /**
     * We consider that a player can only do one action at a time:
     * - Shuffle the cards
     * - Handle new card placement
     */
    protected final Semaphore semaphore = new Semaphore(1);

    public final String name;

    protected FacedUpCards facedUpCards;
    protected TargetDeck targetDeck;
    protected ShufflingDeck shufflingDeck;

    protected Table table;

    /**
     * Upon creation, each player will have all decks shuffled.
     *
     * @param name The identifier of a player.
     */
    public Player(String name) {
        this.name = name;
        init();
    }

    public void setTable(Table table) {
        this.table = table;
    }

    Stream<Card> getVisibleCards() {
        try {
            semaphore.acquire();
            var result = new java.util.ArrayList<>(List.copyOf(facedUpCards.cards().values().stream().toList()));
            semaphore.release();
            result.addAll(shufflingDeck.getCards());
            return result.stream();
        } catch (InterruptedException ignored) {
        } finally {
            semaphore.release();
        }
        return Stream.empty();
    }

    private void init() {
        var decks = DeckShuffler.shuffleStartingDecks(this);
        facedUpCards = decks.getValue0();
        targetDeck = decks.getValue1();
        shufflingDeck = decks.getValue2();
    }


    @Override
    public String toString() {
        return name;
    }
}