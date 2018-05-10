package fun.mike.flapjack.alpha;

public class GetSkipLastVisitor implements FormatVisitor {
    private int count;

    public static int visit(Format format) {
        GetSkipLastVisitor visitor = new GetSkipLastVisitor();
        format.visit(visitor);
        return visitor.getCount();
    }

    public int getCount() {
        return count;
    }

    @Override
    public void accept(DelimitedFormat format) {
        count = format.getSkipLast();
    }

    @Override
    public void accept(FixedWidthFormat format) {
        count = format.getSkipLast();
    }
}
