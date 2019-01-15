package fun.mike.flapjack.beta;

import fun.mike.record.Record;

public interface Failure {
    int getNumber();

    String getLine();

    Record getRecord();

    String explain();

    void accept(FailureVisitor visitor);
}
