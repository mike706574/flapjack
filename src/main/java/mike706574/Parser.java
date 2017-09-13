package mike706574;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;

public class Parser {
    private final Format format;

    public Parser( Format format ) {
        this.format = format;
    }

    private Record parseLine( Long index, String line ) {
        Optional<Integer> length = format.getLength();

        if( length.isPresent() && !length.get().equals( line.length() ) ) {
            return Record.error( index, "length-mismatch", line );
        }

        Map<String, String> data = new LinkedHashMap<String, String>();

        try {
            for( Field field : format.getFields() ) {
                String value = line.substring( field.getStart() - 1,
                                               field.getEnd() );
                data.put( field.getId(), value );
            }
        }
        catch( IndexOutOfBoundsException ex ) {
            return Record.error( index, "too-short", line );
        }

        return Record.of( index, data );
    }

    public Stream<Record> stream( Stream<String> lines ) {
        return StreamUtils.zipWithIndex( lines )
            .map( item -> parseLine( item.getIndex(), item.getValue() ) );
    }
}
