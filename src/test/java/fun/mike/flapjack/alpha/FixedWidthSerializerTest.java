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

public class FixedWidthSerializerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void valid() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("bar", 5, "integer"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);
        FixedWidthSerializer serializer = new FixedWidthSerializer(format);

        Record record = Record.of("foo", "abcde", "bar", 23);

        SerializationResult result = serializer.serialize(record);

        assertTrue(result.isOk());

        assertEquals("abcde23   ", result.getValue());
    }

    @Test
    public void truncation() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("bar", 5, "integer"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);
        FixedWidthSerializer serializer = new FixedWidthSerializer(format);

        Record record = Record.of("foo", "abcdefghi", "bar", 123456);

        SerializationResult result = serializer.serialize(record);

        assertTrue(result.hasProblems());

        List<Problem> problems = result.getProblems();
        assertEquals("          ", result.getValue());
        assertEquals(2, problems.size());
        assertEquals(new TruncationProblem("foo", "string", 5, "abcdefghi"), problems.get(0));
        assertEquals(new TruncationProblem("bar", "integer", 5, "123456"), problems.get(1));
    }

    @Test
    public void streaming() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("bar", 5, "integer"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);
        FixedWidthSerializer serializer = new FixedWidthSerializer(format);

        Record record = Record.of("foo", "abcdefghi", "bar", 123456);

        List<Record> records = Arrays.asList(Record.of("foo", "abcde", "bar", 23),
                                             Record.of("foo", "fghij", "bar", 24));

        List<String> lines = records.stream()
                .map(format::serialize)
                .map(Result::orElseThrow)
                .collect(Collectors.toList());

        assertEquals(2, lines.size());
        assertEquals("abcde23   ", lines.get(0));
        assertEquals("fghij24   ", lines.get(1));
    }
}
