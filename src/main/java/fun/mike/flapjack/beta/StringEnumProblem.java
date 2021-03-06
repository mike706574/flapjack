package fun.mike.flapjack.beta;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Problem used when the value of a string enumeration field does not match one
 * of the valid options.
 */
public class StringEnumProblem implements Problem {
    private final String id;
    private final String value;
    private final List<String> options;

    @JsonCreator
    public StringEnumProblem(@JsonProperty("id") String id,
                             @JsonProperty("value") String value,
                             @JsonProperty("options") List<String> options) {
        this.id = id;
        this.value = value;
        this.options = options;
    }

    /**
     * @return the id of the field
     */
    public String getId() {
        return id;
    }

    /**
     * @return the value of the field
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the valid options
     */
    public List<String> getOptions() {
        return options;
    }

    public String explain() {
        Integer count = options.size();
        String optionsStr = options.stream()
                .map(option -> String.format("\"%s\"", option))
                .collect(Collectors.joining(", "));
        return String.format("Value \"%s\" for field \"%s\" must be one of the following %d string options: %s",
                             value,
                             id,
                             count,
                             optionsStr);
    }

    @Override
    public String toString() {
        return "StringEnumProblem{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", options=" + options +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringEnumProblem that = (StringEnumProblem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return options != null ? options.equals(that.options) : that.options == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }
}
