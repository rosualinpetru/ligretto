package core.entities;

import core.card.Card;
import core.card.CardColour;
import core.card.CardNumber;
import core.deck.OnTableDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTests {
    @Test
    public void fittingDeckFoundOnTheSecondPosition() {

        var table = new Table();

        table.newDeck(new Card(CardColour.BLUE, CardNumber.ONE, null));
        table.newDeck(new Card(CardColour.YELLOW, CardNumber.ONE, null));

        var card = new Card(CardColour.YELLOW, CardNumber.TWO, null);
        var position = table.fittingDeck(card);
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

        var table = new Table();

        var bot1 = new Bot("foo");
        var bot2 = new Bot("bar");

        table.register(bot1);
        table.register(bot2);

        table.newDeck(new Card(CardColour.BLUE, CardNumber.ONE, bot1));

        var t1 = racingThread_racingForPlacingACardOnAOnTableDesk(table, bot1, bot2);
        var t2 = racingThread_racingForPlacingACardOnAOnTableDesk(table, bot2, bot1);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        var deck = table.getDeck(1);

        assertTrue(deck.isPresent());
        assertEquals(2, deck.get().size());
    }

    private Thread racingThread_racingForPlacingACardOnAOnTableDesk(Table table, Player player1, Player player2) {
        return new Thread(() -> {
            var card = new Card(CardColour.BLUE, CardNumber.TWO, player1);
            var positionOpt = table.fittingDeck(card);
            if (positionOpt.isPresent()) {
                var position = positionOpt.get();
                var condition = table.put(card, position);
                var deck = table.getDeck(position);
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
