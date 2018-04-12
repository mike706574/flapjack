package fun.mike.flapjack.alpha;

import fun.mike.record.alpha.Record;

public interface Parser {
    Result<Record> parse(String line);

    static Result<Record> parse(Format format, String line) {
        return ParserFactory.build(format).parse(line);
    }
}
