package main;

import core.entities.Table;
import core.entities.Bot;


public class Core {

    public static void main(String[] args) throws InterruptedException {
        var table = new Table();

        // Each bot need to be registered to a table
        int NR_OF_PLAYERS = 5;
        for (int i = 0; i < NR_OF_PLAYERS; i++) {
            table.register(new Bot("id" + i));
        }

        // The round also starts in a different thread from the main.
        var tableThread = new Thread(table);
        tableThread.setName("round1");
        tableThread.start();
        tableThread.join();
        table.score();
        table.state();

    }
}
