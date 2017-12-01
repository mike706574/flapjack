package fun.mike.flapjack;

public class NoSuchTypeError implements fun.mike.flapjack.Error {
    private final Field field;

    public NoSuchTypeError(Field field) {
        this.field = field;
    }

    public String explain() {
        return String.format("Type %s of field %s does not exist.",
                field.getType(),
                field.getId());
    }
}
