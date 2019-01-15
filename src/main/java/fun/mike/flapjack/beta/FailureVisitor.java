package fun.mike.flapjack.beta;

public interface FailureVisitor {
    void visit(SerializationFailure failure);

    void visit(ParseFailure failure);

    void visit(TransformFailure failure);

    void visit(OutputFailure failure);
}
