package fun.mike.flapjack.alpha;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;

public class DelimitedParser {
    private final DelimitedFormat format;
    private final BiFunction<Long, String, Record> parseLine;

    public DelimitedParser(DelimitedFormat format) {
        this.format = format;

        this.parseLine = format.isFramed() ? this::parseFramedLine : this::parseUnframedLine;
    }

    public Stream<Record> stream(Stream<String> lines) {
        return StreamUtils.zipWithIndex(lines)
                .map(item -> parseLine.apply(item.getIndex(), item.getValue()));
    }

    public Record parseUnframedLine(Long index, String line) {
        Map<String, Object> data = new HashMap<String, Object>();
        Set<Problem> problems = new HashSet<Problem>();

        String value = "";
        StringBuffer cell = new StringBuffer();
        boolean inside = false;

        char delimiter = format.getDelimiter();

        int columnIndex = 0;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (inside) {
                if (ch == delimiter) {
                    inside = false;
                    setColumn(data, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else {
                    cell.append(ch);
                }
            } else {
                inside = true;
                cell.append(ch);
            }
        }

        setColumn(data, problems, columnIndex, cell.toString());
        return Record.with(index, data, problems);
    }

    public Record parseFramedLine(Long index, String line) {
        Map<String, Object> data = new HashMap<String, Object>();
        Set<Problem> problems = new HashSet<Problem>();

        String value = "";
        StringBuffer cell = new StringBuffer();
        boolean inside = false;
        boolean quoted = false;
        boolean afterEndQuote = false;

        char delimiter = format.getDelimiter();

        char frameDelimiter = format.getFrameDelimiter().get().charAt(0);

        int columnIndex = 0;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (inside) {
                if (quoted && ch == frameDelimiter) {
                    inside = false;
                    quoted = false;
                    afterEndQuote = true;
                    setColumn(data, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else if (ch == delimiter) {
                    inside = false;
                    quoted = false;
                    setColumn(data, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else {
                    cell.append(ch);
                }
            } else if (afterEndQuote) {
                if (ch == delimiter) {
                    afterEndQuote = false;
                } else {
                    problems.add(new FramingProblem(columnIndex, i));
                    break;
                }
            } else {
                inside = true;
                if (ch == frameDelimiter) {
                    quoted = true;
                } else {
                    problems.add(new FramingProblem(columnIndex, i));
                    break;
                }
            }
        }

        return Record.with(index, data, problems);
    }

    private void setColumn(Map<String, Object> data, Set<Problem> problems, int index, String value) {
        boolean indexInRange = index < format.getColumns().size();
        if (indexInRange) {
            Column column = format.getColumns().get(index);

            String columnId = column.getId();
            String columnType = column.getType();
            Map<String, Object> props = column.getProps();
            ObjectOrProblem result = ValueParser.parse(columnId, columnType, props, value);

            if (result.hasProblem()) {
                problems.add(result.getProblem());
            } else {
                data.put(columnId, result.getObject());
            }
        } else {
            throw new RuntimeException("TODO");
        }
    }
}