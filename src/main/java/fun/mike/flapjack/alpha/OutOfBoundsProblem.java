package fun.mike.flapjack.alpha;

public class OutOfBoundsProblem implements Problem {
    private final String fieldId;
    private final Integer fieldEnd;
    private final Integer lineLength;

    public OutOfBoundsProblem(String fieldId,
                              Integer fieldEnd,
                              Integer lineLength) {
        this.fieldId = fieldId;
        this.fieldEnd = fieldEnd;
        this.lineLength = lineLength;
    }

    public String explain() {
        return String.format("The %s ends at character %d, but the line was only %d characters long.",
                fieldId,
                fieldEnd,
                lineLength);
    }

    public String getFieldId() {
        return this.fieldId;
    }

    public Integer getFieldEnd() {
        return this.fieldEnd;
    }

    public Integer getLineLength() {
        return this.lineLength;
    }
}
