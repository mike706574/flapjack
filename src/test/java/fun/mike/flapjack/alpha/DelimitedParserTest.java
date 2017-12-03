package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


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

        List<Record> records = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, records.size());

        Record record1 = records.get(0);
        assertEquals("baz", record1.get("foo"));
        assertEquals("burp", record1.get("bar"));

        Record record2 = records.get(1);
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

        List<Record> records = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(2, records.size());

        Record record1 = records.get(0);
        assertEquals("baz", record1.get("foo"));
        assertEquals("burp", record1.get("bar"));

        Record record2 = records.get(1);
        assertEquals("bip", record2.get("foo"));
        assertEquals("bop", record2.get("bar"));
    }

    @Test
    public void badFrameAtStart() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"));

        DelimitedFormat format = DelimitedFormat.framed("baz", "Baz", ",", "\"", columns);

        DelimitedParser parser = new DelimitedParser(format);

        List<String> lines = Arrays.asList("baz");

        List<Record> records = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(1, records.size());

        Record record1 = records.get(0);

        Assert.assertEquals("Column 1 was not properly framed (at character 1).",
                record1.getProblems().iterator().next().explain());
    }

    @Test
    public void badFrameAtDelimiter() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.framed("baz", "Baz", ",", "\"", columns);

        DelimitedParser parser = new DelimitedParser(format);

        List<String> lines = Arrays.asList("\"baz\"\"bip\"");

        List<Record> records = parser.stream(lines.stream())
                .collect(Collectors.toList());

        assertEquals(1, records.size());

        Record record1 = records.get(0);

        Assert.assertEquals("Column 2 was not properly framed (at character 6).",
                record1.getProblems().iterator().next().explain());
    }
}
