package fun.mike.flapjack.alpha;

public class ResultException extends RuntimeException {
    private Result result;

    public ResultException(Result result) {
        super(result.explain());
    }
}
