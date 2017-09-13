package mike706574;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.function.Function;

public class Error {
    private final String id;
    private final String fieldId;

    public Error( Error error ) {
        this.id = error.getId();
        this.fieldId = error.getFieldId().orElse( null );
    }

    public Error( String id ) {
        this.id = id;
        this.fieldId = null;
    }

    public Error( String id, String fieldId ) {
        this.id = id;
        this.fieldId = fieldId;
    }

    public String getId() {
        return this.id;
    }

    public Optional<String> getFieldId() {
        if( fieldId == null ) {
            return Optional.empty();
        }
        return Optional.of( fieldId );
    }
}
