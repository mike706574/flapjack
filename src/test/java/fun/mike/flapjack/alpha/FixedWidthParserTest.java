package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fun.mike.record.alpha.Record;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FixedWidthParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void valid() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("bar", 5, "string"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("1234567890",
                                           "abcdefghij");

        List<Result<Record>> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, results.size());

        Result<Record> result1 = results.get(0);
        assertTrue(result1.isOk());
        Record record1 = result1.getValue();
        assertEquals(3, record1.size());
        assertEquals(0L, record1.get("lineIndex"));
        assertEquals("12345", record1.get("foo"));
        assertEquals("67890", record1.get("bar"));

        Result<Record> result2 = results.get(1);
        assertTrue(result2.isOk());
        Record record2 = result2.getValue();
        assertEquals(3, record2.size());
        assertEquals(1L, record2.get("lineIndex"));
        assertEquals("abcde", record2.get("foo"));
        assertEquals("fghij", record2.get("bar"));
    }

    @Test
    public void filler() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("filler", 5, "filler"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("1234567890",
                                           "abcdefghij");

        List<Result<Record>> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, results.size());

        Result<Record> result1 = results.get(0);
        assertTrue(result1.isOk());
        Record record1 = result1.getValue();
        assertEquals(2, record1.size());
        assertEquals(0L, record1.get("lineIndex"));
        assertEquals("12345", record1.get("foo"));

        Result<Record> result2 = results.get(1);
        assertTrue(result2.isOk());
        Record record2 = result2.getValue();
        assertEquals(2, record2.size());
        assertEquals(1L, record2.get("lineIndex"));
        assertEquals("abcde", record2.get("foo"));
    }

    @Test
    public void validInteger() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "integer"),
                                           Field.with("bar", 5, "string"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("1234567890",
                                           "54321fghij");

        List<Result<Record>> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, results.size());

        Result<Record> result1 = results.get(0);
        assertTrue(result1.isOk());
        Record record1 = result1.getValue();
        assertEquals(12345, record1.get("foo"));
        assertEquals("67890", record1.get("bar"));

        Result<Record> result2 = results.get(1);
        assertTrue(result2.isOk());
        Record record2 = result2.getValue();
        assertEquals(54321, record2.get("foo"));
        assertEquals("fghij", record2.get("bar"));
    }

    @Test
    public void outOfBounds() {
        List<Field> fields = Arrays.asList(Field.with("foo", 2, "string"),
                                           Field.with("bar", 2, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);
        FixedWidthParser parser = new FixedWidthParser(format);

        Result<Record> result = parser.parse("abc");
        assertTrue(result.hasProblems());

        Record record = result.getValue();
        assertEquals("ab", record.get("foo"));
        assertFalse(record.containsKey("bar"));

        List<Problem> problems = result.getProblems();
        assertEquals(1, problems.size());

        Problem problem = problems.get(0);
        assertTrue(problem instanceof OutOfBoundsProblem);
        OutOfBoundsProblem outOfBoundsProblem = (OutOfBoundsProblem) problem;
        assertEquals("bar", outOfBoundsProblem.getFieldId());
        assertEquals(new Integer(4), outOfBoundsProblem.getEnd());
        assertEquals(new Integer(3), outOfBoundsProblem.getLength());
    }
}
