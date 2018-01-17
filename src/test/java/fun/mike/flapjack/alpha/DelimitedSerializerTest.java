package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.List;

import fun.mike.record.alpha.Record;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DelimitedSerializerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void validUnframed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "integer"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);
        DelimitedSerializer serializer = new DelimitedSerializer(format);

        Record record = Record.of("foo", "abcde", "bar", 23);

        Result result = serializer.serialize(record);

        assertTrue(result.isOk());

        assertEquals("abcde,23", result.getLine());
    }

    @Test
    public void validFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "integer"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);
        DelimitedSerializer serializer = new DelimitedSerializer(format);

        Record record = Record.of("foo", "abcde", "bar", 23);

        Result result = serializer.serialize(record);

        assertTrue(result.isOk());

        assertEquals("\"abcde\",\"23\"", result.getLine());
    }
}
