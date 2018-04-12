package fun.mike.flapjack.alpha;

public class ParserFactory {
    public static Parser build(Format format) {
        if (format instanceof DelimitedFormat) {
            return new DelimitedParser((DelimitedFormat) format);
        } else if (format instanceof FixedWidthFormat) {
            return new FixedWidthParser((FixedWidthFormat) format);
        }

        throw new UnsupportedOperationException();
    }
}
