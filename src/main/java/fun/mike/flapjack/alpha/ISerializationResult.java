package fun.mike.flapjack.alpha;

import java.util.List;

import fun.mike.record.alpha.Record;

public interface ISerializationResult {
    String getValue();
    Record getRecord();
    List<Problem> getProblems();
}
