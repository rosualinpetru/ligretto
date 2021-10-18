package model.board;

import events.EventBus;
import model.card.Card;
import model.deck.OnTableDeck;
import model.event.CardPlacedEvent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Table {
    public final EventBus<CardPlacedEvent> eventBus;
    private final Set<Map.Entry<Integer, OnTableDeck>> decks;

    private final AtomicInteger decksCounter = new AtomicInteger();

    public Table(EventBus<CardPlacedEvent> eventBus) {
        this.eventBus = eventBus;
        decks = new CopyOnWriteArraySet<>();
    }

    public Integer startDeck(Card card) {
        OnTableDeck deck = new OnTableDeck(card);
        var counter = decksCounter.incrementAndGet();
        decks.add(Map.entry(counter, deck));
        eventBus.publish(new CardPlacedEvent(card, counter));
        return counter;
    }

    public boolean placeCardAtPosition(Card card, Integer position) {
        var deckOpt = getDeckAtPosition(position);
        if (deckOpt.isPresent()) {
            var deck = deckOpt.get();
            var condition = deck.put(card);
            if (condition) {
                eventBus.publish(new CardPlacedEvent(card, position));
            }
            return condition;
        }
        return false;
    }

    // Here reads should be happening during writing
    public Optional<Integer> findFittingDeck(Card card) {
        return decks.stream()
                .filter(it -> !it.getValue().isCompleted())
                .filter(it -> it.getValue().cardFits(card))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Optional<OnTableDeck> getDeckAtPosition(Integer position) {
        return decks.stream()
                .filter(it -> Objects.equals(it.getKey(), position))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public String toString() {
        return decks.stream().map(Map.Entry::getValue).collect(Collectors.toList()).toString();
    }
}
