package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NoSuchTypeProblem implements Problem {
    private final String id;
    private final String type;

    @JsonCreator
    public NoSuchTypeProblem(@JsonProperty("id") String id,
                             @JsonProperty("type") String type) {
        this.id = id;
        this.type = type;
    }

    public String explain() {
        return String.format("Type \"%s\" specified for field \"%s\" does not exist.",
                type,
                id);
    }
}
