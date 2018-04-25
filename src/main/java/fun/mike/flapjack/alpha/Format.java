package fun.mike.flapjack.alpha;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fun.mike.record.alpha.Record;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = FixedWidthFormat.class, name = "fixed-width"),
        @Type(value = DelimitedFormat.class, name = "delimited"),
})
public interface Format {
    ParseResult parse(String line);

    Record parseAndThrow(String line);

    SerializationResult serialize(Map<String, Object> map);

    SerializationResult serialize(Record record);

    String serializeAndThrow(Record record);
}
