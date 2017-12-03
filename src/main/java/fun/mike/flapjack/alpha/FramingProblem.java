package fun.mike.flapjack.alpha;

public class FramingProblem implements Problem {
    private final Integer columnIndex;
    private final Integer charIndex;

    public FramingProblem(Integer columnIndex,
                          Integer charIndex) {
        this.columnIndex = columnIndex;
        this.charIndex = charIndex;
    }

    public String explain() {
        return String.format("Column %d was not properly framed (at character %d).",
                columnIndex + 1,
                charIndex + 1);
    }
}
