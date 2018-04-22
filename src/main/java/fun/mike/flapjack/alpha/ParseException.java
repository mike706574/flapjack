package fun.mike.flapjack.alpha;

public class ParseException extends RuntimeException {
    private ParseResult result;

    public ParseException(ParseResult result) {
        super(result.explain());
        this.result = result;
    }
}
