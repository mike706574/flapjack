package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormatProblem implements Problem {
    private final String message;

    @JsonCreator
    public FormatProblem(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String explain() {
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
