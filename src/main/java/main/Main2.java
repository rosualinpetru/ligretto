package main;

import events.EventBus;
import events.impl.ConcurrentEventBus;
import model.board.Table;
import model.event.CardPlacedEvent;
import model.player.Bot;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        var eventBus = new ConcurrentEventBus<CardPlacedEvent>();

        var table = new Table();

        var bot1 = new Bot("foo", table, eventBus);
        var bot2 = new Bot("bar", table, eventBus);
        var bot3 = new Bot("baz", table, eventBus);
        var bot4 = new Bot("qux", table, eventBus);
        var bot5 = new Bot("quux", table, eventBus);

        var bot1Thread = new Thread(bot1);
        var bot2Thread = new Thread(bot2);
        var bot3Thread = new Thread(bot3);
        var bot4Thread = new Thread(bot4);
        var bot5Thread = new Thread(bot5);

        var t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    System.out.println("Table: " + table);
                    bot1.showStatus();
                    bot2.showStatus();
                    bot3.showStatus();
                    bot4.showStatus();
                    bot5.showStatus();
                    System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();

        bot1Thread.start();
        bot2Thread.start();
        bot3Thread.start();
        bot4Thread.start();
        bot5Thread.start();

        bot1Thread.join();
        bot2Thread.join();
        bot3Thread.join();
        bot4Thread.join();
        bot5Thread.join();

    }
}
