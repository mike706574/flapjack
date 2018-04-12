package fun.mike.flapjack.alpha;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(as = IResult.class)
public class Result<T> implements IResult<T> {
    private final T value;
    private final List<Problem> problems;

    @JsonCreator
    public Result(@JsonProperty("value") T value,
            @JsonProperty("problems") List<Problem> problems) {
        this.value = value;
        this.problems = new LinkedList<>(problems);
    }

    public static <T> Result<T> ok(T value) {
        return new Result<>(value, new LinkedList<>());
    }

    public static <T> Result<T> withProblem(T value, Problem problem) {
        List<Problem> problems = new LinkedList<>();
        problems.add(problem);
        return new Result<>(value, problems);
    }

    public static <T> Result<T> withProblems(T value, List<Problem> problems) {
        return new Result<>(value, problems);
    }

    public <E extends Throwable> T orElseThrow() throws E {
        return orElseThrow(result -> {
            throw new ResultException(result);
        });
    }

    public <E extends Throwable> T orElseThrow(Function<Result, ? extends E> exceptionBuilder) throws E {
        if (problems.isEmpty()) {
            return value;
        } else {
            throw exceptionBuilder.apply(this);
        }
    }

    public boolean isOk() {
        return problems.isEmpty();
    }

    public boolean hasProblems() {
        return !problems.isEmpty();
    }

    public T getValue() {
        return value;
    }

    public List<Problem> getProblems() {
        return new LinkedList<>(this.problems);
    }

    public String explain() {
        if (hasProblems()) {
            String problemList = problems.stream()
                    .map(Problem::explain)
                    .collect(Collectors.joining("\n"));
            return "Problems:\n" + problemList;
        }
        return "No problems.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return Objects.equals(value, result.value) &&
                Objects.equals(problems, result.problems);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, problems);
    }

    @Override
    public String toString() {
        return "Result{" +
                "value=" + value +
                ", problems=" + problems +
                '}';
    }
}
