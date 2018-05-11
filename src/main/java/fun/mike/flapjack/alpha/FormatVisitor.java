package fun.mike.flapjack.alpha;

public interface FormatVisitor {
    void accept(DelimitedFormat format);

    void accept(FixedWidthFormat format);
}
