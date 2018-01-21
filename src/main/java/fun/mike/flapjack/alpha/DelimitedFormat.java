package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DelimitedFormat implements Format, Serializable {
    private final String id;
    private final String description;
    private final Character delimiter;
    private final Boolean endingDelimiter;
    private final Framing framing;
    private final Character frameDelimiter;
    private final Integer offset;
    private final List<Column> columns;

    @JsonCreator
    public DelimitedFormat(@JsonProperty("id") String id,
            @JsonProperty("description") String description,
            @JsonProperty("delimiter") Character delimiter,
            @JsonProperty("endingDelimiter") Boolean endingDelimiter,
            @JsonProperty("framing") Framing framing,
            @JsonProperty("frameDelimiter") Character frameDelimiter,
            @JsonProperty("offset") Integer offset,
            @JsonProperty("columns") List<Column> columns) {
        this.id = id;
        this.description = description;
        this.delimiter = delimiter;
        this.endingDelimiter = endingDelimiter;
        this.framing = framing;
        this.frameDelimiter = frameDelimiter;
        this.columns = Collections.unmodifiableList(columns);
        this.offset = offset;
    }

    public static DelimitedFormat unframed(String id,
            String description,
            Character delimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, false, Framing.NONE, null, 0, columns);
    }

    public static DelimitedFormat alwaysFramed(String id,
            String description,
            Character delimiter,
            Character frameDelimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, false, Framing.REQUIRED, frameDelimiter, 0, columns);
    }

    public static DelimitedFormat optionallyFramed(String id,
            String description,
            Character delimiter,
            Character frameDelimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, false, Framing.OPTIONAL, frameDelimiter, 0, columns);
    }

    public DelimitedFormat withOffset(Integer offset) {
        return new DelimitedFormat(id,
                                   description,
                                   delimiter,
                                   endingDelimiter, framing,
                                   frameDelimiter,
                                   offset,
                                   new LinkedList<>(columns));
    }

    public DelimitedFormat withEndingDelimiter() {
        return new DelimitedFormat(id,
                                   description,
                                   delimiter,
                                   true,
                                   framing,
                                   frameDelimiter,
                                   offset,
                                   new LinkedList<>(columns));
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Character getDelimiter() {
        return delimiter;
    }

    public Boolean hasEndingDelimiter() {
        return endingDelimiter;
    }

    public Framing getFraming() {
        return framing;
    }

    @JsonIgnore
    public boolean isFramed() {
        return framing == Framing.OPTIONAL || framing == Framing.REQUIRED;
    }

    @JsonIgnore
    public boolean framingRequired() {
        return framing == Framing.REQUIRED;
    }

    public Optional<Character> getFrameDelimiter() {
        if (frameDelimiter == null) {
            return Optional.empty();
        }

        return Optional.of(frameDelimiter);
    }

    public Integer getOffset() {
        return offset;
    }

    public List<Column> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return "DelimitedFormat{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", delimiter=" + delimiter +
                ", endingDelimiter=" + endingDelimiter +
                ", framing=" + framing +
                ", frameDelimiter=" + frameDelimiter +
                ", offset=" + offset +
                ", columns=" + columns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelimitedFormat that = (DelimitedFormat) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(delimiter, that.delimiter) &&
                Objects.equals(endingDelimiter, that.endingDelimiter) &&
                framing == that.framing &&
                Objects.equals(frameDelimiter, that.frameDelimiter) &&
                Objects.equals(offset, that.offset) &&
                Objects.equals(columns, that.columns);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, description, delimiter, endingDelimiter, framing, frameDelimiter, offset, columns);
    }
}
