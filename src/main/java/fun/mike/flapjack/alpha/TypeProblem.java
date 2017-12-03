package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="problem")
public class TypeProblem implements Problem {
    private final String id;
    private final String type;
    private final String value;

    @JsonCreator
    public TypeProblem(@JsonProperty("id") String id,
                       @JsonProperty("type") String type,
                       @JsonProperty("value") String value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public String explain() {
        return String.format("Expected field \"%s\" with value \"%s\" to be a \"%s\".",
                id,
                value,
                type);
    }

    @Override
    public String toString() {
        return "TypeProblem{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
