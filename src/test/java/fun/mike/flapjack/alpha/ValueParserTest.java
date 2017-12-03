package fun.mike.flapjack.alpha;

import java.util.Date;
import java.util.Map;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static fun.mike.map.alpha.Factory.mapOf;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class ValueParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void string() {
        ObjectOrProblem result = ValueParser.parse("foo", "string", null, "bar");
        assertFalse(result.hasProblem());
        assertEquals("bar", result.getObject());
    }

    @Test
    public void trimmedString() {
        ObjectOrProblem alreadyTrimmedResult = ValueParser.parse("foo", "trimmed-string", null, "bar");
        assertFalse(alreadyTrimmedResult.hasProblem());
        assertEquals("bar", alreadyTrimmedResult.getObject());

        ObjectOrProblem untrimmedResult = ValueParser.parse("foo", "trimmed-string", null, "  bar  ");
        assertFalse(untrimmedResult.hasProblem());
        assertEquals("bar", untrimmedResult.getObject());
    }

    @Test
    public void integer() {
        ObjectOrProblem validIntegerResult = ValueParser.parse("foo", "integer", null, "5");
        assertFalse(validIntegerResult.hasProblem());
        assertEquals(5, validIntegerResult.getObject());

        ObjectOrProblem notAnIntegerResult = ValueParser.parse("foo", "integer", null, "bar");
        assertTrue(notAnIntegerResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"integer\".",
                     notAnIntegerResult.getProblem().explain());
    }

    @Test
    public void formattedDate() {
        String format = "yyyyMMdd";
        Map<String, Object> props = mapOf("format", format);

        String unformattedDate = "19950215";
        ObjectOrProblem validDateResult = ValueParser.parse("foo", "formatted-date", props, unformattedDate);
        assertFalse(validDateResult.hasProblem());

        Date expectedDate = parseDate(format, unformattedDate);
        assertEquals(expectedDate, validDateResult.getObject());

        ObjectOrProblem invalidDateResult = ValueParser.parse("foo", "formatted-date", props, "bar");
        assertTrue(invalidDateResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"formatted-date\".",
                     invalidDateResult.getProblem().explain());
    }

    private Date parseDate(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        }
        catch(ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
