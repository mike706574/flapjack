package fun.mike.flapjack.alpha;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        } catch (IllegalArgumentException ex) {
            return ObjectOrProblem.problem(new GeneralProblem(ex.getMessage()));
        }
    }
}
