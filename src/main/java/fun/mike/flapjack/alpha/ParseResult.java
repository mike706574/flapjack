package fun.mike.flapjack.alpha;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fun.mike.record.alpha.Record;

/**
 * A container object that can contain a parsed record or a collection of
 * problems when parsing.
 */
public class ParseResult implements Result<Record> {
    private final Record value;
    private final String line;
    private final List<Problem> problems;

    @JsonCreator
    public ParseResult(@JsonProperty("value") Record value,
                       @JsonProperty("line") String line,
                       @JsonProperty("problems") List<Problem> problems) {
        this.value = value;
        this.line = line;
        this.problems = new LinkedList<>(problems);
    }

    // Factory methods
    public static ParseResult ok(Record value, String line) {
        return new ParseResult(value, line, new LinkedList<>());
    }

    public static ParseResult withProblem(Record value,
                                          String line,
                                          Problem problem) {
        List<Problem> problems = new LinkedList<>();
        problems.add(problem);
        return new ParseResult(value, line, problems);
    }

    public static ParseResult withProblems(Record value,
                                           String line,
                                           List<Problem> problems) {
        return new ParseResult(value, line, problems);
    }

    public Record orElse(Record defaultValue) {
        if (problems.isEmpty()) {
            return value;
        } else {
            return defaultValue;
        }
    }

    public Record orElseThrow() {
        return orElseThrow(result -> {
            throw new ParseException(result);
        });
    }

    public <E extends Throwable> Record orElseThrow(Function<ParseResult, ? extends E> exceptionBuilder) throws E {
        if (problems.isEmpty()) {
            return value;
        } else {
            throw exceptionBuilder.apply(this);
        }
    }

    @JsonIgnore
    public boolean isOk() {
        return problems.isEmpty();
    }

    @JsonIgnore
    public boolean hasProblems() {
        return !problems.isEmpty();
    }

    @Override
    public Record getValue() {
        return value;
    }

    public String getLine() {
        return line;
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
    public String toString() {
        return "ParseResult{" +
                "value=" + value +
                ", line='" + line + '\'' +
                ", problems=" + problems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParseResult that = (ParseResult) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(line, that.line) &&
                Objects.equals(problems, that.problems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, line, problems);
    }
}
