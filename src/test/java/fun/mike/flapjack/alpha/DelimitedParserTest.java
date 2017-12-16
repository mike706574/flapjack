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


public class DelimitedParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void unframed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ",", columns);
        DelimitedParser parser = new DelimitedParser(format);

        List<String> lines = Arrays.asList("baz,burp",
                "bip,bop");

        List<Result> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, results.size());

        Result result1 = results.get(0);
        assertTrue(result1.isOk());
        Record record1 = result1.getRecord();
        assertEquals("baz", record1.get("foo"));
        assertEquals("burp", record1.get("bar"));

        Result result2 = results.get(1);
        assertTrue(result2.isOk());
        Record record2 = result2.getRecord();
        assertEquals("bip", record2.get("foo"));
        assertEquals("bop", record2.get("bar"));
    }

    @Test
    public void framed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.framed("baz", "Baz", ",", "\"", columns);

        DelimitedParser parser = new DelimitedParser(format);

        List<String> lines = Arrays.asList("\"baz\",\"burp\"",
                "\"bip\",\"bop\"");

        List<Result> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, results.size());

        Result result1 = results.get(0);
        assertTrue(result1.isOk());
        Record record1 = result1.getRecord();
        assertEquals("baz", record1.get("foo"));
        assertEquals("burp", record1.get("bar"));

        Result result2 = results.get(1);
        assertTrue(result2.isOk());
        Record record2 = result2.getRecord();
        assertEquals("bip", record2.get("foo"));
        assertEquals("bop", record2.get("bar"));
    }

    @Test
    public void badFrameAtStart() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"));

        DelimitedFormat format = DelimitedFormat.framed("baz", "Baz", ",", "\"", columns);

        DelimitedParser parser = new DelimitedParser(format);

        List<String> lines = Arrays.asList("baz");

        List<Result> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(1, results.size());

        Result result1 = results.get(0);
        assertTrue(result1.hasProblems());
        List<Problem> problems = result1.getProblems();
        assertEquals(1, problems.size());
        assertEquals("Column 1 was not properly framed (at character 1).",
                problems.get(0).explain());
    }

    @Test
    public void badFrameAtDelimiter() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.framed("baz", "Baz", ",", "\"", columns);

        DelimitedParser parser = new DelimitedParser(format);

        List<String> lines = Arrays.asList("\"baz\"\"bip\"");

        List<Result> results = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(1, results.size());

        Result result1 = results.get(0);
        assertTrue(result1.hasProblems());
        List<Problem> problems = result1.getProblems();
        assertEquals(1, problems.size());
        assertEquals("Column 2 was not properly framed (at character 6).",
                problems.get(0).explain());
    }
}
