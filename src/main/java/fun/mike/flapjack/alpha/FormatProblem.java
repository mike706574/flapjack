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
}
