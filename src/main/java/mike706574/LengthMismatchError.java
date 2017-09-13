package mike706574;

public class LengthMismatchError implements Error {
    private final Integer expectedLength;
    private final Integer actualLength;

    public LengthMismatchError( Integer expectedLength,
                                Integer actualLength ) {
        this.expectedLength = expectedLength;
        this.actualLength = actualLength;
    }

    public String explain() {
        return String.format( "We expected the record to be %d characters long, but it was actually %d characters long.",
                              expectedLength,
                              actualLength );
    }
}
