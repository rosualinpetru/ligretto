package core.entities;

import events.EventBus;
import core.card.Card;
import core.deck.OnTableDeck;
import core.event.CardPlacedEvent;
import events.impl.EventListener;
import org.javatuples.Pair;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Table {
    private volatile boolean isGameOver = false;
    private final EventBus<CardPlacedEvent> eventBus;
    private final List<Player> players = new ArrayList<>();
    private final List<Thread> botThreads = new ArrayList<>();

    private final Set<Map.Entry<Integer, OnTableDeck>> decks = new CopyOnWriteArraySet<>();
    private final AtomicInteger decksCounter = new AtomicInteger();

    public Table(EventBus<CardPlacedEvent> eventBus) {
        this.eventBus = eventBus;
    }

    // Public
    public void startGame() {
        botThreads.forEach(Thread::start);
        botThreads.forEach(it -> {
            try {
                it.join();
            } catch (InterruptedException ignored) {
            }
        });
    }

    public synchronized void endGame(Player player) {
        isGameOver = true;
        System.out.println(player + " WINS!");
        botThreads.forEach(Thread::interrupt);
        eventBus.dispose();
    }

    public void registerPlayer(Player player) {
        players.add(player);
        player.setTable(this);
        if (player instanceof Bot bot) {
            var listener = new EventListener<CardPlacedEvent>();
            listener.subscribe(bot::handleCardPlaced);
            eventBus.registerListener(listener);
            var thread = new Thread(player);
            thread.setName(player.name);
            botThreads.add(thread);
        }
    }

    public void showResults() {
        if (!isGameOver)
            return;
        System.out.println(this);
        players.stream()
                .map(player -> {
                    var score = decks.stream()
                            .flatMap(entry -> entry.getValue().getCards().stream())
                            .filter(card -> card.player.equals(player))
                            .count();
                    return Pair.with(player, score);
                }).forEach(System.out::println);
    }

    public void showEndingState() {
        if (!isGameOver)
            return;
        players.forEach(player -> {
            if (player instanceof Bot bot) {
                bot.showStatus();
            }
        });
    }

    //Package
    void createNewDeck(Card card) {
        if (isGameOver)
            return;
        OnTableDeck deck = new OnTableDeck(card);
        var counter = decksCounter.incrementAndGet();
        decks.add(Map.entry(counter, deck));
        eventBus.publish(new CardPlacedEvent(counter));
        System.out.println("New deck: " + card + ", position " + counter);
    }

    Optional<Integer> findFitPosition(Card card) {
        if (isGameOver)
            return Optional.empty();
        return decks.stream()
                .filter(it -> !it.getValue().isCompleted())
                .filter(it -> it.getValue().cardFits(card))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    boolean fitsPosition(Card card, int position) {
        if (isGameOver)
            return false;
        var deckOpt = getDeckAtPosition(position);
        return deckOpt.map(onTableDeck -> onTableDeck.cardFits(card)).orElse(false);
    }

    boolean putAtPosition(Card card, int position) {
        if (isGameOver)
            return false;
        var deckOpt = getDeckAtPosition(position);
        if (deckOpt.isPresent()) {
            var deck = deckOpt.get();
            String s = "Attempt: " + card.player + ", " + card + ", position " + position + " - ";
            var condition = deck.put(card);
            if (condition) {
                s += "SUCCESS";
                eventBus.publish(new CardPlacedEvent(position));
            } else {
                s += "FAILURE";
            }
            System.out.println(s);
            return condition;
        }
        return false;
    }

    Optional<OnTableDeck> getDeckAtPosition(Integer position) {
        if (isGameOver)
            return Optional.empty();
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
