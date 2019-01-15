package fun.mike.flapjack.alpha;

public interface InputChannel extends AutoCloseable {
    InputResult take();

    boolean hasMore();

    void close();
}

