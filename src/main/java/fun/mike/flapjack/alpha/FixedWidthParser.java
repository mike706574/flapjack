package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import fun.mike.record.alpha.Record;

public class FixedWidthParser implements Serializable {
    private final FixedWidthFormat format;

    public FixedWidthParser(FixedWidthFormat format) {
        this.format = format;
    }

    public Result parse(String line) {
        Record record = new Record();
        List<Problem> problems = new LinkedList<>();

        int lineLength = line.length();

        int startIndex = 0;

        for (Field field : format.getFields()) {
            String id = field.getId();
            int length = field.getLength();
            int endIndex = startIndex + length;

            if (endIndex > lineLength) {
                Problem outOfBounds = new OutOfBoundsProblem(id,
                        endIndex,
                        lineLength);
                problems.add(outOfBounds);
                return Result.withProblems(record, problems);
            }

            String value = line.substring(startIndex, endIndex);
            String type = field.getType();
            Map<String, Object> props = field.getProps();

            if(!type.equals("filler")) {
                ValueOrProblem result = ValueParser.parse(id, type, props, value);

                if (result.hasProblem()) {
                    problems.add(result.getProblem());
                } else {
                    record.put(id, result.getValue());
                }
            }

            startIndex = endIndex;
        }

        return Result.withProblems(record, problems);
    }

    public Stream<Result> stream(Stream<String> lines) {
        return StreamUtils.zipWithIndex(lines)
                .map(item -> {
                    Result result = parse(item.getValue());
                    result.getRecord().put("lineIndex", item.getIndex());
                    return result;
                });
    }
}
