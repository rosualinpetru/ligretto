package core.entities;

import core.event.CardPlacedEvent;
import core.exception.IllegalTableCallError;
import gui.BotCardsPanel;
import gui.BotsBoardFrame;
import gui.managers.BotCardManager;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public final class Bot extends Player {

    private final long delayMilliseconds;
    private BotCardsPanel botCardsPanel;
    private BotCardManager botCardManager;
    private boolean orientation;

    public Bot(String id, long delayMilliseconds) {
        super(id);
        this.delayMilliseconds = Math.max(0, delayMilliseconds);
        botCardsPanel = null;
        botCardManager = null;
    }

    public Bot(String id, long delayMilliseconds, boolean orientation){
        super(id);
        this.delayMilliseconds = Math.max(0, delayMilliseconds);
        botCardsPanel = null;
        botCardManager = null;
        this.orientation = orientation;
    }

    public void linkToCard(BotCardsPanel botCardsPanel) {
        this.botCardsPanel = botCardsPanel;
        for (int i = 1; i <= facedUpCards.size(); i++) {
            int finalI = i;
            facedUpCards.peek(i).ifPresent(card -> botCardsPanel.setCardAtPosition(finalI, card));
        }
    }

    public void linkToCardBotsBoard(BotCardManager botCardManager){
        this.botCardManager = botCardManager;
        for (int i = 1; i <= facedUpCards.size(); i++) {
            int finalI = i;
            facedUpCards.peek(i).ifPresent(card -> botCardManager.setCardAtPosition(finalI, card, orientation));
        }
    }

    /**
     * A player constantly shuffles the shuffling deck in order to find fitting cards.
     * This shuffles the deck and competes with other threads in placing cards on the table.
     */
    @Override
    public void run() {
        if (delayMilliseconds != 0) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(0, delayMilliseconds));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        do {
            try {
                while (table.getState() != TableState.ENDED && !Thread.interrupted()) {
                    if (table.getState() == TableState.PAUSED) {
                        table.pause();
                    }

                    semaphore.acquire();

                    /* Can be optimised, yet it is sufficient to simulate an AI
                        A bot should look for 1s at start and whenever pops a card
                        from the target deck.
                     */
                    putFacedUpOnes();
                    checkPlacedCards();

                    if (!shufflingDeck.isEmpty()) {

                        delay();

                        var cardOpt = shufflingDeck.pick();
                        if (cardOpt.isEmpty()) {
                            break;
                        }

                        var card = cardOpt.get();
                        if (card.number().isFirst()) {
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

                        if(delayMilliseconds != 0){
                            Thread.sleep(ThreadLocalRandom.current().nextLong(0, delayMilliseconds));
                        }

                        //update ui pt shuffle
                        if(botCardManager != null){
                            if(orientation)
                                shufflingDeck.peek().ifPresent(c -> botCardManager.setShuffle(c));
                            else
                                shufflingDeck.peek().ifPresent(c -> botCardManager.setShuffleWE(c));
                        }
                    }
                    semaphore.release();
                    Thread.yield();
                }
            } catch (IllegalTableCallError | InterruptedException ignored) {
                semaphore.release();
            }
        } while (table.getState() == TableState.PAUSED);

        table.pauseGamePhaser.arriveAndDeregister();
        System.out.println(this.name + " - Finished Deck cards");
    }

    /**
     * A player also reacts whenever a card is placed on the table.
     * Upon a new card being placed on the table, the player will examine the
     * faced up cards in search of a card that fits on the recently updated deck.
     */
    void handleCardPlaced(CardPlacedEvent event) {
        try {
            if (table.getState() != TableState.ENDED && !Thread.interrupted()) {

                if (table.getState() == TableState.PAUSED) {
                    table.pauseGamePhaser.register();
                    table.pause();
                    table.pauseGamePhaser.arriveAndDeregister();
                }

                semaphore.acquire();
                for (int i = 1; i <= facedUpCards.size(); i++) {
                    /* Recheck each time if there are cards available in the target deck
                    before picking a faced up card. Prevents the winner to end the game with less
                    than 3 faced up cards.
                     */
                    if (targetDeck.isEmpty()) {
                        semaphore.release();
                        return;
                    }
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
                            This means the card has changed, and it's not worth
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
        } catch (IllegalTableCallError | InterruptedException ignored) {
            semaphore.release();
        }
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
            if (card.number().isFirst()) {
                table.newDeck(card);
                updateFacedUpCards(i);
            } else {
                facedUpCards.put(i, card);
            }
        }
    }

    private void checkPlacedCards() {
        for (int i = 1; i <= facedUpCards.size(); i++) {
            var cardOpt = facedUpCards.pick(i);
            if (cardOpt.isEmpty())
                continue;
            var card = cardOpt.get();
            var positionOpt = table.fittingDeck(card);
            if (positionOpt.isPresent()) {
                table.put(card, positionOpt.get());
                updateFacedUpCards(i);
            } else {
                facedUpCards.put(i, card);
            }
        }
    }

    /**
     * This function updated the faced up cards by popping a cards from the target deck.
     */
    private void updateFacedUpCards(int position) {
        var newCard = targetDeck.pop();
        newCard.ifPresent(card -> {
            facedUpCards.put(position, card);
            if (botCardsPanel != null) {
                botCardsPanel.setCardAtPosition(position, card);
            }
            if(botCardManager != null){
                botCardManager.setCardAtPosition(position, card, orientation);
            }
        });
        if (targetDeck.isEmpty()) {
            table.end(this);
        }
    }

    private void delay() throws InterruptedException {
        if (delayMilliseconds == 0) {
            return;
        }

        Thread.sleep(delayMilliseconds);
    }


    @Override
    public String toString() {
        return "Bot{" +
                "id='" + name + '\'' +
                ", facedUpCards=" + facedUpCards +
                ", targetDeck=" + targetDeck +
                ", shufflingDeck=" + shufflingDeck +
                '}';
    }

}
