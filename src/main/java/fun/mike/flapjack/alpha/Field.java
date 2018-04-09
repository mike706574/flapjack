package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static fun.mike.map.alpha.Factory.mapOf;

public class Field implements Serializable {
    private final String id;
    private final int length;
    private final String type;
    private final Map<String, Object> props;

    @JsonCreator
    public Field(@JsonProperty("id") String id,
            @JsonProperty("length") int length,
            @JsonProperty("type") String type,
            @JsonProperty("props") Map<String, Object> props) {
        this.id = id;
        this.length = length;
        this.type = type;
        this.props = props;
    }

    // Factory methods
    public static Field with(String id, int length, String type) {
        return new Field(id, length, type, new HashMap<>());
    }

    public static Field with(String id, int length, String type, Map<String, Object> props) {
        return new Field(id, length, type, props);
    }

    public static Field nullable(String id, int length, String type) {
        Map<String, Object> props = new HashMap<>();
        props.put("nullable", true);
        return Field.with(id, length, type, props);
    }

    public static Field string(String id, int length) {
        return new Field(id, length, "string", new HashMap<>());
    }

    public static Field string(String id, int length, Map<String, Object> props) {
        return new Field(id, length, "string", props);
    }

    public static Field trimmedString(String id, int length) {
        return new Field(id, length, "trimmed-string", new HashMap<>());
    }

    public static Field trimmedString(String id, int length, Map<String, Object> props) {
        return new Field(id, length, "trimmed-string", props);
    }

    public static Field integer(String id, int length) {
        return new Field(id, length, "integer", new HashMap<>());
    }

    public static Field integer(String id, int length, Map<String, Object> props) {
        return new Field(id, length, "integer", props);
    }

    public static Field bigDecimal(String id, int length) {
        return new Field(id, length, "big-decimal", new HashMap<>());
    }

    public static Field bigDecimal(String id, int length, Map<String, Object> props) {
        return new Field(id, length, "big-decimal", props);
    }

    public static Field date(String id, int length, String format) {
        return new Field(id, length, "date", mapOf("format", "yyyyMMdd"));
    }

    public static Field optionalDate(String id, int length, String format) {
        return new Field(id, length, "date", mapOf("format", format,
                                                   "optional", true));
    }

    // Getters
    public String getId() {
        return this.id;
    }

    public int getLength() {
        return this.length;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, Object> getProps() {
        return this.props;
    }

    @Override
    public String toString() {
        return "Field{" +
                "id='" + id + '\'' +
                ", length=" + length +
                ", type='" + type + '\'' +
                ", props=" + props +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (length != field.length) return false;
        if (id != null ? !id.equals(field.id) : field.id != null) return false;
        if (type != null ? !type.equals(field.type) : field.type != null) return false;
        return props != null ? props.equals(field.props) : field.props == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + length;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (props != null ? props.hashCode() : 0);
        return result;
    }
}
