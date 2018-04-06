package fun.mike.flapjack.alpha;

import java.util.List;

import fun.mike.record.alpha.Record;

public interface IResult {
    Record getRecord();

    List<Problem> getProblems();
}
