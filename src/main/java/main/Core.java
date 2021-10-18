package main;

import events.impl.ConcurrentEventBus;
import core.entities.Table;
import core.event.CardPlacedEvent;
import core.entities.Bot;

import java.util.ArrayList;
import java.util.List;

public class Core {
    public static void main(String[] args) throws InterruptedException {
        var eventBus = new ConcurrentEventBus<CardPlacedEvent>();

        var table = new Table(eventBus);

        List<Bot> bots = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            bots.add(new Bot("id" + i));
        }

        bots.forEach(table::registerPlayer);

        table.startGame();

        table.showResults();
        table.showEndingState();

    }
}
