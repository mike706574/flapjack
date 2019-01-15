package fun.mike.flapjack.beta;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fun.mike.record.Record;

public class FixedWidthSerializer implements Serializable {
    private final FixedWidthFormat format;

    public FixedWidthSerializer(FixedWidthFormat format) {
        this.format = format;
    }

    public SerializationResult serialize(Map<String, Object> map) {
        return serialize(new Record(map));
    }

    public SerializationResult serialize(Record record) {
        StringBuilder builder = new StringBuilder();
        List<Problem> problems = new LinkedList<>();

        for (Field field : format.getFields()) {
            ValueOrProblem<String> valueOrProblem = FixedWidthValueSerializer.serializeValue(field, record);

            if (valueOrProblem.hasProblem()) {
                problems.add(valueOrProblem.getProblem());
                builder.append(blankField(field));
            } else {
                builder.append(valueOrProblem.getValue());
            }
        }

        String line = builder.toString();

        if (problems.isEmpty()) {
            return SerializationResult.ok(line, record);
        }

        return SerializationResult.withProblems(line, record, problems);
    }

    private String blankField(Field field) {
        return String.format("%1$-" + field.getLength() + "s", "");
    }
}
