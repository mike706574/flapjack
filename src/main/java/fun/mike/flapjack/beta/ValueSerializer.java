package fun.mike.flapjack.beta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import fun.mike.record.Record;
import fun.mike.record.TypeMismatchException;

public class ValueSerializer implements Serializable {
    private static final Map<String, ValueSerializationFunction> typeSerializers;

    static {
        typeSerializers = new HashMap<>();
        typeSerializers.put("string", ValueSerializer::serializeString);
        typeSerializers.put("trimmed-string", ValueSerializer::serializeString);
        typeSerializers.put("integer", ValueSerializer::serializeInteger);
        typeSerializers.put("date", ValueSerializer::serializeDate);
        typeSerializers.put("local-date", ValueSerializer::serializeLocalDate);
        typeSerializers.put("local-date-time", ValueSerializer::serializeLocalDateTime);
        typeSerializers.put("double", ValueSerializer::serializeDouble);
        typeSerializers.put("big-decimal", ValueSerializer::serializeBigDecimal);
        typeSerializers.put("string-enum", ValueSerializer::serializeString);
    }

    public static ValueOrProblem<String> serializeValue(String id, String type, Map<String, Object> props, Record record) {
        ValueSerializationFunction serialize = typeSerializers.get(type);

        if (serialize == null) {
            return ValueOrProblem.problem(new NoSuchTypeProblem(id, type));
        }

        return serializeType(id, type, props, record, serialize);
    }

    public static ValueOrProblem<String> serializeType(String id,
                                                       String type,
                                                       Map<String, Object> props,
                                                       Record record,
                                                       ValueSerializationFunction serialize) {
        if (!record.containsKey(id) || record.get(id) == null) {
            if (props.containsKey("nullable")) {
                try {
                    boolean nullable = (boolean) props.get("nullable");

                    if (nullable) {
                        return ValueOrProblem.value("");
                    }
                } catch (ClassCastException ex) {
                    String message = "Expected nullable property to be a boolean.";
                    return ValueOrProblem.problem(new FormatProblem(message));
                }
            }
            return ValueOrProblem.problem(new MissingValueProblem(id, type));
        }

        try {
            return serialize.serialize(id, props, record);
        } catch (TypeMismatchException ex) {
            String value = record.get(id).toString();
            Problem problem = new TypeProblem(id, type, value);
            return ValueOrProblem.problem(problem);
        }
    }

    public static ValueOrProblem<String> serializeInteger(String id,
                                                          Map<String, Object> props,
                                                          Record record) {
        Integer value = record.getInteger(id);
        return ValueOrProblem.value(Integer.toString(value));
    }

    public static ValueOrProblem<String> serializeDate(String id,
                                                       Map<String, Object> props,
                                                       Record record) {
        Date value = record.getDate(id);

        String format = getRequiredString(props, "format");
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String serializedValue = formatter.format(value);
        return ValueOrProblem.value(serializedValue);
    }

    public static ValueOrProblem<String> serializeLocalDate(String id,
                                                            Map<String, Object> props,
                                                            Record record) {
        LocalDate value = record.getLocalDate(id);
        return serializeTemporalAccessor(value, props);
    }

    public static ValueOrProblem<String> serializeLocalDateTime(String id,
                                                                Map<String, Object> props,
                                                                Record record) {
        LocalDateTime value = record.getLocalDateTime(id);
        return serializeTemporalAccessor(value, props);
    }

    private static ValueOrProblem<String> serializeTemporalAccessor(TemporalAccessor value,
                                                                    Map<String, Object> props) {
        String format = getRequiredString(props, "format");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String serializedValue = formatter.format(value);
        return ValueOrProblem.value(serializedValue);
    }

    public static ValueOrProblem<String> serializeBigDecimal(String id,
                                                             Map<String, Object> props,
                                                             Record record) {
        BigDecimal value = record.getBigDecimal(id);
        DecimalFormat formatter = new DecimalFormat("#.0000");
        String serializedValue = formatter.format(value);
        return ValueOrProblem.value(serializedValue);
    }

    public static ValueOrProblem<String> serializeDouble(String id,
                                                         Map<String, Object> props,
                                                         Record record) {
        Double value = record.getDouble(id);
        DecimalFormat formatter = new DecimalFormat("#.0000");
        String serializedValue = formatter.format(value);
        return ValueOrProblem.value(serializedValue);
    }

    public static ValueOrProblem<String> serializeString(String id,
                                                         Map<String, Object> props,
                                                         Record record) {
        String value = record.getString(id);
        return ValueOrProblem.value(value);
    }

    private static String getRequiredString(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) {
            String message = String.format("Missing required string value for key \"%s\".",
                                           key);
            throw new NoSuchElementException(message);
        }

        Object value = map.get(key);

        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return (String) value;
        }

        String message = String.format("Value \"%s\" of class \"%s\" for key \"%s\" must be a string.",
                                       value,
                                       value.getClass().getName(),
                                       key);
        throw new IllegalArgumentException(message);
    }
}
