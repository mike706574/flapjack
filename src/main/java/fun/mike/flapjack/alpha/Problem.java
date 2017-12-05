package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @Type(value = TypeProblem.class, name = "type"),
  @Type(value = OutOfBoundsProblem.class, name = "out-of-bounds"),
  @Type(value = NoSuchTypeProblem.class, name = "no-such-type"),
  @Type(value = StringEnumProblem.class, name = "string-enum"),
  @Type(value = FramingProblem.class, name = "framing"),
})
public interface Problem {
    public String explain();
}
