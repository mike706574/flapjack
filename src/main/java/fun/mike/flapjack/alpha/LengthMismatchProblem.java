package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LengthMismatchProblem implements Problem {
    private final Integer expectedLength;
    private final Integer actualLength;

    @JsonCreator
    public LengthMismatchProblem(@JsonProperty("expectedLength") Integer expectedLength,
                                 @JsonProperty("actualLength") Integer actualLength) {
        this.expectedLength = expectedLength;
        this.actualLength = actualLength;
    }

    public String explain() {
        return String.format("We expected the record to be %d characters long, but it was actually %d characters long.",
                expectedLength,
                actualLength);
    }
}
