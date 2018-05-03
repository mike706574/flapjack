package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static fun.mike.map.alpha.Factory.mapOf;

/**
 * A field in a fixed-width format.
 */
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

    /**
     * Builds a field with the given identifier, length, and type.
     *
     * @param id     an identifier
     * @param length field length
     * @param type   field type
     * @return a field with the given identifier and type
     */
    public static Field with(String id, int length, String type) {
        return new Field(id, length, type, new HashMap<>());
    }

    /**
     * Builds a field with the given identifier, length, type, and properties.
     *
     * @param id     an identifier
     * @param length field length
     * @param type   field type
     * @param props  properties
     * @return a field with the given identifier, type, and properties
     */
    public static Field with(String id, int length, String type, Map<String, Object> props) {
        return new Field(id, length, type, props);
    }

    /**
     * Builds a nullable field of the given type.
     *
     * @param id     an identifier
     * @param length field length
     * @param type   field type
     * @return a nullable field of the given type
     */
    public static Field nullable(String id, int length, String type) {
        Map<String, Object> props = new HashMap<>();
        props.put("nullable", true);
        return Field.with(id, length, type, props);
    }

    /**
     * Builds a nullable string field.
     *
     * @param id     an identifier
     * @param length field length
     * @return a nullable string field
     */
    public static Field nullableString(String id, int length) {
        return string(id, length).nullable();
    }

    /**
     * Builds a string field.
     *
     * @param id     an identifier
     * @param length field length
     * @return a string field
     */
    public static Field string(String id, int length) {
        return new Field(id, length, "string", new HashMap<>());
    }

    /**
     * Builds a string field with the given properties.
     *
     * @param id     an identifier
     * @param length field length
     * @param props  properties
     * @return a string field with the given properties
     */
    public static Field string(String id, int length, Map<String, Object> props) {
        return new Field(id, length, "string", props);
    }

    /**
     * Builds a trimmed string field.
     *
     * @param id     an identifier
     * @param length field length
     * @return a trimmed string field
     */
    public static Field trimmedString(String id, int length) {
        return new Field(id, length, "trimmed-string", new HashMap<>());
    }

    /**
     * Builds a nullable trimmed string field.
     *
     * @param id     an identifier
     * @param length field length
     * @return a nullable trimmed string field
     */
    public static Field nullableTrimmedString(String id, int length) {
        return trimmedString(id, length).nullable();
    }

    /**
     * Builds a trimmed string field with the given properties.
     *
     * @param id     an identifier
     * @param length field length
     * @param props  properties
     * @return a trimmed string field with the given properties
     */
    public static Field trimmedString(String id, int length, Map<String, Object> props) {
        return new Field(id, length, "trimmed-string", props);
    }

    /**
     * Builds an integer field.
     *
     * @param id     an identifier
     * @param length field length
     * @return an integer field
     */
    public static Field integer(String id, int length) {
        return new Field(id, length, "integer", new HashMap<>());
    }

    /**
     * Builds a nullable integer field.
     *
     * @param id     an identifier
     * @param length field length
     * @return a nullable integer field
     */
    public static Field nullableInteger(String id, int length) {
        return integer(id, length).nullable();
    }

    /**
     * Builds an integer field with the given properties.
     *
     * @param id     an identifier
     * @param length field length
     * @param props  properties
     * @return an integer field with the given properties
     */
    public static Field integer(String id, int length, Map<String, Object> props) {
        return new Field(id, length, "integer", props);
    }

    /**
     * Builds an BigDecimal field.
     *
     * @param id     an identifier
     * @param length field length
     * @return a BigDecimal field
     */
    public static Field bigDecimal(String id, int length) {
        return new Field(id, length, "big-decimal", new HashMap<>());
    }

    /**
     * Builds a nullable BigDecimal field.
     *
     * @param id     an identifier
     * @param length field length
     * @return a nullable BigDecimal field
     */
    public static Field nullableBigDecimal(String id, int length) {
        return bigDecimal(id, length).nullable();
    }

    /**
     * Builds a BigDecimal field with the given properties.
     *
     * @param id     an identifier
     * @param length field length
     * @param props  properties
     * @return an BigDecimal field with the given properties
     */
    public static Field bigDecimal(String id, int length, Map<String, Object> props) {
        return new Field(id, length, "big-decimal", props);
    }

    /**
     * Builds a date field with the given format.
     *
     * @param id     an identifier
     * @param length field length
     * @param format a date format
     * @return a date field with the given format
     */
    public static Field date(String id, int length, String format) {
        return new Field(id, length, "date", mapOf("format", "yyyyMMdd"));
    }

    /**
     * Builds a nullable date field with the given format.
     *
     * @param id     an identifier
     * @param length field length
     * @param format a date format
     * @return a nullable date field with the given format
     */
    public static Field nullableDate(String id, int length, String format) {
        return date(id, length, format).nullable();
    }

    /**
     * Builds a filler field.
     *
     * @param id     an identifier
     * @param length field length
     * @return a filler field
     */
    public static Field filler(String id, int length) {
        return new Field(id, length, "filler", new HashMap<>());
    }

    /**
     * Returns a nullable version of a field.
     *
     * @return a nullable version of a field
     */
    public Field nullable() {
        return withProp("nullable", true);
    }

    private Field withProp(String key, Object value) {
        Map<String, Object> newProps = new HashMap<>();
        newProps.put(key, value);
        newProps.putAll(props);
        return new Field(id, length, type, newProps);
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
