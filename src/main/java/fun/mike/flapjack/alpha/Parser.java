package fun.mike.flapjack.alpha;

public interface Parser {
    Result parse(String line);

    static Result parse(Format format, String line) {
        return ParserFactory.build(format).parse(line);
    }
}
