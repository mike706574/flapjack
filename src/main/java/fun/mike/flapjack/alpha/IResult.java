package fun.mike.flapjack.alpha;

import java.util.List;

public interface IResult<T> {
    T getValue();

    List<Problem> getProblems();
}
