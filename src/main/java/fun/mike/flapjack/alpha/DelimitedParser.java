package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import fun.mike.record.alpha.Record;

public class DelimitedParser implements Parser, Serializable {
    private final DelimitedFormat format;

    public DelimitedParser(DelimitedFormat format) {
        this.format = format;
    }

    public Stream<Result<Record>> stream(Stream<String> lines) {
        return StreamUtils.zipWithIndex(lines)
                .map(item -> {
                    Result<Record> result = parse(item.getValue());
                    result.getValue().put("lineIndex", item.getIndex());
                    return result;
                });
    }

    public Result<Record> parse(String line) {
        return format.isFramed() ? parseFramedLine(line) : parseUnframedLine(line);
    }

    public Result<Record> parseUnframedLine(String line) {
        Record record = new Record();
        List<Problem> problems = new LinkedList<>();

        StringBuffer cell = new StringBuffer();
        boolean inside = true;

        char delimiter = format.getDelimiter();

        int columnIndex = 0;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            // System.out.print("CHAR |" + ch + "|");
            if (inside) {
                // System.out.print(" INSIDE");
                if (ch == delimiter) {
                    inside = false;
                    setColumn(record, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else {
                    // System.out.print(" APPEND");
                    cell.append(ch);
                }
            } else {
                // System.out.print(" OUTSIDE");
                inside = true;
                if (ch == delimiter) {
                    setColumn(record, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else {
                    cell.append(ch);
                }
            }
            // System.out.print('\n');
        }

        setColumn(record, problems, columnIndex, cell.toString());

        checkForMissingColumns(columnIndex, problems);

        return Result.withProblems(record, problems);
    }

    public Result<Record> parseFramedLine(String line) {
        Record record = new Record();
        List<Problem> problems = new LinkedList<>();

        StringBuffer cell = new StringBuffer();
        boolean inFrame = false;
        boolean afterEndFrame = false;
        boolean afterDelimiter = false;

        char delimiter = format.getDelimiter();

        char frameDelimiter = format.getFrameDelimiter().get();

        int columnIndex = 0;

        // EDGE: If there's a delimiter at the start of the record and framing
        // is not required, then we're starting inside of a cell
        boolean framingRequired = format.framingRequired();
        boolean delimiterAtStart = line.charAt(0) == delimiter;
        boolean inside = false;
        if (!framingRequired && delimiterAtStart) {
            inside = true;
        }

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            // System.out.print("CHAR |" + ch + "|");
            afterDelimiter = false;
            if (inside) {
                // System.out.print(" INSIDE");
                if (inFrame && ch == frameDelimiter) {
                    // System.out.print(" END-FRAME");
                    inside = false;
                    inFrame = false;
                    afterEndFrame = true;
                    setColumn(record, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else if (!inFrame && ch == delimiter) {
                    // System.out.print(" END-CELL");
                    inside = false;
                    inFrame = false;
                    afterDelimiter = true;
                    setColumn(record, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else {
                    // System.out.print(" APPEND");
                    cell.append(ch);
                }
            } else if (afterEndFrame) {
                // System.out.print(" AFTER-END-FRAME");
                if (ch == delimiter) {
                    // System.out.print(" END-CELL");
                    afterEndFrame = false;
                    afterDelimiter = true;
                } else {
                    // System.out.print(" FRAME-END-PROBLEM");
                    problems.add(new FramingProblem(columnIndex, i));
                    return Result.withProblems(record, problems);
                }
            } else {
                // System.out.print(" OUTSIDE");
                if (ch == frameDelimiter) {
                    inside = true;
                    // System.out.print(" START-FRAME");
                    inFrame = true;
                } else if (framingRequired) {
                    // System.out.print(" NOT-FRAMED-PROBLEM");
                    problems.add(new FramingProblem(columnIndex, i));
                    return Result.withProblems(record, problems);
                } else if (ch == delimiter) {
                    setColumn(record, problems, columnIndex, cell.toString());
                    cell = new StringBuffer();
                    columnIndex++;
                } else {
                    // System.out.print(" START-UNFRAMED");
                    inside = true;
                    cell.append(ch);
                }
            }
            // System.out.print('\n');
        }

        if (cell.length() > 0 || afterDelimiter) {
            // System.out.print("END");
            setColumn(record, problems, columnIndex, cell.toString());
            // System.out.print('\n');
            columnIndex++;
        }

        checkForMissingColumns(columnIndex - 1, problems);

        return Result.withProblems(record, problems);
    }

    private boolean setColumn(Map<String, Object> data, List<Problem> problems, int index, String value) {
        // System.out.print(" SET-COLUMN-" + index);
        int offsetIndex = index - format.getOffset();

        if (offsetIndex < 0) {
            return false;
        }

        if (offsetIndex < format.getColumns().size()) {
            Column column = format.getColumns().get(offsetIndex);

            String columnId = column.getId();
            String columnType = column.getType();
            Map<String, Object> props = column.getProps();

            if (!columnType.equals("filler")) {
                ValueOrProblem result = ValueParser.parse(columnId, columnType, props, value);

                if (result.hasProblem()) {
                    problems.add(result.getProblem());
                } else {
                    data.put(columnId, result.getValue());
                }
            }
            return true;
        }

        return false;
    }

    private boolean checkForMissingColumns(int columnIndex, List<Problem> problems) {
        List<Column> columns = format.getColumns();

        int columnCount = columns.size();

        int nextColumnIndex = columnIndex + 1;

        if (nextColumnIndex < columnCount) {
            for (int i = nextColumnIndex; i < columnCount; i++) {
                Column column = columns.get(i);
                problems.add(new MissingValueProblem(column.getId(),
                                                     column.getType()));
            }
            return false;
        }

        return true;
    }
}
