package fun.mike.flapjack.alpha;

public interface OutputContext<T> {
    OutputChannel<T> buildChannel();

    void accept(OutputContextVisitor visitor);
}
