package fun.mike.flapjack.alpha;

import java.util.Map;

public class ValueParser {
    public static ObjectOrError parse(String id, String type, Map<String, Object> props,
                                      String value) {
        switch (type) {
            case "string":
                return ObjectOrError.object(value);
            case "integer":
                return parseInt(id, type, value);
            case "trimmed-string":
                return ObjectOrError.object(value.trim());
        }

        return ObjectOrError.error(new NoSuchTypeError(id, type));
    }

    private static ObjectOrError parseInt(String id, String type, String value) {
        try {
            return ObjectOrError.object(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            return ObjectOrError.error(new TypeError(id, type, value));
        }
    }
}
