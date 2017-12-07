package fun.mike.flapjack.alpha;

public class Parser {
    public Record parse(DelimitedFormat format, String line) {
        return new DelimitedParser(format).parse(line);
    }

    public Record parse(FixedWidthFormat format, String line) {
        return new FixedWidthParser(format).parse(line);
    }
}
