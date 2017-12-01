package fun.mike.flapjack;

import java.util.*;
import java.util.stream.*;

import com.codepoetics.protonpack.*;

public class Parser {
    private final Format format;

    public Parser(Format format) {
        this.format = format;
    }

    private Record parseLine(Long index, String line) {
        Map<String, Object> data = new HashMap<String, Object>();
        Set<fun.mike.flapjack.Error> errors = new HashSet<fun.mike.flapjack.Error>();

        Optional<Integer> length = format.getLength();

        Integer lineLength = line.length();
        if (length.isPresent() && !length.get().equals(lineLength)) {
            fun.mike.flapjack.Error lengthMismatch = new LengthMismatchError(length.get(),
                    lineLength);
            errors.add(lengthMismatch);
            return Record.with(index, data, errors);
        }

        for (Field field : format.getFields()) {
            String fieldId = field.getId();
            Integer fieldStart = field.getStart();
            Integer fieldEnd = field.getEnd();

            if (fieldEnd > lineLength) {
                fun.mike.flapjack.Error outOfBounds = new OutOfBoundsError(fieldId,
                        fieldEnd,
                        lineLength);
                errors.add(outOfBounds);
            } else {
                String value = line.substring(fieldStart - 1,
                        fieldEnd);
                ObjectOrError result = parseType(field, value);

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

    private ObjectOrError parseType(Field field, String value) {
        switch (field.getType()) {
            case "string":
                return ObjectOrError.object(value);
            case "integer":
                return parseInt(field, value);
            case "trimmed-string":
                return ObjectOrError.object(value.trim());
        }

        return ObjectOrError.error(new NoSuchTypeError(field));
    }

    private ObjectOrError parseInt(Field field, String value) {
        try {
            return ObjectOrError.object(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            return ObjectOrError.error(new TypeError(field, value));
        }
    }
}
