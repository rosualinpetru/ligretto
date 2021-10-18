package core.entities;

import core.event.CardPlacedEvent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public final class Bot extends Player {
    private final Semaphore semaphore = new Semaphore(1);

    public Bot(String id) {
        super(id);
    }

    public void showStatus() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            semaphore.release();
        }
        System.out.println("Bot{" +
                "id='" + name + '\'' +
                ", facedUpCards=" + facedUpCards +
                ", targetDeck=" + targetDeck +
                ", shufflingDeck=" + shufflingDeck +
                '}');
        semaphore.release();
    }

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
            // needs optimisation
            putFacedUpOnes();
            var cardOpt = shufflingDeck.pick();
            if (cardOpt.isEmpty()) {
                break;
            }
            var card = cardOpt.get();
            if (card.number.isFirst()) {
                table.createNewDeck(card);

            } else {
                var fittingDeckPositionOpt = table.findFitPosition(card);
                if (fittingDeckPositionOpt.isPresent()) {
                    var fittingDeckPosition = fittingDeckPositionOpt.get();
                    var successfullyPlacedCard = table.putAtPosition(card, fittingDeckPosition);
                    if (!successfullyPlacedCard) {
                        shufflingDeck.put(card);
                    }
                } else {
                    shufflingDeck.put(card);
                }
            }
            shufflingDeck.shuffle();
            semaphore.release();
            try {
                // No, daca imi explica si mie cineva de ce naiba daca nu pun linia asta crapa tot programul.
                // O sa fie mereu remiza.
                // 0L poate fi inlocuit cu ThreadLocalRandom.nextDouble(0.3, 0.5) * 1000L
                Thread.sleep(0L);
            } catch (InterruptedException ignored) {
                return;
            }
        }
        while (true) {

        }
    }


    void handleCardPlaced(CardPlacedEvent event) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            return;
        }
        for (int i = 1; i <= facedUpCards.size(); i++) {
            var cardOpt = facedUpCards.pick(i);
            if(cardOpt.isEmpty())
                continue;
            var card = cardOpt.get();
            if (table.fitsPosition(card, event.position())) {
                var successfullyPlacedCard = table.putAtPosition(card, event.position());
                if (successfullyPlacedCard) {
                    updateFacedUpCards(i);
                } else {
                    // The bot attempted to put a card but was to slow
                    // This means the card has changed and it's not worth
                    // investigating during this event handling task
                    facedUpCards.put(i, card);
                    semaphore.release();
                    return;
                }
            } else {
                facedUpCards.put(i, card);
            }
        }
        semaphore.release();
    }

    private void putFacedUpOnes() {
        for (int i = 1; i <= facedUpCards.size(); i++) {
            var cardOpt = facedUpCards.pick(i);
            if(cardOpt.isEmpty())
                continue;
            var card = cardOpt.get();
            if (card.number.isFirst()) {
                table.createNewDeck(card);
                updateFacedUpCards(i);
            } else {
                facedUpCards.put(i, card);
            }
        }
    }

    private void updateFacedUpCards(int position) {
        var newCard = targetDeck.pop();
        if (newCard.isPresent())
            facedUpCards.put(position, newCard.get());
        else
            table.endGame(this);
    }

}
