package model.player;

import events.EventBus;
import events.impl.EventListener;
import model.board.Table;
import model.card.Card;
import model.event.CardPlacedEvent;

import java.util.concurrent.Semaphore;

public class Bot extends Player implements Runnable {
    private final Semaphore action = new Semaphore(1);

    public Bot(String id, Table table) {
        super(id, table);
        var listener = new EventListener<CardPlacedEvent>();
        listener.subscribe(this::handleCardPlaced);
        table.eventBus.registerListener(listener);
    }

    @Override
    public void run() {
        try {
            while (!targetDeck.isEmpty()) {
                action.acquire();
                // needs optimisation
                putFacedUpOnes();
                Card card = shufflingDeck.pick();
                if (card.number.isFirst()) {
                    var newDeckPosition = table.startDeck(card);

                } else {
                    var fittingDeckPositionOpt = table.findFittingDeck(card);
                    if (fittingDeckPositionOpt.isPresent()) {
                        var fittingDeckPosition = fittingDeckPositionOpt.get();
                        var successfullyPlacedCard = table.placeCardAtPosition(card, fittingDeckPosition);
                        if (!successfullyPlacedCard) {
                            shufflingDeck.putBack(card);
                        }
                    } else {
                        shufflingDeck.putBack(card);
                    }
                }
                shufflingDeck.shuffle();
                action.release();
            }
            System.out.println(id + " finished the deck!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showStatus() throws InterruptedException {
        action.acquire();
        System.out.println("Bot{" +
                "id='" + id + '\'' +
                ", facedUpCards=" + facedUpCards +
                ", targetDeck=" + targetDeck +
                ", shufflingDeck=" + shufflingDeck +
                '}');
        action.release();
    }

    private void handleCardPlaced(CardPlacedEvent event) {
        try {
            action.acquire();
            for (int i = 1; i <= facedUpCards.size(); i++) {
                var card = facedUpCards.pick(i);
                var placed = event.card;
                if (card.isSuccessor(placed)) {
                    var successfullyPlacedCard = table.placeCardAtPosition(card, event.position);
                    if (successfullyPlacedCard) {
                        updateFacedUpCards(i);
                    } else {
                        facedUpCards.put(i, card);
                    }
                } else {
                    facedUpCards.put(i, card);
                }
            }
            action.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void putFacedUpOnes() {
        for (int i = 1; i <= facedUpCards.size(); i++) {
            var card = facedUpCards.pick(i);
            if (card.number.isFirst()) {
                var newDeckPosition = table.startDeck(card);
                updateFacedUpCards(i);
            } else {
                facedUpCards.put(i, card);
            }
        }
    }

    private void updateFacedUpCards(int position) {
        var newCard = targetDeck.pop();
        facedUpCards.put(position, newCard);
    }

}
