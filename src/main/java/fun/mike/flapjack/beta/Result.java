package fun.mike.flapjack.beta;

public interface Result<T> {
    T getValue();

    T orElse(T other);

    T orElseThrow();
}
