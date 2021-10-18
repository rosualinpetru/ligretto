package core.entities;

import events.EventBus;
import core.card.Card;
import core.card.CardColour;
import core.card.CardNumber;
import core.deck.OnTableDeck;
import core.event.CardPlacedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

        table.createNewDeck(new Card(CardColour.BLUE, CardNumber.ONE, null));
        table.createNewDeck(new Card(CardColour.YELLOW, CardNumber.ONE, null));

        var card = new Card(CardColour.YELLOW, CardNumber.TWO, null);
        var position = table.findFitPosition(card);
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
    public void racingForPlacingACardOnAOnTableDesk() throws InterruptedException {

        var table = new Table(eventBusMock);

        var bot1 = new Bot("foo");
        var bot2 = new Bot("bar");

        table.registerPlayer(bot1);
        table.registerPlayer(bot2);

        table.createNewDeck(new Card(CardColour.BLUE, CardNumber.ONE, bot1));

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
            var positionOpt = table.findFitPosition(card);
            if (positionOpt.isPresent()) {
                var position = positionOpt.get();
                var condition = table.putAtPosition(card, position);
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
