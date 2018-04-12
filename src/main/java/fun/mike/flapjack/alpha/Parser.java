package fun.mike.flapjack.alpha;

import fun.mike.record.alpha.Record;

public interface Parser {
    static Result<Record> parse(Format format, String line) {
        return ParserFactory.build(format).parse(line);
    }

    Result<Record> parse(String line);
}
