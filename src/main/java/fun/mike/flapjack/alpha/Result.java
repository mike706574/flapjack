package fun.mike.flapjack.alpha;

public interface Result<T> {
    T getValue();

    T orElse(T other);

    T orElseThrow();
}
