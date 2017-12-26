package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FixedWidthFormat implements Format, Serializable {
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

    @Override
    public String toString() {
        return "FixedWidthFormat{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", fields=" + fields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedWidthFormat that = (FixedWidthFormat) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return fields != null ? fields.equals(that.fields) : that.fields == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        return result;
    }
}
