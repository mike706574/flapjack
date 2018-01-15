package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import fun.mike.record.alpha.Record;

public class DelimitedParser implements Serializable {
    private final DelimitedFormat format;

    public DelimitedParser(DelimitedFormat format) {
        this.format = format;
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
        return format.isFramed() ? parseFramedLine(line) : parseUnframedLine(line);
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

        checkForMissingColumns(columnIndex, problems);

        return Result.withProblems(record, problems);
    }

    public Result parseFramedLine(String line) {
        Record record = new Record();
        List<Problem> problems = new LinkedList<>();

        StringBuffer cell = new StringBuffer();
        boolean inside = false;
        boolean inFrame = false;
        boolean afterEndFrame = false;

        char delimiter = format.getDelimiter();

        char frameDelimiter = format.getFrameDelimiter().get().charAt(0);

        int columnIndex = 0;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            // System.out.print("CHAR |" + ch + "|");
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
                } else if (ch == delimiter) {
                    // System.out.print(" END-CELL");
                    inside = false;
                    inFrame = false;
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
                } else {
                    // System.out.print(" FRAME-END-PROBLEM");
                    problems.add(new FramingProblem(columnIndex, i));
                    return Result.withProblems(record, problems);
                }
            } else {
                // System.out.print(" OUTSIDE");
                inside = true;
                if (ch == frameDelimiter) {
                    // System.out.print(" START-FRAME");
                    inFrame = true;
                }
                else if(format.framingRequired()) {
                    // System.out.print(" NOT-FRAMED-PROBLEM");
                    problems.add(new FramingProblem(columnIndex, i));
                    return Result.withProblems(record, problems);
                }
                else {
                    // System.out.print(" START-UNFRAMED");
                    cell.append(ch);
                }
            }

            // System.out.print('\n');
        }

        if(cell.length() != 0) {
            setColumn(record, problems, columnIndex, cell.toString());
            columnIndex++;
        }

        checkForMissingColumns(columnIndex - 1, problems);

        return Result.withProblems(record, problems);
    }

    private boolean setColumn(Map<String, Object> data, List<Problem> problems, int index, String value) {
        int offsetIndex = index - format.getOffset();

        if(offsetIndex < 0) {
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
