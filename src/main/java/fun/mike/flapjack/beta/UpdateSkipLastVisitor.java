package fun.mike.flapjack.beta;

public class UpdateSkipLastVisitor implements FormatVisitor {
    private Format newFormat;
    private final int count;

    public UpdateSkipLastVisitor(int count) {
        this.count = count;
    }

    public static Format visit(Format format, int count) {
        UpdateSkipLastVisitor visitor = new UpdateSkipLastVisitor(count);
        format.visit(visitor);
        return format;
    }

    @Override
    public void accept(DelimitedFormat format) {
        newFormat = format.skipLast(count);
    }

    @Override
    public void accept(FixedWidthFormat format) {
        newFormat = format.skipLast(count);
    }
}
