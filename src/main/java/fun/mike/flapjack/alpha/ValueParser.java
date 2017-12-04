package fun.mike.flapjack.alpha;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static fun.mike.map.alpha.Get.requiredString;

public class ValueParser {
    public static ObjectOrProblem parse(String id, String type, Map<String, Object> props,
                                        String value) {
        switch (type) {
            case "string":
                return ObjectOrProblem.object(value);
            case "integer":
                return parseInt(id, type, value);
            case "trimmed-string":
                return ObjectOrProblem.object(value.trim());
            case "formatted-date":
                return parseAndFormatDate(id, type, props, value);
            case "big-decimal":
                return parseBigDecimal(id, type, value);
            case "string-enum":
                return parseStringEnum(id, type, props, value);
        }

        return ObjectOrProblem.problem(new NoSuchTypeProblem(id, type));
    }

    private static ObjectOrProblem parseInt(String id, String type, String value) {
        try {
            return ObjectOrProblem.object(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            return ObjectOrProblem.problem(new TypeProblem(id, type, value));
        }
    }

    private static ObjectOrProblem parseBigDecimal(String id, String type, String value) {
        try {
            return ObjectOrProblem.object(new BigDecimal(value));
        } catch (NumberFormatException ex) {
            return ObjectOrProblem.problem(new TypeProblem(id, type, value));
        }
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


    private static ObjectOrProblem parseAndFormatDate(String id,
                                                      String type,
                                                      Map<String, Object> props,
                                                      String value) {
        try {
            String format = requiredString(props, "format");
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return ObjectOrProblem.object(formatter.parse(value));
        } catch (ParseException ex) {
            return ObjectOrProblem.problem(new TypeProblem(id, type, value));
        }
    }
}
