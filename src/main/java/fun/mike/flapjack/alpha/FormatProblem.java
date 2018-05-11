package fun.mike.flapjack.alpha;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Problem for when a format is defined incorrectly.
 */
public class FormatProblem implements Problem, Serializable {
    private final String message;

    @JsonCreator
    public FormatProblem(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String explain() {
        return message;
    }

    /**
     * @return a message describing the format problem
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "FormatProblem{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormatProblem that = (FormatProblem) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }
}
