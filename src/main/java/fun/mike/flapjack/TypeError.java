package fun.mike.flapjack;

public class TypeError implements fun.mike.flapjack.Error {
    private final Field field;
    private final String value;

    public TypeError(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    public String explain() {
        return String.format("We expected field %s with value %s to be a %s.",
                field.getId(),
                value,
                field.getType());
    }
}
