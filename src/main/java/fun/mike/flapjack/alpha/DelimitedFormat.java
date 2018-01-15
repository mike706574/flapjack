package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DelimitedFormat implements Format, Serializable {
    private final String id;
    private final String description;
    private final String delimiter;
    private final Framing framing;
    private final String frameDelimiter;
    private final Integer offset;
    private final List<Column> columns;

    @JsonCreator
    public DelimitedFormat(@JsonProperty("id") String id,
            @JsonProperty("description") String description,
            @JsonProperty("delimiter") String delimiter,
            @JsonProperty("framing") Framing framing,
            @JsonProperty("frameDelimiter") String frameDelimiter,
            @JsonProperty("offset") Integer offset,
            @JsonProperty("columns") List<Column> columns) {
        this.id = id;
        this.description = description;
        this.delimiter = delimiter;
        this.framing = framing;
        this.frameDelimiter = frameDelimiter;
        this.columns = Collections.unmodifiableList(columns);
        this.offset = offset;
    }

    public static DelimitedFormat unframed(String id,
            String description,
            String delimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, Framing.NONE, null, 0, columns);
    }

    public static DelimitedFormat alwaysFramed(String id,
            String description,
            String delimiter,
            String frameDelimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, Framing.REQUIRED, frameDelimiter, 0, columns);
    }

    public static DelimitedFormat optionallyFramed(String id,
            String description,
            String delimiter,
            String frameDelimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, Framing.OPTIONAL, frameDelimiter, 0, columns);
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public char getDelimiter() {
        return delimiter.charAt(0);
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

    public Optional<String> getFrameDelimiter() {
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
                ", delimiter='" + delimiter + '\'' +
                ", framing=" + framing +
                ", frameDelimiter='" + frameDelimiter + '\'' +
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
                framing == that.framing &&
                Objects.equals(frameDelimiter, that.frameDelimiter) &&
                Objects.equals(offset, that.offset) &&
                Objects.equals(columns, that.columns);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, description, delimiter, framing, frameDelimiter, offset, columns);
    }
}
