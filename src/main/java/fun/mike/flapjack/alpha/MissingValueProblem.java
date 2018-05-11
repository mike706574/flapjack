package fun.mike.flapjack.alpha;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Problem used when a value is missing for a non-nullable field.
 */
public class MissingValueProblem implements Problem, Serializable {
    private final String id;
    private final String type;

    @JsonCreator
    public MissingValueProblem(@JsonProperty("id") String id,
                               @JsonProperty("type") String type) {
        this.id = id;
        this.type = type;
    }

    /**
     * @return the id of the field
     */
    public String getId() {
        return id;
    }

    /**
     * @return the type of the field
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MissingValueProblem{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MissingValueProblem that = (MissingValueProblem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String explain() {
        return String.format("Missing required value for field \"%s\" of type \"%s\".",
                             id,
                             type);
    }
}
