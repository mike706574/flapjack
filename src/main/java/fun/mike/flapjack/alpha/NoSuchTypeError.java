package fun.mike.flapjack.alpha;

public class NoSuchTypeError implements fun.mike.flapjack.alpha.Error {
    private final String id;
    private final String type;

    public NoSuchTypeError(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String explain() {
        return String.format("Type %s of field %s does not exist.",
                type,
                id);
    }
}
