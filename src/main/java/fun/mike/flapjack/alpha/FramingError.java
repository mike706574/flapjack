package fun.mike.flapjack.alpha;

public class FramingError implements Error {
    private final Integer columnIndex;
    private final Integer charIndex;

    public FramingError(Integer columnIndex,
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
