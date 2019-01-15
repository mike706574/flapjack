package fun.mike.flapjack.beta;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Problem used when a format includes a field with an invalid type.
 */
public class NoSuchTypeProblem implements Problem, Serializable {
    private final String id;
    private final String type;

    @JsonCreator
    public NoSuchTypeProblem(@JsonProperty("id") String id,
                             @JsonProperty("type") String type) {
        this.id = id;
        this.type = type;
    }


    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String explain() {
        return String.format("Type \"%s\" specified for field \"%s\" does not exist.",
                             type,
                             id);
    }

    @Override
    public String toString() {
        return "NoSuchTypeProblem{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoSuchTypeProblem that = (NoSuchTypeProblem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
