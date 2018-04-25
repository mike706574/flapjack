package fun.mike.flapjack.alpha;

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
