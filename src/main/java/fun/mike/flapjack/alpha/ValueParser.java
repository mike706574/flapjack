package fun.mike.flapjack.alpha;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static fun.mike.map.alpha.Get.requiredString;

public class ValueParser {
    public static ValueOrProblem parse(String id,
                                       String type,
                                       Map<String, Object> props,
                                       String value) {
        switch (type) {
            case "string":
                return ValueOrProblem.value(value);
            case "trimmed-string":
                return ValueOrProblem.value(value.trim());
            case "integer":
                return parseInt(id, type, props, value);
            case "date":
                return parseDate(id, type, props, value);
            case "big-decimal":
                return parseBigDecimal(id, type, props, value);
            case "string-enum":
                return parseStringEnum(id, type, props, value);
        }

        return ValueOrProblem.problem(new NoSuchTypeProblem(id, type));
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    private static <T> ValueOrProblem parseSomething(String id,
                                                     String type,
                                                     Map<String, Object> props,
                                                     String value,
                                                     Function<String, ValueOrProblem> parseValue) {

        if (isBlank(value)) {
            if (props.containsKey("default")) {
                try {
                    @SuppressWarnings("unchecked")
                    T defaultValue = (T) props.get("default");
                    return ValueOrProblem.value(defaultValue);
                } catch (ClassCastException ex) {
                    String message = String.format("Expected default value for field \"%s\" to be an %s.", id, type);
                    return ValueOrProblem.problem(new FormatProblem(message));
                }
            }
            return ValueOrProblem.problem(new TypeProblem(id, type, value));
        }

        return parseValue.apply(value);
    }

    private static ValueOrProblem parseInt(String id, String type, Map<String, Object> props, String value) {
        Function<String, ValueOrProblem> parseValue = stringValue -> {
            try {
                return ValueOrProblem.value(Integer.parseInt(stringValue.trim()));
            } catch (NumberFormatException ex) {
                return ValueOrProblem.problem(new TypeProblem(id, type, stringValue));
            }
        };

        return parseSomething(id, type, props, value, parseValue);
    }

    private static ValueOrProblem parseBigDecimal(String id, String type, Map<String, Object> props, String value) {
        Function<String, ValueOrProblem> parseValue = stringValue -> {
            try {
                return ValueOrProblem.value(new BigDecimal(stringValue.trim()));
            } catch (NumberFormatException ex) {
                return ValueOrProblem.problem(new TypeProblem(id, type, stringValue));
            }
        };

        return parseSomething(id, type, props, value, parseValue);
    }

    private static ValueOrProblem parseStringEnum(String id, String type, Map<String, Object> props, String value) {
        try {
            @SuppressWarnings("unchecked")
            List<String> options = (List<String>) props.get("options");

            if (options.contains(value)) {
                return ValueOrProblem.value(value);
            }

            return ValueOrProblem.problem(new StringEnumProblem(id, value, options));
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("Required property \"options\" must be a list of strings.", ex);

        }
    }

    private static ValueOrProblem parseDate(String id,
                                            String type,
                                            Map<String, Object> props,
                                            String value) {
        try {
            ValueOrProblem optionalOrProblem = getOptionalFlag(id, props);

            if (optionalOrProblem.hasProblem()) {
                return optionalOrProblem;
            }

            Boolean optional = (Boolean) optionalOrProblem.getValue();

            if (isBlank(value)) {
                if (optional) {
                    return ValueOrProblem.value(null);
                }

                return ValueOrProblem.problem(new TypeProblem(id, type, value));
            }

            String format = requiredString(props, "format");
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date date = formatter.parse(value);
            return ValueOrProblem.value(date);
        } catch (ParseException ex) {
            return ValueOrProblem.problem(new TypeProblem(id, type, value));
        }
    }

    private static ValueOrProblem getOptionalFlag(String id, Map<String, Object> props) {
        if (props.containsKey("optional")) {
            try {
                return ValueOrProblem.value((Boolean) props.get("optional"));
            } catch (ClassCastException ex) {
                String message = String.format("Expected optional flag for field \"%s\" to be a boolean.", id);
                return ValueOrProblem.problem(new FormatProblem(message));
            }
        }

        return ValueOrProblem.value(new Boolean(false));
    }
}
