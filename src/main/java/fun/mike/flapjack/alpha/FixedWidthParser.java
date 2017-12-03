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
        Set<fun.mike.flapjack.alpha.Error> errors = new HashSet<fun.mike.flapjack.alpha.Error>();

        Optional<Integer> length = format.getLength();

        Integer lineLength = line.length();
        if (length.isPresent() && !length.get().equals(lineLength)) {
            fun.mike.flapjack.alpha.Error lengthMismatch = new LengthMismatchError(length.get(),
                    lineLength);
            errors.add(lengthMismatch);
            return Record.with(index, data, errors);
        }

        for (Field field : format.getFields()) {
            String fieldId = field.getId();
            Integer fieldStart = field.getStart();
            Integer fieldEnd = field.getEnd();

            if (fieldEnd > lineLength) {
                fun.mike.flapjack.alpha.Error outOfBounds = new OutOfBoundsError(fieldId,
                        fieldEnd,
                        lineLength);
                errors.add(outOfBounds);
            } else {
                String value = line.substring(fieldStart - 1,
                        fieldEnd);
                String fieldType = field.getType();
                Map<String, Object> props = field.getProps();
                ObjectOrError result = ValueParser.parse(fieldId, fieldType, props, value);

                if (result.isError()) {
                    errors.add(result.getError());
                } else {
                    data.put(fieldId, result.getObject());
                }
            }
        }

        return Record.with(index, data, errors);
    }

    public Stream<Record> stream(Stream<String> lines) {
        return StreamUtils.zipWithIndex(lines)
                .map(item -> parseLine(item.getIndex(), item.getValue()));
    }

}
