package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fun.mike.record.alpha.Record;

public class FixedWidthSerializer implements Serializable {
    private final FixedWidthFormat format;

    public FixedWidthSerializer(FixedWidthFormat format) {
        this.format = format;
    }

    public Result<String> serialize(Map<String, Object> map) {
        return serialize(new Record(map));
    }

    public Result<String> serialize(Record record) {
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
            return Result.ok(line);
        }

        return Result.withProblems(line, problems);
    }

    private String blankField(Field field) {
        return String.format("%1$-" + field.getLength() + "s", "");
    }
}
