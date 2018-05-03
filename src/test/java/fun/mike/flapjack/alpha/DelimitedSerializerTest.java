package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        SerializationResult result = serializer.serialize(record);

        assertTrue(result.isOk());

        assertEquals("abcde,23", result.getValue());
    }

    @Test
    public void validFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "integer"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);
        DelimitedSerializer serializer = new DelimitedSerializer(format);

        Record record = Record.of("foo", "abcde", "bar", 23);

        SerializationResult result = serializer.serialize(record);

        assertTrue(result.isOk());

        assertEquals("\"abcde\",\"23\"", result.getValue());
    }

    @Test
    public void withEndingDelimiter() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "integer"));

        DelimitedFormat format = DelimitedFormat
                .alwaysFramed("baz", "Baz", ',', '"', columns)
                .withEndingDelimiter();

        DelimitedSerializer serializer = new DelimitedSerializer(format);

        Record record = Record.of("foo", "abcde", "bar", 23);

        SerializationResult result = serializer.serialize(record);

        assertTrue(result.isOk());

        assertEquals("\"abcde\",\"23\",", result.getValue());
    }

    @Test
    public void streaming() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "integer"));

        DelimitedFormat format = DelimitedFormat
                .alwaysFramed("baz", "Baz", ',', '"', columns);

        DelimitedSerializer serializer = new DelimitedSerializer(format);

        List<Record> records = Arrays.asList(Record.of("foo", "abcde", "bar", 23),
                                             Record.of("foo", "fghij", "bar", 24));

        List<String> lines = records.stream()
                .map(format::serialize)
                .map(Result::orElseThrow)
                .collect(Collectors.toList());

        assertEquals(2, lines.size());
        assertEquals("\"abcde\",\"23\"", lines.get(0));
        assertEquals("\"fghij\",\"24\"", lines.get(1));
    }
}
