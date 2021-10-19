package core.entities;

import events.EventBus;
import core.card.Card;
import core.deck.OnTableDeck;
import core.event.CardPlacedEvent;
import events.impl.ConcurrentEventBus;
import events.impl.EventListener;
import org.javatuples.Pair;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Table implements Runnable {
    /**
     * Blocks all threads (specifically those from the thread pool) to enter
     * any function after the game. The number of queue of tasks of the bus
     * is higher than the thread pool can handle. If it is not used,
     * upon ending the game and disposing the bus, some events might still
     * be executed.
     */
    private volatile boolean isGameOver = false;

    private final EventBus<CardPlacedEvent> eventBus;

    private final List<Player> players = new ArrayList<>();
    private final List<Thread> botThreads = new ArrayList<>();

    private final AtomicInteger decksCounter = new AtomicInteger();
    /**
     * Although expensive, it allows both reads and writed on all
     * decks to happen concurrently.
     * <p>
     * It is also important to know the position of each decks it is easier
     * to identify them
     */
    private final Set<Map.Entry<Integer, OnTableDeck>> decks = new CopyOnWriteArraySet<>();

    public Table() {
        this.eventBus = new ConcurrentEventBus<>();
    }

    /*
    =============================================================================
    ================================ Public =====================================
    =============================================================================
     */

    /**
     * Upon registration, the player has the table set using the setter.
     * Afterwards, a thread is created for each player.
     * Furthermore, each player subscribes to the event bus of placed cards.
     *
     * @param player The player that joins the table.
     */
    public void register(Player player) {
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

    /**
     * Upon starting the game, all player threads will also start.
     * Also, a game blocks the caller threads.
     * <p>
     * TODO: Insert Human thread
     */
    @Override
    public void run() {
        botThreads.forEach(Thread::start);
        botThreads.forEach(it -> {
            try {
                it.join();
            } catch (InterruptedException ignored) {
            }
        });
    }

    /**
     * Upon ending the game, all player threads are interrupted.
     * It is synchronized because only one player can finish the game.
     * It is a rare case, but it is possible that two players can win
     * at the same time.
     * <p>
     * TODO: There is a bug where player threads are not interrupted; it does not reproduce everytime or following a pattern.
     */
    public synchronized void end(Player player) {
        if (!isGameOver) {
            isGameOver = true;
            System.out.println(player.name + " WINS!");
            botThreads.forEach(Thread::interrupt);
            eventBus.dispose();
        }
    }

    /**
     * Prints the score of each player after the game is finished.
     */
    public void score() {
        if (!isGameOver)
            return;
        players.stream()
                .map(player -> {
                    var score = decks.stream()
                            .flatMap(entry -> entry.getValue().getAll().stream())
                            .filter(card -> card.player.equals(player))
                            .count();
                    return Pair.with(player.name, score);
                }).forEach(System.out::println);
    }

    /**
     * Prints the state of the game after it has finished.
     */
    public void state() {
        if (!isGameOver)
            return;
        System.out.println(this);
        players.forEach(player -> {
            if (player instanceof Bot bot) {
                System.out.println(bot);
            }
        });
    }

    /*
    =============================================================================
    ============================= Package private ===============================
    =============================================================================
     */

    /**
     * Starts a new deck on the table. This function relies on the {@link CopyOnWriteArraySet}.
     *
     * @param card The initial card of the deck.
     */
    void newDeck(Card card) {
        if (isGameOver)
            return;
        OnTableDeck deck = new OnTableDeck(card);
        var counter = decksCounter.incrementAndGet();
        decks.add(Map.entry(counter, deck));
        eventBus.publish(new CardPlacedEvent(counter));
        System.out.println("New deck: " + card + ", position " + counter);
    }

    /**
     * Places a card on the deck located the specified position.
     * Due to threads racing to place a card, this operation might fail.
     *
     * @param card     Card to be placed.
     * @param position The position of the deck on which a card will be placed.
     * @return Boolean representing whether the operation was successful or not.
     */
    boolean put(Card card, int position) {
        if (isGameOver)
            return false;
        var deckOpt = getDeck(position);
        if (deckOpt.isPresent()) {
            var deck = deckOpt.get();
            String s = "Attempt: " + card.player.name + ", " + card + ", position " + position + " - ";
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

    /**
     * Searches all already placed decks to find one on which a specified card fits.
     *
     * @param card The card for which the function searches a fitting deck.
     * @return The position of a deck on which a card fits. None is returned if it does not exist.
     */
    Optional<Integer> fittingDeck(Card card) {
        if (isGameOver)
            return Optional.empty();
        return decks.stream()
                .filter(it -> !it.getValue().isCompleted())
                .filter(it -> it.getValue().cardFits(card))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    /**
     * Tests whether a card fits on a deck located at a specified position.
     *
     * @param card     Card to be tested.
     * @param position Position of the deck.
     * @return Boolean representing whether the card fits on the deck.
     */
    boolean fitsPosition(Card card, int position) {
        if (isGameOver)
            return false;
        var deckOpt = getDeck(position);
        return deckOpt.map(onTableDeck -> onTableDeck.cardFits(card)).orElse(false);
    }

    /**
     * Retrieves the reference of the deck located at specified position.
     *
     * @param position Position of the deck.
     * @return OnTableDeck at the position.
     */
    Optional<OnTableDeck> getDeck(Integer position) {
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
