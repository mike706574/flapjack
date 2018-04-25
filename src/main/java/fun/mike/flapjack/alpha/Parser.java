package fun.mike.flapjack.alpha;

public interface Parser {
    static ParseResult parse(Format format, String line) {
        return ParserFactory.build(format).parse(line);
    }

    ParseResult parse(String line);
}
