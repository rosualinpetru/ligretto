package core.entities;

import core.card.Card;
import core.deck.OnTableDeck;
import core.event.CardPlacedEvent;
import core.exception.IllegalTableCallError;
import events.EventBus;
import events.impl.ConcurrentEventBus;
import events.impl.EventListener;
import gui.managers.BoardManager;
import gui.managers.FrameManager;
import org.javatuples.Pair;

import java.time.OffsetTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Table {

    /* ======== FIELDS ======== */
    private final EventBus<CardPlacedEvent> eventBus = new ConcurrentEventBus<>();
    private final Checker checker = new Checker(this);

    private final List<Player> players = new ArrayList<>();
    private final List<Thread> botThreads = new ArrayList<>();

    private Player winner;

    private static int roundCounter = 0;

    private final AtomicInteger decksCounter = new AtomicInteger();
    private volatile TableState state = TableState.INITIALIZING;
    private final Semaphore stateSemaphore = new Semaphore(1);

    public Phaser pauseGamePhaser;

    private BoardManager boardManager;
    private FrameManager frameManager;

    /**
     * Although expensive, it allows both reads and written on all
     * decks to happen concurrently.
     * It is also important to know the position of each decks it is easier
     * to identify them
     */
    private final Set<Map.Entry<Integer, OnTableDeck>> decks = new CopyOnWriteArraySet<>();

    /**
     * It is nearly impossible to predict in which state
     * a certain thread will be, thus stopping the threads becomes a problem.
     * Upon ending the game, we will make a copy of the table configuration so the
     * points will not be altered. This way, the other threads can end their execution
     * without altering the state of the table.
     */
    private Set<Map.Entry<Integer, OnTableDeck>> endCopy;

    /* ======== CONSTR ======== */
    public Table() {
        var listener = new EventListener<CardPlacedEvent>();
        listener.subscribe(event -> checker.setTimestamp(OffsetTime.now()));
        eventBus.registerListener(listener);
    }

    public Table(BoardManager boardManager) {
        this();
        this.boardManager = boardManager;
        this.frameManager = FrameManager.getInstance();
    }

    /* ======== THREAD ======== */
    private class TableThread implements Runnable {
        /**
         * Upon starting the game, all player threads will also start.
         * Also, a round blocks the the thread which started it.
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
            eventBus.dispose();
            if (winner != null) {
                System.out.println(winner.name + " WON THIS ROUND!");
                boardManager.showMessageDialog(winner.name + " WON THIS ROUND!");
            } else {
                System.out.println("TIE!");
                boardManager.showMessageDialog("TIE!");
            }
            score();
            state();
            frameManager.switchToEndFrame();
        }

        /**
         * Prints the score of each player after the game is finished.
         */
        private void score() {
            players.stream()
                    .map(player -> {
                        if (endCopy == null) {
                            // todo check why is null
                            endCopy = new HashSet<>(decks);
                        }
                        var score = endCopy.stream()
                                .flatMap(entry -> entry.getValue().getAll().stream())
                                .filter(card -> card.player().equals(player))
                                .count();
                        return Pair.with(player.name, score);
                    }).forEach(System.out::println);
        }

        /**
         * Prints the state of the game after it has finished.
         */
        private void state() {
            System.out.println(this);
            players.forEach(player -> {
                if (player instanceof Bot bot) {
                    System.out.println(bot);
                }
            });
        }

        @Override
        public String toString() {
            return decks.stream().map(Map.Entry::getValue).collect(Collectors.toList()).toString();
        }
    }

    /* ======== PUBLIC ======== */

    /**
     * Upon registration, the player has the table set using the setter.
     * Afterwards, a thread is created for each player.
     * Furthermore, each player subscribes to the event bus of placed cards.
     *
     * @param player The player that joins the table.
     * @throws IllegalTableCallError if the game is not ended.
     */
    public void register(Player player) {
        if (state != TableState.INITIALIZING)
            throw new IllegalTableCallError(TableState.INITIALIZING, state);

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
     * Start a new round. This function also resets the configuration on the table.
     *
     * @throws InterruptedException  join thread.
     * @throws IllegalTableCallError if the game is not ended.
     */
    public void start() throws InterruptedException {
        if (state != TableState.INITIALIZING)
            throw new IllegalTableCallError(TableState.INITIALIZING, state);

        // 1 =  checker
        pauseGamePhaser = new Phaser(players.size() + 1) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                checker.checkGameForTie();
                return super.onAdvance(phase, registeredParties);
            }
        };

        state = TableState.ONGOING;
        botThreads.clear();
        players.forEach(it -> {
                    var thread = new Thread(it);
                    thread.setName(it.name);
                    botThreads.add(thread);
                }
        );
        decksCounter.set(0);
        decks.clear();
        winner = null;
        endCopy = null;
        roundCounter++;


        var checkerThread = new Thread(checker);
        checkerThread.setDaemon(true);
        checkerThread.setName("checker");

        var tableThread = new Thread(new TableThread());
        tableThread.setName("round-" + roundCounter);

        checkerThread.start();
        tableThread.start();
        tableThread.join();
    }


    /* ======== PACKAGE ======== */
    public TableState getState() {
        return state;
    }

    /**
     * While the game is frozen, all entities are stuck, leaving all locks and semaphores
     * available to the checker.
     */
    void pause() {
        try {
            stateSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        state = TableState.PAUSED;
        stateSemaphore.release();

        pauseGamePhaser.arriveAndAwaitAdvance();
    }

    void resume() {
        try {
            stateSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        state = TableState.ONGOING;
        stateSemaphore.release();
    }

    public TableData getAllData() {
        var visibleCards = players.stream().flatMap(Player::getVisibleCards).toList();
        return new TableData(visibleCards, decks.stream().map(Map.Entry::getValue).toList());
    }

    /**
     * Upon ending the game, all player threads are interrupted.
     * It is synchronized because only one player can finish the game.
     * It is a rare case, but it is possible that two players can win
     * at the same time.
     *
     * @throws IllegalTableCallError if the game is already ended.
     */
    synchronized void end(Player player) {
        if (state != TableState.ONGOING && state != TableState.PAUSED)
            throw new IllegalTableCallError(TableState.ONGOING, state);

        try {
            stateSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        state = TableState.ENDED;
        stateSemaphore.release();

        pauseGamePhaser.forceTermination();

        endCopy = new HashSet<>(decks);
        botThreads.forEach(Thread::interrupt);
        winner = player;
    }

    /**
     * Starts a new deck on the table. This function relies on the {@link CopyOnWriteArraySet}.
     *
     * @param card The initial card of the deck.
     * @throws IllegalTableCallError if the game is already ended.
     */
    void newDeck(Card card) {
        if (state != TableState.ONGOING)
            throw new IllegalTableCallError(TableState.ONGOING, state);

        OnTableDeck deck = new OnTableDeck(card);
        var counter = decksCounter.incrementAndGet();
        decks.add(Map.entry(counter, deck));

        boardManager.putCardAtPosition(card, counter);

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
     * @throws IllegalTableCallError if the game is already ended.
     */
    boolean put(Card card, int position) {
        if (state != TableState.ONGOING)
            throw new IllegalTableCallError(TableState.ONGOING, state);

        var deckOpt = getDeck(position);
        if (deckOpt.isPresent()) {
            var deck = deckOpt.get();
            String s = "Attempt: " + card.player().name + ", " + card + ", position " + position + " - ";
            var condition = deck.put(card);
            if (condition) {
                s += "SUCCESS";
                boardManager.putCardAtPosition(card, position);
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
     * @throws IllegalTableCallError if the game is already ended.
     */
    Optional<Integer> fittingDeck(Card card) {
        if (state != TableState.ONGOING)
            throw new IllegalTableCallError(TableState.ONGOING, state);

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
     * @throws IllegalTableCallError if the game is already ended.
     */
    boolean fitsPosition(Card card, int position) {
        if (state != TableState.ONGOING)
            throw new IllegalTableCallError(TableState.ONGOING, state);

        var deckOpt = getDeck(position);
        return deckOpt.map(onTableDeck -> onTableDeck.cardFits(card)).orElse(false);
    }

    /**
     * Retrieves the reference of the deck located at specified position.
     *
     * @param position Position of the deck.
     * @return OnTableDeck at the position.
     * @throws IllegalTableCallError if the game is already ended.
     */
    Optional<OnTableDeck> getDeck(Integer position) {
        if (state != TableState.ONGOING)
            throw new IllegalTableCallError(TableState.ONGOING, state);

        return decks.stream()
                .filter(it -> Objects.equals(it.getKey(), position))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
