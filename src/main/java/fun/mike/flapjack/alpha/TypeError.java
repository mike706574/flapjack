package fun.mike.flapjack.alpha;

public class TypeError implements fun.mike.flapjack.alpha.Error {
    private final String id;
    private final String type;
    private final String value;


    public TypeError(String id, String type, String value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public String explain() {
        return String.format("We expected field %s with value %s to be a %s.",
                id,
                value,
                type);
    }
}
