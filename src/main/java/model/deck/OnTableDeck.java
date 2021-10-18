package model.deck;

import model.card.Card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//Here is a lot of concurrency
public class OnTableDeck implements Deck {
    private Deque<Card> cards = new ArrayDeque<>();

    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock readLock = rwl.readLock();
    private final Lock writeLock = rwl.writeLock();

    private final Semaphore writeSemaphore = new Semaphore(1);

    public OnTableDeck(Card initialCard) {
        cards.push(initialCard);
    }

    public Card peek() {
        readLock.lock();
        try {
            return cards.peek();
        } finally {
            readLock.unlock();
        }
    }

    public boolean isCompleted() {
        readLock.lock();
        try {
            assert cards.peek() != null;
            return cards.peek().number.isLast();
        } finally {
            readLock.unlock();
        }
    }

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

    public boolean put(Card card) {
        try {
            writeLock.lock();
            writeSemaphore.acquire();
            if (cardFits(card)) {
                cards.push(card);
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            writeSemaphore.release();
            writeLock.unlock();
        }
    }

    /**
     * Used for testing
     *
     * @return number of cards in deck
     */
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
