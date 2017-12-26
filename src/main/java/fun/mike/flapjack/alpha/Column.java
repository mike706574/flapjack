package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


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

    public static Column with(String id, String type, Map<String, Object> props) {
        return new Column(id, type, props);
    }

    public static Column with(String id, String type) {

        return new Column(id, type, new HashMap<>());
    }

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
