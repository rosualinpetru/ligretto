package gui.managers;

public interface PairConsumer<T, K> {
    public void accept(T t , K k);
}
