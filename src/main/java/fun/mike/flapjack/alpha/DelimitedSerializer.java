package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fun.mike.record.alpha.Record;

public class DelimitedSerializer implements Serializable {
    private final DelimitedFormat format;

    public DelimitedSerializer(DelimitedFormat format) {
        this.format = format;
    }

    public Result<String> serialize(Map<String, Object> map) {
        return serialize(new Record(map));
    }

    public Result<String> serialize(Record record) {
        StringBuilder builder = new StringBuilder();
        List<Problem> problems = new LinkedList<>();

        List<Column> columns = format.getColumns();
        int i;
        int lengthMinusOne = columns.size() - 1;
        for (i = 0; i < lengthMinusOne; i++) {
            Column column = columns.get(i);
            ValueOrProblem<String> valueOrProblem = processColumn(column, record);

            if (valueOrProblem.hasProblem()) {
                problems.add(valueOrProblem.getProblem());
            } else {
                appendValue(valueOrProblem.getValue(), builder);
            }
            builder.append(format.getDelimiter());
        }

        Column lastColumn = columns.get(i);
        ValueOrProblem<String> lastValueOrProblem = processColumn(lastColumn, record);

        if (lastValueOrProblem.hasProblem()) {
            problems.add(lastValueOrProblem.getProblem());
        } else {
            appendValue(lastValueOrProblem.getValue(), builder);
        }

        String line = builder.toString();

        if (format.hasEndingDelimiter()) {
            line = line + format.getDelimiter();
        }

        if (problems.isEmpty()) {
            return Result.ok(line);
        }

        return Result.withProblems(line, problems);
    }

    private void appendValue(String value, StringBuilder builder) {
        if (format.isFramed()) {
            Character frameDelimiter = format.getFrameDelimiter().get();
            builder.append(frameDelimiter);
            builder.append(value);
            builder.append(frameDelimiter);
        } else {
            builder.append(value);
        }
    }

    private ValueOrProblem<String> processColumn(Column column, Record record) {
        String id = column.getId();
        String type = column.getType();
        Map<String, Object> props = column.getProps();
        return ValueSerializer.serializeValue(id, type, props, record);
    }
}
