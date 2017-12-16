package fun.mike.flapjack.alpha;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import fun.mike.record.alpha.Record;

public class DelimitedParser {
    private final DelimitedFormat format;
    private final Function<String, Result> parseLine;

    public DelimitedParser(DelimitedFormat format) {
        this.format = format;

        this.parseLine = format.isFramed() ? this::parseFramedLine : this::parseUnframedLine;
    }

    public Stream<Result> stream(Stream<String> lines) {
        return StreamUtils.zipWithIndex(lines)
                .map(item -> {
                    Result result = parse(item.getValue());
                    result.getRecord().put("lineIndex", item.getIndex());
                    return result;
                });
    }

    public Result parse(String line) {
        return parseLine.apply(line);
    }

    public Result parseUnframedLine(String line) {
        Record record = new Record();
        List<Problem> problems = new LinkedList<>();

        StringBuffer cell = new StringBuffer();
        boolean inside = false;

        char delimiter = format.getDelimiter();

        int columnIndex = 0;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (inside) {
                if (ch == delimiter) {
                    inside = false;
                    setColumn(record, problems, columnIndex, cell.toString());
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

        setColumn(record, problems, columnIndex, cell.toString());
        return Result.withProblems(record, problems);
    }

    public Result parseFramedLine(String line) {
        Record record = new Record();
        List<Problem> problems = new LinkedList<>();

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
                    setColumn(record, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else if (ch == delimiter) {
                    inside = false;
                    quoted = false;
                    setColumn(record, problems, columnIndex, cell.toString());
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
                    return Result.withProblems(record, problems);
                }
            } else {
                inside = true;
                if (ch == frameDelimiter) {
                    quoted = true;
                } else {
                    problems.add(new FramingProblem(columnIndex, i));
                    return Result.withProblems(record, problems);
                }
            }
        }

        return Result.withProblems(record, problems);
    }

    private boolean setColumn(Map<String, Object> data, List<Problem> problems, int index, String value) {
        boolean indexInRange = index < format.getColumns().size();
        if (indexInRange) {
            Column column = format.getColumns().get(index);

            String columnId = column.getId();
            String columnType = column.getType();
            Map<String, Object> props = column.getProps();
            ValueOrProblem result = ValueParser.parse(columnId, columnType, props, value);

            if (result.hasProblem()) {
                problems.add(result.getProblem());
            } else {
                data.put(columnId, result.getValue());
            }
            return true;
        }

        return false;
    }
}
