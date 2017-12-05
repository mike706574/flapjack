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

    @Override
    public String toString() {
        return "FramingProblem{" +
                "columnIndex=" + columnIndex +
                ", charIndex=" + charIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FramingProblem that = (FramingProblem) o;

        if (columnIndex != null ? !columnIndex.equals(that.columnIndex) : that.columnIndex != null) return false;
        return charIndex != null ? charIndex.equals(that.charIndex) : that.charIndex == null;
    }

    @Override
    public int hashCode() {
        int result = columnIndex != null ? columnIndex.hashCode() : 0;
        result = 31 * result + (charIndex != null ? charIndex.hashCode() : 0);
        return result;
    }
}
