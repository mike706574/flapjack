package fun.mike.flapjack.alpha;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DelimitedFormat {
    private final String id;
    private final String description;
    private final String delimiter;
    private final Boolean framed;
    private final String frameDelimiter;
    private final List<Column> columns;

    @JsonCreator
    public DelimitedFormat(@JsonProperty("id") String id,
                           @JsonProperty("description") String description,
                           @JsonProperty("delimiter") String delimiter,
                           @JsonProperty("isFramed") Boolean framed,
                           @JsonProperty("frameDelimiter") String frameDelimiter,
                           @JsonProperty("columns") List<Column> columns) {
        this.id = id;
        this.description = description;
        this.delimiter = delimiter;
        this.framed = framed;
        this.frameDelimiter = frameDelimiter;
        this.columns = Collections.unmodifiableList(columns);
    }

    public static DelimitedFormat unframed(String id,
                                           String description,
                                           String delimiter,
                                           List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, false, null, columns);
    }

    public static DelimitedFormat framed(String id,
                                         String description,
                                         String delimiter,
                                         String frameDelimiter,
                                         List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, true, frameDelimiter, columns);
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

    public boolean isFramed() {
        return framed;
    }

    public Optional<String> getFrameDelimiter() {
        if (frameDelimiter == null) {
            return Optional.empty();
        }

        return Optional.of(frameDelimiter);
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
                ", framed=" + framed +
                ", frameDelimiter='" + frameDelimiter + '\'' +
                ", columns=" + columns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DelimitedFormat that = (DelimitedFormat) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (delimiter != null ? !delimiter.equals(that.delimiter) : that.delimiter != null) return false;
        if (framed != null ? !framed.equals(that.framed) : that.framed != null) return false;
        if (frameDelimiter != null ? !frameDelimiter.equals(that.frameDelimiter) : that.frameDelimiter != null)
            return false;
        return columns != null ? columns.equals(that.columns) : that.columns == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (delimiter != null ? delimiter.hashCode() : 0);
        result = 31 * result + (framed != null ? framed.hashCode() : 0);
        result = 31 * result + (frameDelimiter != null ? frameDelimiter.hashCode() : 0);
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        return result;
    }
}
