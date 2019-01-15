package fun.mike.flapjack.beta;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Problem used when a fixed-width record is not long enough to contain a
 * field.
 */
public class OutOfBoundsProblem implements Problem, Serializable {
    private final String id;
    private final Integer end;
    private final Integer length;

    @JsonCreator
    public OutOfBoundsProblem(@JsonProperty("id") String id,
                              @JsonProperty("end") Integer end,
                              @JsonProperty("length") Integer length) {
        this.id = id;
        this.end = end;
        this.length = length;
    }

    public String explain() {
        return String.format("The field \"%s\" ends at character %d, but the line was only %d characters long.",
                             id,
                             end,
                             length);
    }

    /**
     * @return the id of the field
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return the character index of the end of the field
     */
    public Integer getEnd() {
        return this.end;
    }

    /**
     * @return the length of the fixed-width record
     */
    public Integer getLength() {
        return this.length;
    }

    @Override
    public String toString() {
        return "OutOfBoundsProblem{" +
                "id='" + id + '\'' +
                ", end=" + end +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutOfBoundsProblem that = (OutOfBoundsProblem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        return length != null ? length.equals(that.length) : that.length == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        return result;
    }
}
