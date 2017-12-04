package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @Type(value = FixedWidthFormat.class, name = "fixed-width"),
  @Type(value = DelimitedFormat.class, name = "delimited"),
})

public interface Format {}
