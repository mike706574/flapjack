package fun.mike.flapjack.alpha;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
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

    public static Field with(String id, int length, String type) {
        return new Field(id, length, type, new HashMap<>());
    }

    public static Field with(String id, int length, String type, Map<String, Object> props) {
        return new Field(id, length, type, props);
    }

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
}
