package fun.mike.flapjack.alpha;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fun.mike.record.alpha.Record;

/**
 * An flat file format that can be used to parse or serialize records.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = FixedWidthFormat.class, name = "fixed-width"),
        @Type(value = DelimitedFormat.class, name = "delimited"),
})
public interface Format {
    /**
     * Parses a serialized string.
     *
     * @param line a serialized string
     * @return a ParseResult containing a record if parsing is successful;
     * otherwise, a ParseResult containing parsing problems.
     */
    ParseResult parse(String line);

    /**
     * Parses a serialized value, throwing a ParseException if parsing is
     * unsuccessful.
     *
     * @param line a serialized string
     * @return a record
     */
    Record parseAndThrow(String line);

    /**
     * Serializes a map.
     *
     * @param map a map
     * @return a SerializationResult containing a serialized string if
     * serialization is successful; otherwise, a SerializationResult containing
     * serialization problems
     */
    SerializationResult serialize(Map<String, Object> map);

    /**
     * Serializes a record.
     *
     * @param record a record
     * @return a SerializationResult containing a serialized string if
     * serialization is successful; otherwise, a SerializationResult containing
     * serialization problems
     */
    SerializationResult serialize(Record record);

    /**
     * Serializes a record, throwing a SerializationException if serialization
     * is unsuccessful.
     *
     * @param record a record
     * @return a serialized string
     */
    String serializeAndThrow(Record record);

    /**
     * Returns the identifier for the format.
     * @return the identifier for the format
     */
    String getId();

    /**
     * Returns the description for the format.
     * @return the description of the format
     */
    public String getDescription();

    void visit(FormatVisitor visitor);
}
