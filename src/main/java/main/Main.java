package main;

import events.EventBus;
import events.impl.ConcurrentEventBus;
import events.impl.EventListener;
import gui.StartFrame;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        StartFrame menu = new StartFrame();
    }

    private static void eventBusExample() {
        EventBus<Integer> eventBus = new ConcurrentEventBus<>();

        EventListener<Integer> listener = new EventListener<>();
        eventBus.registerListener(listener);

        AtomicInteger count = new AtomicInteger();

        listener.subscribe(integer -> count.getAndIncrement());

        eventBus.dispose();
    }
}
