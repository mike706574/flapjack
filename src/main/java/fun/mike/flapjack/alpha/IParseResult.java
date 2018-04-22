package fun.mike.flapjack.alpha;

import java.util.List;

import fun.mike.record.alpha.Record;

public interface IParseResult {
    Record getValue();
    String getLine();
    List<Problem> getProblems();
}
