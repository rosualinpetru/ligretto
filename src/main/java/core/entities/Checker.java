package core.entities;

import core.card.Card;
import core.deck.OnTableDeck;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.concurrent.locks.ReentrantLock;

public class Checker implements Runnable {
    private final ReentrantLock lock = new ReentrantLock();

    private OffsetTime timestamp = OffsetTime.now();
    //TODO: Update the countdown_duration and delays after inserting the human thread
    private final Duration COUNTDOWN_DURATION = Duration.ofSeconds(5);
    private final Table table;

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

                if (duration.compareTo(COUNTDOWN_DURATION) > 0) {
                    var isTie = checkGameForTie();
                    if (isTie) {
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

    private boolean checkGameForTie() {
        table.pause();
        var tableData = table.getAllData();
        for (OnTableDeck deck : tableData.placedDecks()) {
            for (Card card : tableData.visibleCards()) {
                if (deck.cardFits(card))
                    return false;
            }
        }

        return true;
    }
}
