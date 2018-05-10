package fun.mike.flapjack.alpha;

public class GetSkipFirstVisitor implements FormatVisitor {
    private int count;

    public static int visit(Format format) {
        GetSkipFirstVisitor visitor = new GetSkipFirstVisitor();
        format.visit(visitor);
        return visitor.getCount();
    }

    public int getCount() {
        return count;
    }

    @Override
    public void accept(DelimitedFormat format) {
        count = format.getSkipFirst();
    }

    @Override
    public void accept(FixedWidthFormat format) {
        count = format.getSkipFirst();
    }
}
