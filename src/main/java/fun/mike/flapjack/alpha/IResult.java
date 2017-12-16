package fun.mike.flapjack.alpha;

import java.util.List;

import fun.mike.record.alpha.Record;

public interface IResult {
    public Record getRecord();

    public List<Problem> getProblems();
}
