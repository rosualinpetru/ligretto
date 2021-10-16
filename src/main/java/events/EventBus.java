package events;

public interface EventBus<T> {
    void publish(T value);

    void registerListener(EventListener<T> eventListener);

    void unregisterListener(EventListener<T> eventListener);

    void dispose();
}
