package fun.mike.flapjack.beta;

import java.io.Serializable;
import java.util.Map;

import fun.mike.record.Record;

public class FixedWidthValueSerializer implements Serializable {
    public static ValueOrProblem<String> serializeValue(Field field, Record record) {
        String id = field.getId();
        int length = field.getLength();
        String type = field.getType();
        Map<String, Object> props = field.getProps();

        return ValueSerializer.serializeValue(id, type, props, record)
                .flatMap(line -> {
                    if (line.length() > length) {
                        Problem problem = new TruncationProblem(id, type, length, line);
                        return ValueOrProblem.problem(problem);
                    }
                    return ValueOrProblem.value(padRight(line, length));
                });
    }

    private static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
}
