package fun.mike.flapjack.beta;

public interface InputChannel extends AutoCloseable {
    InputResult take();

    boolean hasMore();

    void close();
}

