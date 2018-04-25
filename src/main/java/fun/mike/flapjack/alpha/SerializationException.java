package fun.mike.flapjack.alpha;

public class SerializationException extends RuntimeException {
    private final SerializationResult result;

    public SerializationException(SerializationResult result) {
        super(result.explain());
        this.result = result;
    }

    public SerializationResult getResult() {
        return result;
    }
}
