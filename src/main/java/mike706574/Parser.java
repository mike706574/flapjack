package mike706574;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;

public class Parser {
    private final Format format;

    public Parser( Format format ) {
        this.format = format;
    }

    private Record parseLine( Long index, String line ) {
        Map<String, Object> data = new HashMap<String, Object>();
        Set<Error> errors = new HashSet<Error>();

        Optional<Integer> length = format.getLength();

        if( length.isPresent() && !length.get().equals( line.length() ) ) {
            Error lengthMismatch = new Error( "length-mismatch" );
            errors.add( lengthMismatch );
        }

        for( Field field : format.getFields() ) {
            String fieldId = field.getId();
            try {
                String value = line.substring( field.getStart() - 1,
                                               field.getEnd() );
                data.put( fieldId, value );
            }
            catch( IndexOutOfBoundsException ex ) {
                Error outOfBounds = new Error( "field-out-of-bounds", fieldId );
                errors.add( outOfBounds );
            }
        }

        return Record.with( index, data, errors );
    }

    public Stream<Record> stream( Stream<String> lines ) {
        return StreamUtils.zipWithIndex( lines )
            .map( item -> parseLine( item.getIndex(), item.getValue() ) );
    }
}
