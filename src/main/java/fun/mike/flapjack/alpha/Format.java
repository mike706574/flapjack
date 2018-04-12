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
    Result<Record> parse(String line);

    Result<String> serialize(Map<String, Object> map);

    Result<String> serialize(Record record);
}
