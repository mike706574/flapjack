package fun.mike.flapjack.alpha;

/**
 * Thrown when an attempt is made to access the serialized value from a result
 * with serialization problems.
 */
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
