package fun.mike.flapjack.alpha;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.function.Function;

import static fun.mike.map.alpha.Get.requiredString;

public class ValueParser {
    public static ObjectOrProblem parse(String id, String type, Map<String, Object> props,
                                        String value) {
        switch (type) {
            case "string":
                return ObjectOrProblem.object(value);
            case "trimmed-string":
                return ObjectOrProblem.object(value.trim());
            case "integer":
                return parseInt(id, type, props, value);
            case "date":
                return parseDate(id, type, props, value);
            case "big-decimal":
                return parseBigDecimal(id, type, props, value);
            case "string-enum":
                return parseStringEnum(id, type, props, value);
        }

        return ObjectOrProblem.problem(new NoSuchTypeProblem(id, type));
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    private static <T> ObjectOrProblem parseSomething(String id,
                                                      String type,
                                                      Map<String, Object> props,
                                                      String value,
                                                      Function<String, ObjectOrProblem> parseValue) {

        if(isBlank(value)) {
            if(props.containsKey("default")) {
                try {
                    @SuppressWarnings("unchecked")
                    T defaultValue = (T)props.get("default");
                    return ObjectOrProblem.object(defaultValue);
                }
                catch(ClassCastException ex) {
                    String message = String.format("Expected default value for field \"%s\" to be an %s.", id, type);
                    return ObjectOrProblem.problem(new FormatProblem(message));
                }
            }
            return ObjectOrProblem.problem(new TypeProblem(id, type, value));
        }

        return parseValue.apply(value);
    }

    private static ObjectOrProblem parseInt(String id, String type, Map<String, Object> props, String value) {
        Function<String, ObjectOrProblem> parseValue = stringValue -> {
            try {
                return ObjectOrProblem.object(Integer.parseInt(stringValue.trim()));
            }
            catch (NumberFormatException ex) {
                return ObjectOrProblem.problem(new TypeProblem(id, type, stringValue));
            }
        };

        return parseSomething(id, type, props, value, parseValue);
    }

    private static ObjectOrProblem parseBigDecimal(String id, String type, Map<String, Object> props, String value) {
        Function<String, ObjectOrProblem> parseValue = stringValue -> {
            try {
                return ObjectOrProblem.object(new BigDecimal(stringValue.trim()));
            }
            catch (NumberFormatException ex) {
                return ObjectOrProblem.problem(new TypeProblem(id, type, stringValue));
            }
        };

        return parseSomething(id, type, props, value, parseValue);
    }

    private static ObjectOrProblem parseStringEnum(String id, String type, Map<String, Object> props, String value) {
        try {
            @SuppressWarnings("unchecked")
            List<String> options = (List<String>) props.get("options");

            if (options.contains(value)) {
                return ObjectOrProblem.object(value);
            }

            return ObjectOrProblem.problem(new StringEnumProblem(id, value, options));
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("Required property \"options\" must be a list of strings.", ex);

        }
    }

    private static ObjectOrProblem parseDate(String id,
                                             String type,
                                             Map<String, Object> props,
                                             String value) {
        try {
            ObjectOrProblem optionalOrProblem = getOptionalFlag(id, props);

            if(optionalOrProblem.hasProblem()) {
                return optionalOrProblem;
            }

            Boolean optional = (Boolean)optionalOrProblem.getObject();

            if(isBlank(value)) {
                if(optional) {
                    return ObjectOrProblem.object(null);
                }

                return ObjectOrProblem.problem(new TypeProblem(id, type, value));
            }

            String format = requiredString(props, "format");
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date date = formatter.parse(value);
            return ObjectOrProblem.object(date);
        } catch (ParseException ex) {
            return ObjectOrProblem.problem(new TypeProblem(id, type, value));
        }
    }

    private static ObjectOrProblem getOptionalFlag(String id, Map<String, Object> props) {
        if(props.containsKey("optional")) {
            try {
                return ObjectOrProblem.object((Boolean)props.get("optional"));
            }
            catch(ClassCastException ex) {
                String message = String.format("Expected optional flag for field \"%s\" to be a boolean.", id);
                return ObjectOrProblem.problem(new FormatProblem(message));
            }
        }

        return ObjectOrProblem.object(new Boolean(false));
    }
}
