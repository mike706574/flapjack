package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static fun.mike.map.alpha.Factory.mapOf;

/**
 * A column in a delimited format.
 */
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

    /**
     * Builds a column with the given identifier and type.
     *
     * @param id   an identifier
     * @param type column type
     * @return a column with the given identifier and type
     */
    public static Column with(String id, String type) {
        return new Column(id, type, new HashMap<>());
    }

    /**
     * Builds a column with the given identifier, type, and properties.
     *
     * @param id    an identifier
     * @param type  a type
     * @param props properties
     * @return a column with the given identifier, type, and properties
     */
    public static Column with(String id, String type, Map<String, Object> props) {
        return new Column(id, type, props);
    }

    /**
     * Builds a string column.
     *
     * @param id an identifier
     * @return a string column
     */
    public static Column string(String id) {
        return new Column(id, "string", new HashMap<>());
    }

    /**
     * Builds a string column with the given properties.
     *
     * @param id    an identifier
     * @param props properties
     * @return a string column with the given properties
     */
    public static Column string(String id, Map<String, Object> props) {
        return new Column(id, "string", props);
    }

    /**
     * Builds a trimmed string column.
     *
     * @param id an identifier
     * @return a trimmed string column
     */
    public static Column trimmedString(String id) {
        return new Column(id, "trimmed-string", new HashMap<>());
    }

    /**
     * Builds a trimmed string column with the given properties.
     *
     * @param id    an identifier
     * @param props properties
     * @return a trimmed string column with the given properties
     */
    public static Column trimmedString(String id, Map<String, Object> props) {
        return new Column(id, "trimmed-string", props);
    }

    /**
     * Builds an integer column.
     *
     * @param id an identifier
     * @return an integer column
     */
    public static Column integer(String id) {
        return new Column(id, "integer", new HashMap<>());
    }

    /**
     * Builds an integer column with the given properties.
     *
     * @param id    an identifier
     * @param props properties
     * @return an integer with the given properties
     */
    public static Column integer(String id, Map<String, Object> props) {
        return new Column(id, "integer", props);
    }

    /**
     * Builds a BigDecimal column.
     *
     * @param id an identifier
     * @return a BigDecimal column
     */
    public static Column bigDecimal(String id) {
        return new Column(id, "big-decimal", new HashMap<>());
    }

    /**
     * Builds a BigDecimal column with the given properties.
     *
     * @param id    an identifier
     * @param props properties
     * @return a BigDecimal column
     */
    public static Column bigDecimal(String id, Map<String, Object> props) {
        return new Column(id, "big-decimal", props);
    }

    /**
     * Builds a date column with the given format.
     *
     * @param id     an identifier
     * @param format a date format
     * @return a date column with the given format
     */
    public static Column date(String id, String format) {
        return new Column(id, "date", mapOf("format", format));
    }

    /**
     * Builds a filler column.
     *
     * @param id an identifier
     * @return a filler column
     */
    public static Column filler(String id) {
        return new Column(id, "filler", new HashMap<>());
    }

    /**
     * Builds a nullable column of the given type.
     *
     * @param id   an identifier
     * @param type a column type
     * @return a nullable column of the given type
     */
    public static Column nullable(String id, String type) {
        Map<String, Object> props = new HashMap<>();
        props.put("nullable", true);
        return Column.with(id, type, props);
    }

    /**
     * Returns a nullable version of a column.
     *
     * @return a nullable version of a column
     */
    public Column nullable() {
        return withProp("nullable", true);
    }

    private Column withProp(String key, Object value) {
        Map<String, Object> newProps = new HashMap<>();
        newProps.put(key, value);
        newProps.putAll(props);
        return new Column(id, type, newProps);
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
