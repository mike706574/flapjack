package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeProblem implements Problem {
    private final String id;
    private final String type;
    private final String value;

    @JsonCreator
    public TypeProblem(@JsonProperty("id") String id,
                       @JsonProperty("type") String type,
                       @JsonProperty("value") String value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public String explain() {
        return String.format("Expected field \"%s\" with value \"%s\" to be a \"%s\".",
                id,
                value,
                type);
    }

    @Override
    public String toString() {
        return "TypeProblem{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeProblem that = (TypeProblem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
