package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.List;
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
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                Field.with("bar", 5, "string"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("1234567890",
                "abcdefghij");

        List<Result> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, results.size());

        Result result1 = results.get(0);
        assertTrue(result1.isOk());
        Record record1 = result1.getRecord();
        assertEquals("12345", record1.get("foo"));
        assertEquals("67890", record1.get("bar"));

        Result result2 = results.get(1);
        assertTrue(result2.isOk());
        Record record2 = result2.getRecord();
        assertEquals("abcde", record2.get("foo"));
        assertEquals("fghij", record2.get("bar"));
    }

    @Test
    public void validInteger() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "integer"),
                Field.with("bar", 5, "string"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("1234567890",
                "54321fghij");

        List<Result> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, results.size());

        Result result1 = results.get(0);
        assertTrue(result1.isOk());
        Record record1 = result1.getRecord();
        assertEquals(new Integer(12345), record1.get("foo"));
        assertEquals("67890", record1.get("bar"));

        Result result2 = results.get(1);
        assertTrue(result2.isOk());
        Record record2 = result2.getRecord();
        assertEquals(new Integer(54321), record2.get("foo"));
        assertEquals("fghij", record2.get("bar"));
    }

    @Test
    public void outOfBounds() {
        List<Field> fields = Arrays.asList(Field.with("foo", 2, "string"),
                Field.with("bar", 2, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);
        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("abc");
        List<Result> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(1, results.size());

        Result result1 = results.get(0);
        assertTrue(result1.hasProblems());
        Record record1 = result1.getRecord();
        assertEquals("ab", record1.get("foo"));
        assertFalse(record1.containsKey("bar"));

        List<Problem> problems = result1.getProblems();
        assertEquals(1, problems.size());

        Problem problem = problems.get(0);
        assertTrue(problem instanceof OutOfBoundsProblem);
        OutOfBoundsProblem outOfBoundsProblem = (OutOfBoundsProblem) problem;
        assertEquals("bar", outOfBoundsProblem.getFieldId());
        assertEquals(new Integer(4), outOfBoundsProblem.getEnd());
        assertEquals(new Integer(3), outOfBoundsProblem.getLength());
    }
}
