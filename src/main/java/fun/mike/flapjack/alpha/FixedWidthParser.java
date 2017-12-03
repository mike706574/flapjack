package fun.mike.flapjack.alpha;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;

public class FixedWidthParser {
    private final FixedWidthFormat format;

    public FixedWidthParser(FixedWidthFormat format) {
        this.format = format;
    }

    private Record parseLine(Long index, String line) {
        Map<String, Object> data = new HashMap<String, Object>();
        Set<Problem> problems = new HashSet<Problem>();

        Optional<Integer> length = format.getLength();

        Integer lineLength = line.length();
        if (length.isPresent() && !length.get().equals(lineLength)) {
            Problem lengthMismatch = new LengthMismatchProblem(length.get(),
                    lineLength);
            problems.add(lengthMismatch);
            return Record.with(index, data, problems);
        }

        for (Field field : format.getFields()) {
            String fieldId = field.getId();
            Integer fieldStart = field.getStart();
            Integer fieldEnd = field.getEnd();

            if (fieldEnd > lineLength) {
                Problem outOfBounds = new OutOfBoundsProblem(fieldId,
                        fieldEnd,
                        lineLength);
                problems.add(outOfBounds);
            } else {
                String value = line.substring(fieldStart - 1,
                        fieldEnd);
                String fieldType = field.getType();
                Map<String, Object> props = field.getProps();
                ObjectOrProblem result = ValueParser.parse(fieldId, fieldType, props, value);

                if (result.hasProblem()) {
                    problems.add(result.getProblem());
                } else {
                    data.put(fieldId, result.getObject());
                }
            }
        }

        return Record.with(index, data, problems);
    }

    public Stream<Record> stream(Stream<String> lines) {
        return StreamUtils.zipWithIndex(lines)
                .map(item -> parseLine(item.getIndex(), item.getValue()));
    }

}
