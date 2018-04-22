package fun.mike.flapjack.alpha;

import fun.mike.record.alpha.Record;

public interface Parser {
    static ParseResult parse(Format format, String line) {
        return ParserFactory.build(format).parse(line);
    }

    ParseResult parse(String line);
}
