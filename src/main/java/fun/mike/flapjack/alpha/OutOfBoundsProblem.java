package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OutOfBoundsProblem implements Problem {
    private final String fieldId;
    private final Integer end;
    private final Integer length;

    @JsonCreator
    public OutOfBoundsProblem(@JsonProperty("fieldId") String id,
                              @JsonProperty("end") Integer end,
                              @JsonProperty("length") Integer length) {
        this.fieldId = id;
        this.end = end;
        this.length = length;
    }

    public String explain() {
        return String.format("The %s ends at character %d, but the line was only %d characters long.",
                fieldId,
                end,
                length);
    }

    public String getFieldId() {
        return this.fieldId;
    }

    public Integer getEnd() {
        return this.end;
    }

    public Integer getLength() {
        return this.length;
    }
}
