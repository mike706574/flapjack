package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fun.mike.record.alpha.Record;

/**
 * A delimited format used to parse and serialize delimited records.
 */
public class DelimitedFormat implements Format, Serializable {
    private final String id;
    private final String description;
    private final Character delimiter;
    private final Boolean endingDelimiter;
    private final Framing framing;
    private final Character frameDelimiter;
    private final Integer offset;
    private final List<Column> columns;

    private final DelimitedParser parser;
    private final DelimitedSerializer serializer;

    /**
     * Builds a delimited format.
     * @param id an identifier for the format
     * @param description a description of the format
     * @param delimiter a delimiter
     * @param endingDelimiter an ending delimiter
     * @param framing a framing type
     * @param frameDelimiter a frame delimiter
     * @param offset an offset
     * @param columns the columns
     */
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

        this.parser = new DelimitedParser(this);
        this.serializer = new DelimitedSerializer(this);
    }

    /**
     * Returns an unframed delimited format.
     * @param id an identifier for the format
     * @param description a description of th eformat
     * @param delimiter a delimiter
     * @param columns the columns
     * @return an unframed delimited format
     */
    public static DelimitedFormat unframed(String id,
            String description,
            Character delimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, false, Framing.NONE, null, 0, columns);
    }

    /**
     * Returns a delimited format with required framing.
     * @param id an identifier for the format
     * @param description a description of the format
     * @param delimiter a delimiter
     * @param frameDelimiter a frame delimiter
     * @param columns the columns
     * @return a delimited format with required framing
     */
    public static DelimitedFormat alwaysFramed(String id,
            String description,
            Character delimiter,
            Character frameDelimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, false, Framing.REQUIRED, frameDelimiter, 0, columns);
    }

    /**
     * Returns a delimited format with optional framing.
     * @param id an identifier for the format
     * @param description a description of the format
     * @param delimiter a delimiter
     * @param frameDelimiter a frame delimiter
     * @param columns the columns
     * @return a delimited format with optional framing
     */
    public static DelimitedFormat optionallyFramed(String id,
            String description,
            Character delimiter,
            Character frameDelimiter,
            List<Column> columns) {
        return new DelimitedFormat(id, description, delimiter, false, Framing.OPTIONAL, frameDelimiter, 0, columns);
    }

    /**
     * Returns a version of the format with the given offset.
     * @param offset an offset
     * @return a version of the format with the given offset
     */
    public DelimitedFormat withOffset(Integer offset) {
        return new DelimitedFormat(id,
                                   description,
                                   delimiter,
                                   endingDelimiter, framing,
                                   frameDelimiter,
                                   offset,
                                   new LinkedList<>(columns));
    }

    /**
     * Builds a version of the format with an ending delimiter.
     * @return a version of the format with an ending delimiter
     */
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

    /**
     * @return an identifier for the format
     */
    public String getId() {
        return id;
    }

    /**
     * @return a description of the format
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the delimiter
     */
    public Character getDelimiter() {
        return delimiter;
    }

    /**
     * @return true if the format has an ending delimiter; otherwise, false.
     */
    public Boolean hasEndingDelimiter() {
        return endingDelimiter;
    }

    /**
     * @return the framing configuration.
     */
    public Framing getFraming() {
        return framing;
    }

    /**
     * @return true if values can or are required to be framed; otherwise,
     * false.
     */
    @JsonIgnore
    public boolean isFramed() {
        return framing == Framing.OPTIONAL || framing == Framing.REQUIRED;
    }

    /**
     * @return true if framing is required; otherwise, false.
     */
    @JsonIgnore
    public boolean framingRequired() {
        return framing == Framing.REQUIRED;
    }

    /**
     * @return an Optional containing the frame delimiter if present;
     * otherwise, an empty Optional.
     */
    public Optional<Character> getFrameDelimiter() {
        if (frameDelimiter == null) {
            return Optional.empty();
        }

        return Optional.of(frameDelimiter);
    }

    /**
     * @return the offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @return the columns
     */
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

    /**
     * Parses a delimited string.
     * @param line a delimited string
     * @return a Result containing a record if parsing was successful;
     * otherwise, a Result containing parsing problems.
     */
    @Override
    public Result<Record> parse(String line) {
        return parser.parse(line);
    }

    /**
     * Serializes a map to a delimited string.
     * @param map a map
     * @return a Result containing a delimited string if serialization was
     * successful; otherwise, a Result containing serialization problems.
     */
    @Override
    public Result<String> serialize(Map<String, Object> map) {
        return serializer.serialize(map);
    }

    /**
     * Serializes a record to a delimited string.
     * @param record a record
     * @return a Result containing a delimited string if serialization was
     * successful; otherwise, a Result containing serialization problems.
     */
    @Override
    public Result<String> serialize(Record record) {
        return serializer.serialize(record);
    }
}
