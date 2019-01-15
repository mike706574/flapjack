package fun.mike.flapjack.beta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fun.mike.record.Record;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DelimitedParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void alwaysFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("\"baz\",\"burp\"");

        assertTrue(result.isOk());

        Record record = result.getValue();
        assertEquals("\"baz\",\"burp\"", record.getMetadataProperty("line"));
        assertEquals(2, record.size());
        assertEquals("baz", record.get("foo"));
        assertEquals("burp", record.get("bar"));
    }

    @Test
    public void unframed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("baz,burp");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(2, record.size());
        assertEquals("baz", record.get("foo"));
        assertEquals("burp", record.get("bar"));
    }

    @Test
    public void emptyUnframed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse(",");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(2, record.size());
        assertEquals("", record.get("foo"));
        assertEquals("", record.get("bar"));
    }

    @Test
    public void adjacentDelimitersUnframed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"),
                                             Column.with("baz", "string"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse(",,");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(3, record.size());
        assertEquals("", record.get("foo"));
        assertEquals("", record.get("bar"));
        assertEquals("", record.get("baz"));
    }

    @Test
    public void endingDelimiterUnframed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat
                .unframed("baz", "Baz", '|', columns)
                .withEndingDelimiter();
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("baz|bip|");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(2, record.size());
        assertEquals("baz", record.get("foo"));
        assertEquals("bip", record.get("bar"));
    }

    @Test
    public void framed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("\"baz\",\"burp\"");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(2, record.size());
        assertEquals("baz", record.get("foo"));
        assertEquals("burp", record.get("bar"));
    }

    @Test
    public void emptyFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("\"\",\"\"");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(2, record.size());
        assertEquals("", record.get("foo"));
        assertEquals("", record.get("bar"));
    }

    @Test
    public void endingDelimiterFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat
                .alwaysFramed("baz", "Baz", '|', '"', columns)
                .withEndingDelimiter();
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("\"baz\"|\"bip\"|");

        Record record = result.getValue();
        assertEquals(2, record.size());
        assertEquals("baz", record.get("foo"));
        assertEquals("bip", record.get("bar"));
    }

    @Test
    public void adjacentDelimitersFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"),
                                             Column.with("baz", "string"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("\"\",\"\",\"\"");

        assertTrue(result.isOk());
        Record record = result.getValue();
        assertEquals(3, record.size());
        assertEquals("", record.get("foo"));
        assertEquals("", record.get("bar"));
        assertEquals("", record.get("baz"));
    }

    @Test
    public void optionallyFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.optionallyFramed("baz", "Baz", ',', '"', columns);

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result1 = parser.parse("\"baz\",burp");

        assertTrue(result1.isOk());
        Record record1 = result1.getValue();
        assertEquals(2, record1.size());
        assertEquals("baz", record1.get("foo"));
        assertEquals("burp", record1.get("bar"));

        ParseResult result2 = parser.parse("bip,\"bop\"");

        assertTrue(result2.isOk());
        Record record2 = result2.getValue();
        assertEquals(2, record2.size());
        assertEquals("bip", record2.get("foo"));
        assertEquals("bop", record2.get("bar"));
    }

    @Test
    public void endingDelimiterOptionallyFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat
                .optionallyFramed("baz", "Baz", '|', '"', columns)
                .withEndingDelimiter();
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("baz|bip|");

        Record record = result.getValue();
        assertEquals(2, record.size());
        assertEquals("baz", record.get("foo"));
        assertEquals("bip", record.get("bar"));
    }

    @Test
    public void emptyOptionallyFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.optionallyFramed("baz", "Baz", ',', '"', columns);

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result1 = parser.parse("\"\",");

        assertTrue(result1.explain(), result1.isOk());
        Record record1 = result1.getValue();
        assertEquals(2, record1.size());
        assertEquals("", record1.get("foo"));
        assertEquals("", record1.get("bar"));

        ParseResult result2 = parser.parse(",\"\"");

        assertTrue(result2.isOk());
        Record record2 = result2.getValue();
        assertEquals(2, record2.size());
        assertEquals("", record2.get("foo"));
        assertEquals("", record2.get("bar"));
    }

    @Test
    public void adjacentDelimitersOptionallyFramed() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"),
                                             Column.with("baz", "string"));

        DelimitedFormat format = DelimitedFormat.optionallyFramed("baz", "Baz", ',', '"', columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("\"\",,\"\"");

        assertTrue(result.isOk());
        Record record1 = result.getValue();
        assertEquals(3, record1.size());
        assertEquals("", record1.get("foo"));
        assertEquals("", record1.get("bar"));
        assertEquals("", record1.get("baz"));
    }


    @Test
    public void filler() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("filler", "filler"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result1 = parser.parse("\"baz\",\"burp\"");
        assertTrue(result1.isOk());
        Record record1 = result1.getValue();
        assertEquals(1, record1.size());
        assertEquals("baz", record1.get("foo"));

        ParseResult result2 = parser.parse("\"bip\",\"bop\"");
        assertTrue(result2.isOk());
        Record record2 = result2.getValue();
        assertEquals(1, record2.size());
        assertEquals("bip", record2.get("foo"));
    }

    @Test
    public void badFrameAtStart() {
        List<Column> columns = Collections.singletonList(Column.with("foo", "string"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("baz");

        assertTrue(result.hasProblems());
        List<Problem> problems = result.getProblems();
        assertEquals(1, problems.size());
        assertEquals("Column 1 was not properly framed (at character 1).",
                     problems.get(0).explain());
    }

    @Test
    public void badFrameAtDelimiter() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("\"baz\"\"bip\"");

        assertTrue(result.hasProblems());
        List<Problem> problems = result.getProblems();
        assertEquals(1, problems.size());
        assertEquals("Column 2 was not properly framed (at character 6).",
                     problems.get(0).explain());
    }

    @Test
    public void unframedMissingTwoColumns() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"),
                                             Column.with("baz", "string"),
                                             Column.with("burp", "integer"));

        DelimitedFormat format = DelimitedFormat.unframed("bop", "Bop", ',', columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("baz,burp");

        assertTrue(result.hasProblems());

        List<Problem> problems = result.getProblems();
        assertEquals(2, problems.size());
        assertEquals("Missing required value for field \"burp\" of type \"integer\".",
                     problems.get(1).explain());
        assertEquals("Missing required value for field \"baz\" of type \"string\".",
                     problems.get(0).explain());
    }

    @Test
    public void framedMissingTwoColumns() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"),
                                             Column.with("baz", "string"),
                                             Column.with("burp", "integer"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("bop", "Bop", ',', '"', columns);
        DelimitedParser parser = new DelimitedParser(format);


        ParseResult result = parser.parse("\"baz\",\"burp\"");

        assertTrue(result.hasProblems());

        List<Problem> problems = result.getProblems();
        assertEquals(2, problems.size());
        assertEquals("Missing required value for field \"burp\" of type \"integer\".",
                     problems.get(1).explain());
        assertEquals("Missing required value for field \"baz\" of type \"string\".",
                     problems.get(0).explain());
    }

    @Test
    public void withOffset() {
        List<Column> columns = Collections.singletonList(Column.with("foo", "string"));

        DelimitedFormat format = DelimitedFormat.optionallyFramed("bop", "Bop", ',', '"', columns)
                .withOffset(1);

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("baz,burp");

        assertTrue(result.isOk());

        Record record = result.getValue();

        assertEquals(1, record.size());
        assertEquals("burp", record.get("foo"));
    }

    @Test
    public void delimitersInFramedWhenFramingIsRequired() {
        List<Column> columns = Arrays.asList(Column.bigDecimal("foo"),
                                             Column.bigDecimal("bar"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("bop",
                                                              "Bop.",
                                                              ',',
                                                              '"',
                                                              columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = format.parse("\"1,000\",\"1,000\"");

        assertTrue(result.explain(), result.isOk());
    }

    @Test
    public void delimitersInFramedWhenFramingIsOptional() {
        List<Column> columns = Arrays.asList(Column.bigDecimal("foo"),
                                             Column.bigDecimal("bar"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("bop",
                                                              "Bop.",
                                                              ',',
                                                              '"',
                                                              columns);
        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = format.parse("\"1,000\",\"1,000\"");

        assertTrue(result.explain(), result.isOk());
    }

    @Test
    public void empty() {
        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.builder()
                .withId("baz")
                .withDescription("Baz")
                .withDelimiter(',')
                .alwaysFramed('"')
                .withColumns(columns)
                .build();

        DelimitedParser parser = new DelimitedParser(format);

        ParseResult result = parser.parse("");

        assertTrue(result.hasProblems());

        assertEquals(1, result.getProblems().size());

        assertEquals(new EmptyLineProblem(), result.getProblems().get(0));
    }
}
