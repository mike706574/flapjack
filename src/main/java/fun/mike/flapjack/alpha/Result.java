package fun.mike.flapjack.alpha;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as = IResult.class)
public class Result implements IResult {
    private final Record record;
    private final List<Problem> problems;

    @JsonCreator
    public Result(@JsonProperty("record") Record record,
                  @JsonProperty("problems") List<Problem> problems) {
        this.record = record;
        this.problems = new LinkedList<>(problems);
    }

    public static Result ok(Record record) {
        return new Result(record, new LinkedList<>());
    }

    public static Result withProblem(Record record, Problem problem) {
        List<Problem> problems = new LinkedList<>();
        problems.add(problem);
        return new Result(record, problems);
    }

    public static Result withProblems(Record record, List<Problem> problems) {
        return new Result(record, problems);
    }

    public <E extends Throwable> Record orElseThrow(Function<List<Problem>, ? extends E> exceptionBuilder) throws E {
        if (problems.isEmpty()) {
            return record;
        } else {
            throw exceptionBuilder.apply(problems);
        }
    }

    public boolean isOk() {
        return problems.isEmpty();
    }

    public boolean hasProblems() {
        return !problems.isEmpty();
    }

    public Record getRecord() {
        return record;
    }

    public List<Problem> getProblems() {
        return new LinkedList<Problem>(this.problems);
    }

    @Override
    public String toString() {
        return "Result{" +
                "record=" + record +
                ", problems=" + problems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (record != null ? !record.equals(result.record) : result.record != null) return false;
        return problems != null ? problems.equals(result.problems) : result.problems == null;
    }

    @Override
    public int hashCode() {
        int result = record != null ? record.hashCode() : 0;
        result = 31 * result + (problems != null ? problems.hashCode() : 0);
        return result;
    }
}
