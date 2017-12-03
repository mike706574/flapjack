package fun.mike.flapjack.alpha;

public class NoSuchTypeProblem implements Problem {
    private final String id;
    private final String type;

    public NoSuchTypeProblem(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String explain() {
        return String.format("Type \"%s\" specified for field \"%s\" does not exist.",
                type,
                id);
    }
}
