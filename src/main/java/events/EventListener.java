package events;

import java.util.function.Consumer;

public interface EventListener<T> {

    void notify(T value);

    void subscribe(Consumer<T> callback);
}
