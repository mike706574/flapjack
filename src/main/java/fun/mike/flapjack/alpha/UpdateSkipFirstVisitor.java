package fun.mike.flapjack.alpha;

public class UpdateSkipFirstVisitor implements FormatVisitor {
    private Format newFormat;
    private int count;

    public UpdateSkipFirstVisitor(int count) {
        this.count = count;
    }

    public static Format visit(Format format, int count) {
        UpdateSkipFirstVisitor visitor = new UpdateSkipFirstVisitor(count);
        format.visit(visitor);
        return format;
    }

    @Override
    public void accept(DelimitedFormat format) {
        newFormat = format.skipFirst(count);
    }

    @Override
    public void accept(FixedWidthFormat format) {
        newFormat = format.skipFirst(count);
    }
}
