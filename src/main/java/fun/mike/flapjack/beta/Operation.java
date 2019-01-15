package fun.mike.flapjack.beta;

import java.util.Optional;

import fun.mike.record.Record;

public interface Operation {
    String getId();

    String getDescription();

    Optional<Record> run(Record value);
}
