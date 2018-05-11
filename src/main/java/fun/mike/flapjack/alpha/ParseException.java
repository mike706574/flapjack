package fun.mike.flapjack.alpha;

/**
 * Thrown when an attempt is made to access the parsed value from a result with
 * parsing problems.
 */
public class ParseException extends RuntimeException {
    private final ParseResult result;

    public ParseException(ParseResult result) {
        super(result.explain());
        this.result = result;
    }

    public ParseResult getResult() {
        return result;
    }
}
