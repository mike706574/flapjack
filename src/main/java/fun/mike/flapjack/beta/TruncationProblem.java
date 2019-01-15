package fun.mike.flapjack.beta;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Problem used when a value does not fit in a fixed-width record during
 * serialization.
 */
public class TruncationProblem implements Problem {
    private final String id;
    private final String type;
    private final Integer length;
    private final String value;

    @JsonCreator
    public TruncationProblem(@JsonProperty("id") String id,
                             @JsonProperty("type") String type,
                             @JsonProperty("length") Integer length,
                             @JsonProperty("value") String value) {
        this.id = id;
        this.type = type;
        this.length = length;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getLength() {
        return length;
    }

    public String getValue() {
        return value;
    }

    public String explain() {
        return String.format("Field \"%s\" of type \"%s\" with serialized value \"%s\" must be %d characters or less.",
                             id,
                             type,
                             value,
                             length);
    }

    @Override
    public String toString() {
        return "TruncationProblem{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", length=" + length +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruncationProblem that = (TruncationProblem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(length, that.length) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, length, value);
    }
}
