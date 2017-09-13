package mike706574;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Format {
    private final String id;
    private final String description;
    private final Integer length;
    private final List<Field> fields;

    @JsonCreator
    public Format( @JsonProperty( "id" ) String id,
                   @JsonProperty( "description" ) String description,
                   @JsonProperty( "length" ) Integer length,
                   @JsonProperty( "fields" ) List<Field> fields) {
        this.id = id;
        this.description = description;
        this.length = length;
        this.fields = Collections.unmodifiableList( fields );
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Optional<Integer> getLength() {
        if( this.length == null ) {
            return Optional.empty();
        }
        return Optional.of( this.length );
    }

    public List<Field> getFields() {
        return this.fields;
    }
}
