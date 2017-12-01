package fun.mike.flapjack;

public class ObjectOrError {
    private Object object;
    private fun.mike.flapjack.Error error;

    public ObjectOrError( Object object,
                          Error error ) {
        this.object = object;
        this.error = error;
    }

    public static ObjectOrError error(Error error) {
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

    public fun.mike.flapjack.Error getError() {
        return this.error;
    }
}
