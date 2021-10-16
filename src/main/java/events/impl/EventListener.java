package events.impl;

import java.util.function.Consumer;

public class EventListener<T> implements events.EventListener<T> {

    private Consumer<T> callback;

    @Override
    public void subscribe(Consumer<T> callback) {
        this.callback = callback;
    }

    @Override
    public void notify(T value) {
        if (callback != null) {
            callback.accept(value);
        }
    }
}
