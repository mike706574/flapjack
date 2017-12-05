package fun.mike.flapjack.alpha;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FixedWidthFormat implements Format {
    private final String id;
    private final String description;
    private final List<Field> fields;

    @JsonCreator
    public FixedWidthFormat(@JsonProperty("id") String id,
                            @JsonProperty("description") String description,
                            @JsonProperty("fields") List<Field> fields) {
        this.id = id;
        this.description = description;
        this.fields = Collections.unmodifiableList(fields);
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Field> getFields() {
        return this.fields;
    }
}
