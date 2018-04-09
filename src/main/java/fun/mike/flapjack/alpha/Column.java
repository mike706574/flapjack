package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static fun.mike.map.alpha.Factory.mapOf;

public class Column implements Serializable {
    private final String id;
    private final String type;
    private final Map<String, Object> props;

    @JsonCreator
    public Column(@JsonProperty("id") String id,
            @JsonProperty("type") String type,
            @JsonProperty("props") Map<String, Object> props) {
        this.id = id;
        this.type = type;
        this.props = props;
    }

    // Factory methods
    public static Column with(String id, String type) {
        return new Column(id, type, new HashMap<>());
    }

    public static Column with(String id, String type, Map<String, Object> props) {
        return new Column(id, type, props);
    }

    public static Column string(String id) {
        return new Column(id, "string", new HashMap<>());
    }

    public static Column string(String id, Map<String, Object> props) {
        return new Column(id, "string", props);
    }

    public static Column trimmedString(String id) {
        return new Column(id, "trimmed-string", new HashMap<>());
    }

    public static Column trimmedString(String id, Map<String, Object> props) {
        return new Column(id, "trimmed-string", props);
    }

    public static Column integer(String id) {
        return new Column(id, "integer", new HashMap<>());
    }

    public static Column integer(String id, Map<String, Object> props) {
        return new Column(id, "integer", props);
    }

    public static Column bigDecimal(String id) {
        return new Column(id, "big-decimal", new HashMap<>());
    }

    public static Column bigDecimal(String id, Map<String, Object> props) {
        return new Column(id, "big-decimal", props);
    }

    public static Column date(String id, String format) {
        return new Column(id, "date", mapOf("format", format));
    }

    public static Column optionalDate(String id, String format) {
        return new Column(id, "date", mapOf("format", format,
                                            "optional", true));
    }

    public static Column nullable(String id, String type) {
        Map<String, Object> props = new HashMap<>();
        props.put("nullable", true);
        return Column.with(id, type, props);
    }

    // Getters
    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, Object> getProps() {
        return this.props;
    }

    @Override
    public String toString() {
        return "Column{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", props=" + props +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (id != null ? !id.equals(column.id) : column.id != null) return false;
        if (type != null ? !type.equals(column.type) : column.type != null) return false;
        return props != null ? props.equals(column.props) : column.props == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (props != null ? props.hashCode() : 0);
        return result;
    }
}
