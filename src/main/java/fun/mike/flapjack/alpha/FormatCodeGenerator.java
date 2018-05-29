package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FormatCodeGenerator {
    public static String generateDelimitedColumns(String header, char delimiter) {
        String columns = Arrays.stream(header.split(String.valueOf(delimiter)))
                .map(columnId -> "Column.string(" + columnId + ")")
                .collect(Collectors.joining(",\n     "));

        return "List<Column> columns = Arrays.asList(" + columns + ");";
    }
}
