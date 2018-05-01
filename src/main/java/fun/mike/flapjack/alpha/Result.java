package fun.mike.flapjack.alpha;

import java.util.List;

public interface Result<T> {
    T getValue();
    T orElse(T other);
    T orElseThrow();
}
