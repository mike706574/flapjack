package fun.mike.flapjack.alpha;

public interface InputContext {
    InputChannel buildChannel();

    void accept(InputContextVisitor visitor);
}
