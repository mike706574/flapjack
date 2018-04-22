package fun.mike.flapjack.alpha;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fun.mike.record.alpha.Record;

/**
 * A container object that can contain a serialized record or a collection of
 * problems from serialization.
 */
@JsonSerialize(as = ISerializationResult.class)
public class SerializationResult implements ISerializationResult {
    private final String value;
    private final Record record;
    private final List<Problem> problems;

    @JsonCreator
    public SerializationResult(@JsonProperty("value") String value,
            @JsonProperty("record") Record record,
            @JsonProperty("problems") List<Problem> problems) {
        this.value = value;
        this.record = record;
        this.problems = new LinkedList<>(problems);
    }

    public static SerializationResult ok(String value, Record record) {
        return new SerializationResult(value, null, new LinkedList<>());
    }

    public static SerializationResult withProblem(String value,
                                                  Record record,
                                                  Problem problem) {
        List<Problem> problems = new LinkedList<>();
        problems.add(problem);
        return new SerializationResult(value, record, problems);
    }

    public static SerializationResult withProblems(String value,
                                                   Record record,
                                                   List<Problem> problems) {
        return new SerializationResult(value, record, problems);
    }

    public String orElseThrow() {
        return orElseThrow(result -> {
            throw new SerializationException(result);
        });
    }

    public <E extends Throwable> String orElseThrow(Function<SerializationResult, ? extends E> exceptionBuilder) throws E {
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

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Record getRecord() {
        return record;
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
        return "SerializationResult{" +
                "value='" + value + '\'' +
                ", record=" + record +
                ", problems=" + problems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializationResult that = (SerializationResult) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(record, that.record) &&
                Objects.equals(problems, that.problems);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, record, problems);
    }
}
