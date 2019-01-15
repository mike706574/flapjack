package fun.mike.flapjack.beta;

public interface InputContext {
    InputChannel buildChannel();

    void accept(InputContextVisitor visitor);
}
