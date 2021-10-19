package core.entities;

import core.event.CardPlacedEvent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public final class Bot extends Player {
    /**
     * We consider that a player can only do one action at a time:
     * - Shuffle the cards
     * - Handle new card placement
     */
    private final Semaphore semaphore = new Semaphore(1);

    public Bot(String id) {
        super(id);
    }

    /**
     * A player constantly shuffles the shuffling deck in order to find fitting cards.
     * This shuffles the deck and competes with other threads in placing cards on the table.
     */
    @Override
    public void run() {
        if (table == null) {
            System.out.println("The player " + name + " has not joined any table!");
            return;
        }
        while (!shufflingDeck.isEmpty()) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                return;
            }
            //TODO: a bot should not constantly search for 1s. Needs optimisation
            putFacedUpOnes();

            var cardOpt = shufflingDeck.pick();
            if (cardOpt.isEmpty()) {
                break;
            }

            var card = cardOpt.get();
            if (card.number.isFirst()) {
                table.newDeck(card);

            } else {
                var fitPositionOpt = table.fittingDeck(card);
                if (fitPositionOpt.isPresent()) {
                    var fitPosition = fitPositionOpt.get();
                    var successfullyPlacedCard = table.put(card, fitPosition);
                    if (!successfullyPlacedCard) {
                        shufflingDeck.put(card);
                    }
                } else {
                    shufflingDeck.put(card);
                }
            }
            shufflingDeck.shuffle();
            semaphore.release();
            /* This is very important. Because of time slicing algorithm, each thread can
            execute a certain amount of instructions. The problem is that between the Semaphore
            release and acquire there is only a jump instruction. This leads to the main bot thread
            to constantly acquire the semaphore, without leaving a change for the tasks of the event
            bus to be handled. What this means is that shuffling will have a higher priority than
            responding to updates on the table, which is the exact opposite of how the bot should work,

            Thread.yield() instructs that the current thread gives up its processor time, leaving the event
            handler to acquire the semaphore.
             */
            Thread.yield();
        }
        // If no cards left in the shuffling deck, wait for other events.
        while (true) {

        }
    }

    /**
     * A player also reacts whenever a card is placed on the table.
     * Upon a new card being placed on the table, the player will examine the
     * faced up cards in search of a card that fits on the recently updated deck.
     */
    void handleCardPlaced(CardPlacedEvent event) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            return;
        }
        for (int i = 1; i <= facedUpCards.size(); i++) {
            var cardOpt = facedUpCards.pick(i);
            if (cardOpt.isEmpty())
                continue;
            var card = cardOpt.get();
            if (table.fitsPosition(card, event.position())) {
                var successfullyPlacedCard = table.put(card, event.position());
                if (successfullyPlacedCard) {
                    updateFacedUpCards(i);
                } else {
                    facedUpCards.put(i, card);
                    /* The bot attempted to put a card but the deck has been
                    already updated
                    This means the card has changed and it's not worth
                    investigating during this event handling task */
                    semaphore.release();
                    return;
                }
            } else {
                facedUpCards.put(i, card);
            }
        }
        semaphore.release();
    }

    /**
     * This function searches for 1s on the faced up cards and places them
     * on the table.
     */
    private void putFacedUpOnes() {
        for (int i = 1; i <= facedUpCards.size(); i++) {
            var cardOpt = facedUpCards.pick(i);
            if (cardOpt.isEmpty())
                continue;
            var card = cardOpt.get();
            if (card.number.isFirst()) {
                table.newDeck(card);
                updateFacedUpCards(i);
            } else {
                facedUpCards.put(i, card);
            }
        }
    }

    /**
     * This function updated the faced up cards by popping a cards from the target deck.
     *
     * TODO: The winner ends up with lesser faced up cards than it should.
     */
    private void updateFacedUpCards(int position) {
        var newCard = targetDeck.pop();
        if (newCard.isPresent())
            facedUpCards.put(position, newCard.get());
        else
            table.end(this);
    }

    @Override
    public String toString() {
        try {
            semaphore.acquire();
        } catch (InterruptedException ignored) {
        }
        String s = "Bot{" +
                "id='" + name + '\'' +
                ", facedUpCards=" + facedUpCards +
                ", targetDeck=" + targetDeck +
                ", shufflingDeck=" + shufflingDeck +
                '}';
        semaphore.release();
        return s;
    }

}
