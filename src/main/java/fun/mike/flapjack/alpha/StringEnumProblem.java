package fun.mike.flapjack.alpha;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StringEnumProblem implements Problem {
    private final String id;
    private final String value;
    private final List<String> options;

    @JsonCreator
    public StringEnumProblem(@JsonProperty("id") String id,
                             @JsonProperty("value") String value,
                             @JsonProperty("options") List<String> options) {
        this.id = id;
        this.value = value;
        this.options = options;
    }

    public String explain() {
        Integer count = options.size();
        String optionsStr = options.stream()
                .map(option -> String.format("\"%s\"", option))
                .collect(Collectors.joining(", "));
        return String.format("Expected field \"%s\" with value \"%s\" must be one of the following %d string options: %s",
                id,
                value,
                count,
                optionsStr);

    }
}
