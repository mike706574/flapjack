package fun.mike.flapjack.alpha;

public class ObjectOrError {
    private Object object;
    private fun.mike.flapjack.alpha.Error error;

    public ObjectOrError(Object object,
                         fun.mike.flapjack.alpha.Error error) {
        this.object = object;
        this.error = error;
    }

    public static ObjectOrError error(fun.mike.flapjack.alpha.Error error) {
        return new ObjectOrError(null, error);
    }

    public static ObjectOrError object(Object object) {
        return new ObjectOrError(object, null);
    }

    public boolean isError() {
        return this.error != null;
    }

    public Object getObject() {
        return this.object;
    }

    public fun.mike.flapjack.alpha.Error getError() {
        return this.error;
    }
}
