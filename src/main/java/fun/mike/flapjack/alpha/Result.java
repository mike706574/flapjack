package fun.mike.flapjack.alpha;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fun.mike.record.alpha.Record;


@JsonSerialize(as = IResult.class)
public class Result implements IResult {
    private final String line;
    private final Record record;
    private final List<Problem> problems;

    @JsonCreator
    public Result(@JsonProperty("line") String line,
            @JsonProperty("record") Record record,
            @JsonProperty("problems") List<Problem> problems) {
        this.line = line;
        this.record = record;
        this.problems = new LinkedList<>(problems);
    }

    public static Result ok(Record record) {
        return new Result(null, record, new LinkedList<>());
    }

    public static Result ok(String line) {
        return new Result(line, null, new LinkedList<>());
    }

    public static Result ok(String line, Record record) {
        return new Result(line, record, new LinkedList<>());
    }

    public static Result withProblem(Record record, Problem problem) {
        List<Problem> problems = new LinkedList<>();
        problems.add(problem);
        return new Result(null, record, problems);
    }

    public static Result withProblem(String line, Problem problem) {
        List<Problem> problems = new LinkedList<>();
        problems.add(problem);
        return new Result(line, null, problems);
    }

    public static Result withProblem(String line, Record record, Problem problem) {
        List<Problem> problems = new LinkedList<>();
        problems.add(problem);
        return new Result(line, record, problems);
    }

    public static Result withProblems(Record record, List<Problem> problems) {
        return new Result(null, record, problems);
    }

    public static Result withProblems(String line, List<Problem> problems) {
        return new Result(line, null, problems);
    }

    public static Result withProblems(String line, Record record, List<Problem> problems) {
        return new Result(line, record, problems);
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

    public String getLine() {
        return line;
    }

    public List<Problem> getProblems() {
        return new LinkedList<Problem>(this.problems);
    }

    @Override
    public String toString() {
        if (line == null) {
            return "Result{" +
                    "record=" + record +
                    ", problems=" + problems +
                    '}';
        }

        if (record == null) {
            return "Result{" +
                    "line=" + line +
                    ", problems=" + problems +
                    '}';
        }

        return "Result{" +
                "line=" + line +
                ", record=" + record +
                ", problems=" + problems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (line != null ? !line.equals(result.line) : result.line != null) return false;
        if (record != null ? !record.equals(result.record) : result.record != null) return false;
        return problems != null ? problems.equals(result.problems) : result.problems == null;
    }

    @Override
    public int hashCode() {
        int result = line != null ? line.hashCode() : 0;
        result = 31 * result + (record != null ? record.hashCode() : 0);
        result = 31 * result + (problems != null ? problems.hashCode() : 0);
        return result;
    }
}
