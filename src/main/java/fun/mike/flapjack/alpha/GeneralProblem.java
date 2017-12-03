package fun.mike.flapjack.alpha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="problem")
public class GeneralProblem implements Problem {
    private final String message;

    @JsonCreator
    public GeneralProblem(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String explain() {
        return message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GeneralProblem{" +
                "message='" + message + '\'' +
                '}';
    }
}
