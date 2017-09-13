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

        Integer lineLength = line.length();
        if( length.isPresent() && !length.get().equals( lineLength ) ) {
            Error lengthMismatch = new LengthMismatchError( length.get(),
                                                            lineLength );
            errors.add( lengthMismatch );
            return Record.with( index, data, errors );
        }

        for( Field field : format.getFields() ) {
            String fieldId = field.getId();
            Integer fieldStart = field.getStart();
            Integer fieldEnd = field.getEnd();

            if( fieldEnd > lineLength ) {
                Error outOfBounds = new OutOfBoundsError( fieldId,
                                                          fieldEnd,
                                                          lineLength );
                errors.add( outOfBounds );
            }
            else {
                String value = line.substring( fieldStart - 1,
                                               fieldEnd );
                data.put( fieldId, value );
            }
        }

        return Record.with( index, data, errors );
    }

    public Stream<Record> stream( Stream<String> lines ) {
        return StreamUtils.zipWithIndex( lines )
            .map( item -> parseLine( item.getIndex(), item.getValue() ) );
    }
}
