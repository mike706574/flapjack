package fun.mike.flapjack.beta;

import java.util.stream.Collectors;

public class HeaderBuilder {
    public static String build(DelimitedFormat format) {
        String headerFrameDelimiter = format.isFramed() ? format.getFrameDelimiter().get().toString() : "";
        return format.getColumns().stream()
                .map(column -> headerFrameDelimiter + column.getId() + headerFrameDelimiter)
                .collect(Collectors.joining(format.getDelimiter().toString()));
    }
}
