package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<Field> fields = Arrays.asList(Field.with("foo", 1, 5, "string"),
                Field.with("bar", 6, 10, "string"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", 10, fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("1234567890",
                "abcdefghij");

        List<Record> records = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, records.size());

        Record record1 = records.get(0);
        assertEquals("12345", record1.get("foo"));
        assertEquals("67890", record1.get("bar"));

        Record record2 = records.get(1);
        assertEquals("abcde", record2.get("foo"));
        assertEquals("fghij", record2.get("bar"));
    }

    @Test
    public void validInteger() {
        List<Field> fields = Arrays.asList(Field.with("foo", 1, 5, "integer"),
                Field.with("bar", 6, 10, "string"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", 10, fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("1234567890",
                "54321fghij");

        List<Record> records = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, records.size());

        Record record1 = records.get(0);
        assertEquals(new Integer(12345), record1.get("foo"));
        assertEquals("67890", record1.get("bar"));

        Record record2 = records.get(1);
        assertEquals(new Integer(54321), record2.get("foo"));
        assertEquals("fghij", record2.get("bar"));
    }

    @Test
    public void lengthMismatch() {
        List<Field> fields = Arrays.asList(Field.with("foo", 1, 2, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", 2, fields);
        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("abc");
        List<Record> records = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(1, records.size());

        Record record1 = records.get(0);
        assertTrue(record1.isEmpty());

        Set<Problem> problems = record1.getProblems();
        assertEquals(1, problems.size());
        Problem problem = problems.iterator().next();
        assertTrue(problem instanceof LengthMismatchProblem);
    }

    @Test
    public void outOfBounds() {
        List<Field> fields = Arrays.asList(Field.with("foo", 1, 2, "string"),
                Field.with("bar", 3, 4, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", null, fields);
        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("abc");
        List<Record> records = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(1, records.size());

        Record record1 = records.get(0);

        assertEquals("ab", record1.get("foo"));
        assertFalse(record1.containsKey("bar"));

        Set<Problem> problems = record1.getProblems();
        assertEquals(1, problems.size());

        Problem problem = problems.iterator().next();
        assertTrue(problem instanceof OutOfBoundsProblem);
        OutOfBoundsProblem outOfBoundsProblem = (OutOfBoundsProblem) problem;
        assertEquals("bar", outOfBoundsProblem.getFieldId());
        assertEquals(new Integer(4), outOfBoundsProblem.getEnd());
        assertEquals(new Integer(3), outOfBoundsProblem.getLength());
    }
}
