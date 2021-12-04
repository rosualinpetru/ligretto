package core.entities;

import core.card.Card;
import core.deck.OnTableDeck;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Checker implements Runnable {
    private final ReentrantLock lock = new ReentrantLock();

    private OffsetTime timestamp = OffsetTime.now();
    //TODO: Update the countdown_duration and delays after inserting the human thread
    private final Duration COUNTDOWN_DURATION = Duration.ofSeconds(5);
    private final Table table;
    private final AtomicBoolean shouldEndGame = new AtomicBoolean(false);
    private boolean disabled = false;

    public Checker(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        while (table.getState() != TableState.ENDED) {
            while (table.getState() == TableState.ONGOING) {
                lock.lock();
                var duration = Duration.between(timestamp, OffsetTime.now());
                lock.unlock();

                if (disabled) {
                    continue;
                }

                if (duration.compareTo(COUNTDOWN_DURATION) > 0) {
                    table.pause();

                    if (shouldEndGame.get()) {
                        table.end(null);
                        return;
                    }
                    table.resume();
                    timestamp = OffsetTime.now();
                }
            }
        }
    }

    public void setTimestamp(OffsetTime timestamp) {
        lock.lock();
        this.timestamp = timestamp;
        lock.unlock();
    }

    public void checkGameForTie() {
        System.out.println("Checking for tie...");
        var tableData = table.getAllData();
        for (OnTableDeck deck : tableData.placedDecks()) {
            for (Card card : tableData.visibleCards()) {
                if (deck.cardFits(card)) {
                    shouldEndGame.set(false);
                    return;
                }
            }
        }

        shouldEndGame.set(true);
    }

    public void disable() {
        disabled = true;
    }
}
