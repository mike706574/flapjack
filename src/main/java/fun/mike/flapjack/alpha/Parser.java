package fun.mike.flapjack.alpha;

public class Parser {
    public Result parse(DelimitedFormat format, String line) {
        return new DelimitedParser(format).parse(line);
    }

    public Result parse(FixedWidthFormat format, String line) {
        return new FixedWidthParser(format).parse(line);
    }
}
