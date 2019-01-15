package fun.mike.flapjack.beta;

public interface OutputContext<T> {
    OutputChannel<T> buildChannel();

    void accept(OutputContextVisitor visitor);
}
