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

        ParseResult result = parser.parse("1234567890");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(2, record.size());
        assertEquals("12345", record.get("foo"));
        assertEquals("67890", record.get("bar"));
    }

    @Test
    public void filler() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("filler", 5, "filler"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList("1234567890",
                                           "abcdefghij");

        ParseResult result = parser.parse("1234567890");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(1, record.size());
        assertEquals("12345", record.get("foo"));
    }

    @Test
    public void validInteger() {
        List<Field> fields = Arrays.asList(Field.with("foo", 5, "integer"),
                                           Field.with("bar", 5, "string"));

        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        FixedWidthParser parser = new FixedWidthParser(format);

        List<String> lines = Arrays.asList(
                                           "54321fghij");

        ParseResult result = parser.parse("1234567890");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(12345, record.get("foo"));
        assertEquals("67890", record.get("bar"));
    }

    @Test
    public void outOfBounds() {
        List<Field> fields = Arrays.asList(Field.with("foo", 2, "string"),
                                           Field.with("bar", 2, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);
        FixedWidthParser parser = new FixedWidthParser(format);

        ParseResult result = parser.parse("abc");
        assertTrue(result.hasProblems());

        Record record = result.getValue();
        assertEquals("ab", record.get("foo"));
        assertFalse(record.containsKey("bar"));

        List<Problem> problems = result.getProblems();
        assertEquals(1, problems.size());

        Problem problem = problems.get(0);
        assertTrue(problem instanceof OutOfBoundsProblem);
        OutOfBoundsProblem outOfBoundsProblem = (OutOfBoundsProblem) problem;
        assertEquals("bar", outOfBoundsProblem.getId());
        assertEquals(new Integer(4), outOfBoundsProblem.getEnd());
        assertEquals(new Integer(3), outOfBoundsProblem.getLength());
    }

    @Test
    public void what() {
        FixedWidthFormat.builder()
                .withFields(Arrays.asList())
                .skipFirst(0)
                .skipLast(0)
                .build();


    }
}
