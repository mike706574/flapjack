package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FramingProblem implements Problem {
    private final Integer columnIndex;
    private final Integer charIndex;

    @JsonCreator
    public FramingProblem(@JsonProperty("columnIndex") Integer columnIndex,
                          @JsonProperty("charIndex") Integer charIndex) {
        this.columnIndex = columnIndex;
        this.charIndex = charIndex;
    }

    public String explain() {
        return String.format("Column %d was not properly framed (at character %d).",
                columnIndex + 1,
                charIndex + 1);
    }
}
