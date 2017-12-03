package fun.mike.flapjack.alpha;

public class LengthMismatchProblem implements Problem {
    private final Integer expectedLength;
    private final Integer actualLength;

    public LengthMismatchProblem(Integer expectedLength,
                                 Integer actualLength) {
        this.expectedLength = expectedLength;
        this.actualLength = actualLength;
    }

    public String explain() {
        return String.format("We expected the record to be %d characters long, but it was actually %d characters long.",
                expectedLength,
                actualLength);
    }
}
