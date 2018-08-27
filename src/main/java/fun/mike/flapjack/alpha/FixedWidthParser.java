package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import fun.mike.record.alpha.Record;

public class FixedWidthParser implements Parser, Serializable {
    private final FixedWidthFormat format;

    public FixedWidthParser(FixedWidthFormat format) {
        this.format = format;
    }

    public ParseResult parse(String line) {
        if(line.equals("")) {
            return ParseResult.withProblem(Record.empty(), line, new EmptyLineProblem());
        }

        Record record = new Record();

        record.setMetadataProperty("line", line);

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
                return ParseResult.withProblems(record, line, problems);
            }

            String value = line.substring(startIndex, endIndex);
            String type = field.getType();
            Map<String, Object> props = field.getProps();

            if (!type.equals("filler")) {
                ValueOrProblem result = ValueParser.parse(id, type, props, value);

                if (result.hasProblem()) {
                    problems.add(result.getProblem());
                } else {
                    record.put(id, result.getValue());
                }
            }

            startIndex = endIndex;
        }

        return ParseResult.withProblems(record, line, problems);
    }

    /**
     * @deprecated Use Pipeline API
     */
    @Deprecated
    public Stream<ParseResult> stream(Stream<String> lines) {
        return StreamUtils.zipWithIndex(lines)
                .map(item -> {
                    ParseResult result = parse(item.getValue());
                    result.getValue().put("lineIndex", item.getIndex());
                    return result;
                });
    }
}
