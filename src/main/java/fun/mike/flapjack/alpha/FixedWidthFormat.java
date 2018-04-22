package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fun.mike.record.alpha.Record;

/**
 * A fixed-width format used to parse and serialize fixed-width records.
 */
public class FixedWidthFormat implements Format, Serializable {
    private final String id;
    private final String description;
    private final List<Field> fields;

    private final FixedWidthParser parser;
    private final FixedWidthSerializer serializer;

    /**
     * Builds a fixed-width format
     * @param id an identifier for the format
     * @param description a description of the format
     * @param fields the fields
     */
    @JsonCreator
    public FixedWidthFormat(@JsonProperty("id") String id,
            @JsonProperty("description") String description,
            @JsonProperty("fields") List<Field> fields) {
        this.id = id;
        this.description = description;
        this.fields = Collections.unmodifiableList(fields);

        this.parser = new FixedWidthParser(this);
        this.serializer = new FixedWidthSerializer(this);
    }

    /**
     * @return an identifier for the format
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return a description of the format
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @return the fields
     */
    public List<Field> getFields() {
        return this.fields;
    }

    @Override
    public String toString() {
        return "FixedWidthFormat{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", fields=" + fields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedWidthFormat that = (FixedWidthFormat) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return fields != null ? fields.equals(that.fields) : that.fields == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        return result;
    }

    /**
     * Parses a fixed-width string.
     * @param line a fixed-width string
     * @return a Result containing a record if parsing was successful;
     * otherwise, a Result containing parsing problems.
     */
    @Override
    public Result<Record> parse(String line) {
        return parser.parse(line);
    }

    /**
     * Serializes a map to a fixed-width string.
     * @param map a map
     * @return a Result containing a fixed-width string if serialization was
     * successful; otherwise, a Result containing serialization problems.
     */
    @Override
    public Result<String> serialize(Map<String, Object> map) {
        return serializer.serialize(map);
    }

    /**
     * Serializes a record to a fixed-width string.
     * @param record a record
     * @return a Result containing a fixed-width string if serialization was
     * successful; otherwise, a Result containing serialization problems.
     */
    @Override
    public Result<String> serialize(Record record) {
        return serializer.serialize(record);
    }
}
