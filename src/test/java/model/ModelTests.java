package model;

import events.EventBus;
import events.impl.ConcurrentEventBus;
import model.board.Table;
import model.card.Card;
import model.card.CardColour;
import model.card.CardNumber;
import model.deck.OnTableDeck;
import model.event.CardPlacedEvent;
import model.player.Bot;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTests {

    @Mock
    private EventBus<CardPlacedEvent> eventBusMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fittingDeckFoundOnTheSecondPosition() {
        var table = new Table(eventBusMock);
        table.startDeck(new Card(CardColour.BLUE, CardNumber.ONE, null));
        table.startDeck(new Card(CardColour.YELLOW, CardNumber.ONE, null));

        var card = new Card(CardColour.YELLOW, CardNumber.TWO, null);
        var position = table.findFittingDeck(card);
        assertTrue(position.isPresent() && position.get() == 2);

    }

    @Test
    public void onTableDeckIsNotCompleted() {
        var deck = new OnTableDeck(new Card(CardColour.BLUE, CardNumber.ONE, null));
        assertFalse(deck.isCompleted());
    }

    @Test
    public void onTableDeckIsCompleted() {
        var deck = new OnTableDeck(new Card(CardColour.BLUE, CardNumber.TEN, null));
        assertTrue(deck.isCompleted());
    }

    @Test
    public void onTableDeckCardFits() {
        var deck = new OnTableDeck(new Card(CardColour.BLUE, CardNumber.ONE, null));
        assertTrue(deck.cardFits(new Card(CardColour.BLUE, CardNumber.TWO, null)));
    }

    @Test
    public void testReentrantReadWriteLock() {
        var rwl = new ReentrantReadWriteLock();
        rwl.writeLock().lock();
        rwl.writeLock().lock();
    }

    @Test
    public void racingForPlacingACardOnAOnTableDesk() throws InterruptedException {
        var bus = new ConcurrentEventBus<CardPlacedEvent>();
        var table = new Table(eventBusMock);

        var bot1 = new Bot("foo", table);
        var bot2 = new Bot("bar", table);

        table.startDeck(new Card(CardColour.BLUE, CardNumber.ONE, bot1));

        var t1 = racingThread_racingForPlacingACardOnAOnTableDesk(table, bot1, bot2);
        var t2 = racingThread_racingForPlacingACardOnAOnTableDesk(table, bot2, bot1);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        var deck = table.getDeckAtPosition(1);

        assertTrue(deck.isPresent());
        assertEquals(2, deck.get().size());
    }

    private Thread racingThread_racingForPlacingACardOnAOnTableDesk(Table table, Player player1, Player player2) {
        return new Thread(() -> {
            var card = new Card(CardColour.BLUE, CardNumber.TWO, player1);
            var positionOpt = table.findFittingDeck(card);
            if (positionOpt.isPresent()) {
                var position = positionOpt.get();
                var condition = table.placeCardAtPosition(card, position);
                var deck = table.getDeckAtPosition(position);
                var topCardOpt = deck.map(OnTableDeck::peek);
                assertTrue(topCardOpt.isPresent());
                var topCard = topCardOpt.get();
                if (condition)
                    assertEquals(topCard.player, player1);
                else
                    assertEquals(topCard.player, player2);

            }
        });
    }
}
