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
                return Record.with(index, data, problems);
            }

            String value = line.substring(startIndex, endIndex);
            String type = field.getType();
            Map<String, Object> props = field.getProps();
            ObjectOrProblem result = ValueParser.parse(id, type, props, value);

            if (result.hasProblem()) {
                problems.add(result.getProblem());
            } else {
                data.put(id, result.getObject());
            }

            startIndex = endIndex;
        }

        return Record.with(index, data, problems);
    }

    public Stream<Record> stream(Stream<String> lines) {
        return StreamUtils.zipWithIndex(lines)
                .map(item -> parseLine(item.getIndex(), item.getValue()));
    }

}
