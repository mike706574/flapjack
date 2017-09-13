package mike706574;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

public class ParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testIt() {
        List<Field> fields = Arrays.asList( new Field( "foo", 1, 5 ),
                                            new Field( "bar", 6, 10 ) );

        Format format = new Format( "foo", "Foo", 10, fields );

        Parser parser = new Parser( format );

        List<String> lines = Arrays.asList( "1234567890",
                                            "abcdefghij" );

        List<Record> records = parser.stream( lines.stream() )
            .collect( Collectors.toList() );

        assertEquals( 2, records.size() );

        Record record1 = records.get( 0 );
        assertEquals( "12345", record1.get( "foo" ) );
        assertEquals( "67890", record1.get( "bar" ) );

        Record record2 = records.get( 1 );
        assertEquals( "abcde", record2.get( "foo" ) );
        assertEquals( "fghij", record2.get( "bar" ) );
    }
}
