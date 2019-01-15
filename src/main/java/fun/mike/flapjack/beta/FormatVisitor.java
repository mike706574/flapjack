package fun.mike.flapjack.beta;

public interface FormatVisitor {
    void accept(DelimitedFormat format);

    void accept(FixedWidthFormat format);
}
