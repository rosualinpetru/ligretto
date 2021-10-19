package core.deck;

import core.card.Card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Represents a deck which is placed on the table.
 * For these type of decks are other players competing.
 *
 * An OnTableDeck should not bet empty during the game.
 *
 * It is supposed to handle synchronization.
 */
public class OnTableDeck {
    // Collection which is NOT internally synchronized.
    private final Deque<Card> cards = new ArrayDeque<>();

    // Ensures mutual exclusion between readers and writers.
    // Ensures mutual exclusion between writers.
    // Allows as many readers to obtain the lock.
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock readLock = rwl.readLock();
    private final Lock writeLock = rwl.writeLock();

    public OnTableDeck(Card initialCard) {
        cards.push(initialCard);
    }

    /**
     * Puts a card on top of the deck.
     * As multiple threads can race to place a card, it is important to recheck whether
     * the card which is to be put still fits.
     * @param card The card to be placed.
     * @return Boolean representing whether the operation was successful or not.
     */
    public boolean put(Card card) {
        try {
            writeLock.lock();
            if (cardFits(card)) {
                cards.push(card);
                return true;
            }
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Inspects the card on top of the deck, without removing the card.
     * @return The last card put.
     */
    public Card peek() {
        readLock.lock();
        try {
            return cards.peek();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Checks if a deck is complete by inspecting the card placed on top.
     * If it is a TEN {@link core.card.CardNumber}, the deck is completed.
     * @return Boolean representing whether the deck is completed or not.
     */
    public boolean isCompleted() {
        readLock.lock();
        try {
            assert cards.peek() != null;
            return cards.peek().number.isLast();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Checks if a card can be placed on this deck. The card needs to be a successor, meaning
     * it needs to be the same colour, yet it needs to have the number incremented by one.
     * @param card The card to be verified.
     * @return Boolean representing whether the card fits or not.
     */
    public boolean cardFits(Card card) {
        readLock.lock();
        try {
            var top = peek();
            assert top != null;
            return card.isSuccessor(top);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Retrieves all cards from the deck in a List.
     * It is used at the end of the game to compute stats.
     * @return List containing all cards.
     */
    public synchronized List<Card> getAll() {
        return cards.stream().toList();
    }

    public int size() {
        readLock.lock();
        try {
            return cards.size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String toString() {
        readLock.lock();
        try {
            assert cards.peek() != null;
            return cards.peek().toString();
        } finally {
            readLock.unlock();
        }
    }
}
