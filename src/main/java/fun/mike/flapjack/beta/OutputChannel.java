package fun.mike.flapjack.beta;

import java.util.Optional;

import fun.mike.record.Record;

public interface OutputChannel<T> extends AutoCloseable {
    Optional<Failure> put(int number, String line, Record value);

    T getValue();

    void close();
}
