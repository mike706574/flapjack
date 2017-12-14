package fun.mike.flapjack.alpha;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static fun.mike.map.alpha.Factory.mapOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class ValueParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void string() {
        ValueOrProblem result = ValueParser.parse("foo", "string", null, "bar");
        assertFalse(result.explain(), result.hasProblem());
        assertEquals("bar", result.getValue());
    }

    @Test
    public void trimmedString() {
        ValueOrProblem alreadyTrimmedResult = ValueParser.parse("foo",
                "trimmed-string",
                mapOf(),
                "bar");
        assertFalse(alreadyTrimmedResult.explain(), alreadyTrimmedResult.hasProblem());
        assertEquals("bar", alreadyTrimmedResult.getValue());

        ValueOrProblem untrimmedResult = ValueParser.parse("foo", "trimmed-string", null, "  bar  ");
        assertFalse(untrimmedResult.explain(), untrimmedResult.hasProblem());
        assertEquals("bar", untrimmedResult.getValue());
    }

    @Test
    public void integer() {
        ValueOrProblem validResult = ValueParser.parse("foo",
                "integer",
                mapOf(),
                "5");
        assertFalse(validResult.explain(),
                validResult.hasProblem());
        assertEquals(5, validResult.getValue());

        ValueOrProblem invalidResult = ValueParser.parse("foo", "integer", mapOf(), "bar");
        assertTrue(invalidResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"integer\".",
                invalidResult.getProblem().explain());
    }

    @Test
    public void optionalInteger() {
        Map<String, Object> props = mapOf("default", 0);
        ValueOrProblem validResult = ValueParser.parse("foo", "integer", props, " ");
        assertFalse(validResult.explain(), validResult.hasProblem());
        assertEquals(0, validResult.getValue());
    }

    @Test
    public void bigDecimal() {
        String validValue = "5.20932021";
        ValueOrProblem validResult = ValueParser.parse("foo", "big-decimal", mapOf(), validValue);
        assertFalse(validResult.explain(), validResult.hasProblem());

        BigDecimal expectedValue = new BigDecimal(validValue);
        assertEquals(expectedValue, validResult.getValue());

        ValueOrProblem problemResult = ValueParser.parse("foo", "big-decimal", mapOf(), "bar");
        assertTrue(problemResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"big-decimal\".",
                problemResult.getProblem().explain());
    }

    @Test
    public void parsingDoubles() {
        String validValue = "5.20932021";
        ValueOrProblem validResult = ValueParser.parse("foo", "double", mapOf(), validValue);
        assertFalse(validResult.explain(), validResult.hasProblem());

        Double expectedValue = new Double(validValue);
        assertEquals(expectedValue, validResult.getValue());

        ValueOrProblem problemResult = ValueParser.parse("foo", "double", mapOf(), "bar");
        assertTrue(problemResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"double\".",
                problemResult.getProblem().explain());
    }

    @Test
    public void untrimmedBigDecimal() {
        ValueOrProblem result = ValueParser.parse("foo",
                "big-decimal",
                mapOf(),
                " 5.0");
        assertFalse(result.explain(), result.hasProblem());

        BigDecimal expectedValue = new BigDecimal(5.0);
        assertEquals(0, expectedValue.compareTo((BigDecimal) result.getValue()));
    }

    @Test
    public void optionalBigDecimal() {
        Map<String, Object> props = mapOf("default", new BigDecimal(2.5));
        ValueOrProblem result = ValueParser.parse("foo",
                "big-decimal",
                props,
                " ");
        assertFalse(result.explain(), result.hasProblem());

        BigDecimal expectedValue = new BigDecimal(2.5);
        assertEquals(expectedValue, result.getValue());
    }

    @Test
    public void stringEnum() {
        List<String> options = Arrays.asList("apple", "banana", "carrot");
        Map<String, Object> props = mapOf("options", options);

        ValueOrProblem appleResult = ValueParser.parse("foo", "string-enum", props, "apple");
        assertFalse(appleResult.explain(), appleResult.hasProblem());
        assertEquals("apple", appleResult.getValue());

        ValueOrProblem bananaResult = ValueParser.parse("foo", "string-enum", props, "banana");
        assertFalse(bananaResult.explain(), bananaResult.hasProblem());
        assertEquals("banana", bananaResult.getValue());

        ValueOrProblem carrotResult = ValueParser.parse("foo", "string-enum", props, "carrot");
        assertFalse(carrotResult.explain(), carrotResult.hasProblem());
        assertEquals("carrot", carrotResult.getValue());

        ValueOrProblem orangeResult = ValueParser.parse("foo", "string-enum", props, "orange");
        assertTrue(orangeResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"orange\" must be one of the following 3 string options: \"apple\", \"banana\", \"carrot\"",
                orangeResult.getProblem().explain());
    }

    @Test
    public void formattedDate() {
        String format = "yyyyMMdd";
        Map<String, Object> props = mapOf("format", format);

        String unformattedDate = "19950215";
        ValueOrProblem validResult = ValueParser.parse("foo", "date", props, unformattedDate);
        assertFalse(validResult.hasProblem());

        Date expectedDate = parseDate(format, unformattedDate);
        assertEquals(expectedDate, validResult.getValue());

        ValueOrProblem invalidResult = ValueParser.parse("foo", "date", props, "bar");
        assertTrue(invalidResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"date\".",
                invalidResult.getProblem().explain());
    }

    @Test
    public void optionalFormattedDate() {
        String format = "yyyyMMdd";
        Map<String, Object> props = mapOf("format", format,
                "optional", true);

        ValueOrProblem result = ValueParser.parse("foo", "date", props, "        ");
        assertFalse(result.hasProblem());
        assertNull(result.getValue());
    }

    private Date parseDate(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
