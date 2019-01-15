package fun.mike.flapjack.beta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ValueParser implements Serializable {
    public static ValueOrProblem<?> parse(String id,
                                          String type,
                                          Map<String, Object> props,
                                          String value) {
        switch (type) {
            case "string":
                return parseString(id, type, props, value);
            case "trimmed-string":
                return parseTrimmedString(id, type, props, value);
            case "integer":
                return parseInt(id, type, props, value);
            case "date":
                return parseDate(id, type, props, value);
            case "double":
                return parseDouble(id, type, props, value);
            case "big-decimal":
                return parseBigDecimal(id, type, props, value);
            case "string-enum":
                return parseStringEnum(id, type, props, value);
        }

        return ValueOrProblem.problem(new NoSuchTypeProblem(id, type));
    }

    // TODO: Move.
    private static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    private static String aOrAn(String noun) {
        return isVowel(noun.charAt(0)) ? "an" : "a";
    }

    private static boolean isVowel(char c) {
        return "aeiou".indexOf(c) != -1;
    }

    private static ValueOrProblem<String> parseStringType(String id,
                                                          String type,
                                                          Map<String, Object> props,
                                                          String value,
                                                          Function<String, ValueOrProblem<String>> parseValue) {
        if (isBlank(value)) {
            Object defaultValue = props.get("default");
            if (defaultValue != null) {
                if (defaultValue instanceof String) {
                    return ValueOrProblem.value((String) defaultValue);
                } else {
                    String message = String.format("Default value for field \"%s\" must be %s %s - got value \"%s\" of type \"%s\".",
                                                   id,
                                                   aOrAn(type),
                                                   type,
                                                   defaultValue,
                                                   defaultValue.getClass().getName());
                    return ValueOrProblem.problem(new FormatProblem(message));
                }
            }

            Object nullable = props.get("nullable");
            if (nullable != null) {
                if (nullable instanceof Boolean) {
                    if ((Boolean) nullable) {
                        return ValueOrProblem.value(null);
                    }
                } else {
                    String message = String.format("Property \"nullable\" for field \"%s\" must be a boolean - got value \"%s\" of type \"%s\".",
                                                   id,
                                                   nullable,
                                                   nullable.getClass());
                    return ValueOrProblem.problem(new FormatProblem(message));
                }
            }
        }

        return parseValue.apply(value);
    }

    private static <T> ValueOrProblem<T> parseType(String id,
                                                   String type,
                                                   Map<String, Object> props,
                                                   String value,
                                                   Class<T> typeClass,
                                                   Function<String, ValueOrProblem<T>> parseValue) {
        if (isBlank(value)) {
            Object defaultValue = props.get("default");
            if (defaultValue != null) {
                if (typeClass.isInstance(defaultValue)) {
                    return ValueOrProblem.value(typeClass.cast(defaultValue));
                } else {
                    String message = String.format("Default value for field \"%s\" must be %s %s - got value \"%s\" of type \"%s\".",
                                                   id,
                                                   aOrAn(type),
                                                   type,
                                                   defaultValue,
                                                   defaultValue.getClass().getName());
                    return ValueOrProblem.problem(new FormatProblem(message));
                }
            }

            Object nullable = props.get("nullable");
            if (nullable != null) {
                if (nullable instanceof Boolean) {
                    if ((Boolean) nullable) {
                        return ValueOrProblem.value(null);
                    }
                } else {
                    String message = String.format("Property \"nullable\" for field \"%s\" must be a boolean - got value \"%s\" of type \"%s\"",
                                                   id,
                                                   nullable,
                                                   nullable.getClass());
                    return ValueOrProblem.problem(new FormatProblem(message));
                }
            }
            return ValueOrProblem.problem(new MissingValueProblem(id, type));
        }

        return parseValue.apply(value);
    }

    private static ValueOrProblem<String> parseString(String id, String type, Map<String, Object> props, String value) {
        Function<String, ValueOrProblem<String>> parseValue = ValueOrProblem::value;

        return parseStringType(id, type, props, value, parseValue);
    }


    private static ValueOrProblem<String> parseTrimmedString(String id, String type, Map<String, Object> props, String value) {
        Function<String, ValueOrProblem<String>> parseValue = stringValue -> ValueOrProblem.value(stringValue.trim());

        return parseStringType(id, type, props, value, parseValue);
    }

    // TODO: Make this better.
    private static ValueOrProblem<Integer> parseInt(String id, String type, Map<String, Object> props, String value) {
        Function<String, ValueOrProblem<Integer>> parseValue = stringValue -> {
            try {
                return ValueOrProblem.value(Integer.parseInt(stringValue.trim().replace(",", "")));
            } catch (NumberFormatException ex) {
                return ValueOrProblem.problem(new TypeProblem(id, type, stringValue));
            }
        };

        return parseType(id, type, props, value, Integer.class, parseValue);
    }

    private static ValueOrProblem<BigDecimal> parseBigDecimal(String id, String type, Map<String, Object> props, String value) {
        Function<String, ValueOrProblem<BigDecimal>> parseValue = stringValue -> {
            try {
                return ValueOrProblem.value(new BigDecimal(stringValue.trim().replace(",", "")));
            } catch (NumberFormatException ex) {
                return ValueOrProblem.problem(new TypeProblem(id, type, stringValue));
            }
        };

        return parseType(id, type, props, value, BigDecimal.class, parseValue);
    }

    private static ValueOrProblem<Double> parseDouble(String id, String type, Map<String, Object> props, String value) {
        Function<String, ValueOrProblem<Double>> parseValue = stringValue -> {
            try {
                return ValueOrProblem.value(new Double(stringValue.trim().replace(",", "")));
            } catch (NumberFormatException ex) {
                return ValueOrProblem.problem(new TypeProblem(id, type, stringValue));
            }
        };

        return parseType(id, type, props, value, Double.class, parseValue);
    }

    private static ValueOrProblem<String> parseStringEnum(String id, String type, Map<String, Object> props, String value) {
        Object rawOptions = props.get("options");
        if (rawOptions == null) {
            String message = String.format("Property \"options\" is required for string enumeration field \"%s\".",
                                           id);
            return ValueOrProblem.problem(new FormatProblem(message));
        }

        try {
            @SuppressWarnings("unchecked")
            List<String> options = (List<String>) rawOptions;

            if (options.contains(value)) {
                return ValueOrProblem.value(value);
            }

            return ValueOrProblem.problem(new StringEnumProblem(id, value, options));
        } catch (ClassCastException ex) {
            String message = String.format("Property \"options\" for string enumeration field \"%s\" must be a list of strings - got value \"%s\" of type \"%s\".",
                                           id,
                                           rawOptions,
                                           rawOptions.getClass().getName());
            return ValueOrProblem.problem(new FormatProblem(message));
        }
    }

    private static ValueOrProblem<Date> parseDate(String id,
                                                  String type,
                                                  Map<String, Object> props,
                                                  String value) {
        Object rawFormat = props.get("format");

        if (rawFormat == null) {
            String message = String.format("Property \"format\" is required for date field \"%s\".", id);
            return ValueOrProblem.problem(new FormatProblem(message));
        }

        if (!(rawFormat instanceof String)) {
            String message = String.format("Property \"format\" for date field \"%s\" must be a string - got value \"%s\" of type \"%s\".",
                                           id,
                                           rawFormat,
                                           rawFormat.getClass().getName());
            return ValueOrProblem.problem(new FormatProblem(message));
        }

        String format = (String) rawFormat;

        if (isBlank(value)) {
            ValueOrProblem nullableOrProblem = getNullableFlag(id, props);

            if (nullableOrProblem.hasProblem()) {
                return ValueOrProblem.problem(nullableOrProblem.getProblem());
            }

            Boolean nullable = (Boolean) nullableOrProblem.getValue();

            if (nullable) {
                return ValueOrProblem.value(null);
            }

            return ValueOrProblem.problem(new TypeProblem(id, type, value));
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(value);
            return ValueOrProblem.value(date);
        } catch (ParseException ex) {
            return ValueOrProblem.problem(new TypeProblem(id, type, value));
        }
    }

    private static ValueOrProblem<Boolean> getNullableFlag(String id, Map<String, Object> props) {
        return getBooleanFlag("nullable", id, props);
    }

    private static ValueOrProblem<Boolean> getBooleanFlag(String key, String id, Map<String, Object> props) {
        if (props.containsKey(key)) {
            Object value = props.get(key);

            if (value instanceof Boolean) {
                return ValueOrProblem.value((Boolean) value);
            } else {
                String message = String.format("Property \"%s\" for field \"%s\" must be a boolean.", key, id);
                return ValueOrProblem.problem(new FormatProblem(message));
            }
        }

        return ValueOrProblem.value(Boolean.FALSE);
    }
}
