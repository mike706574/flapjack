package fun.mike.flapjack.beta;

public class FormatExplainer {
    public static String explain(Format format) {
        if (format instanceof FixedWidthFormat) return FixedWidthFormatExplainer.explain((FixedWidthFormat) format);
        if (format instanceof DelimitedFormat) return DelimitedFormatExplainer.explain((DelimitedFormat) format);
        throw new IllegalArgumentException("Unsupported format type: " + format.getClass().getName());
    }
}
