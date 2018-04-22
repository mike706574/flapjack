package fun.mike.flapjack.alpha;

public class SerializationException extends RuntimeException {
    private SerializationResult result;

    public SerializationException(SerializationResult result) {
        super(result.explain());
        this.result = result;
    }
}
