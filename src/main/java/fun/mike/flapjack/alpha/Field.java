package fun.mike.flapjack.alpha;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
    private final String id;
    private final Integer start;
    private final Integer end;
    private final String type;
    private final Map<String, Object> props;

    @JsonCreator
    public Field(@JsonProperty("id") String id,
                 @JsonProperty("start") Integer start,
                 @JsonProperty("end") Integer end,
                 @JsonProperty("type") String type,
                 @JsonProperty("props") Map<String, Object> props) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
        this.props = props;
    }

    public static Field with(String id, Integer start, Integer end, String type) {
        return new Field(id, start, end, type, new HashMap<>());
    }

    public static Field with(String id, Integer start, Integer end, String type, Map<String, Object> props) {
        return new Field(id, start, end, type, props);
    }

    public String getId() {
        return this.id;
    }

    public Integer getStart() {
        return this.start;
    }

    public Integer getEnd() {
        return this.end;
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
                ", start=" + start +
                ", end=" + end +
                ", type='" + type + '\'' +
                ", props=" + props +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (id != null ? !id.equals(field.id) : field.id != null) return false;
        if (start != null ? !start.equals(field.start) : field.start != null) return false;
        if (end != null ? !end.equals(field.end) : field.end != null) return false;
        if (type != null ? !type.equals(field.type) : field.type != null) return false;
        return props != null ? props.equals(field.props) : field.props == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (props != null ? props.hashCode() : 0);
        return result;
    }
}
