package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A problem found during serialization or parsing.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = TypeProblem.class, name = "type"),
        @Type(value = OutOfBoundsProblem.class, name = "out-of-bounds"),
        @Type(value = NoSuchTypeProblem.class, name = "no-such-type"),
        @Type(value = StringEnumProblem.class, name = "string-enum"),
        @Type(value = FramingProblem.class, name = "framing"),
        @Type(value = TruncationProblem.class, name = "truncation"),
        @Type(value = FormatProblem.class, name = "format"),
        @Type(value = MissingValueProblem.class, name = "missing-value"),
        @Type(value = EmptyLineProblem.class, name = "empty-line")
})
public interface Problem {
    String explain();
}
