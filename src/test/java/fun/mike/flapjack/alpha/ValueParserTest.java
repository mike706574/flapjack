package fun.mike.flapjack.alpha;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static fun.mike.map.alpha.Factory.mapOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ValueParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void string() {
        ObjectOrProblem result = ValueParser.parse("foo", "string", null, "bar");
        assertFalse(result.explain(), result.hasProblem());
        assertEquals("bar", result.getObject());
    }

    @Test
    public void trimmedString() {
        ObjectOrProblem alreadyTrimmedResult = ValueParser.parse("foo", "trimmed-string", null, "bar");
        assertFalse(alreadyTrimmedResult.explain(), alreadyTrimmedResult.hasProblem());
        assertEquals("bar", alreadyTrimmedResult.getObject());

        ObjectOrProblem untrimmedResult = ValueParser.parse("foo", "trimmed-string", null, "  bar  ");
        assertFalse(untrimmedResult.explain(), untrimmedResult.hasProblem());
        assertEquals("bar", untrimmedResult.getObject());
    }

    @Test
    public void integer() {
        ObjectOrProblem validResult = ValueParser.parse("foo", "integer", null, "5");
        assertFalse(validResult.explain(),
                validResult.hasProblem());
        assertEquals(5, validResult.getObject());

        ObjectOrProblem invalidResult = ValueParser.parse("foo", "integer", null, "bar");
        assertTrue(invalidResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"integer\".",
                invalidResult.getProblem().explain());
    }

    @Test
    public void bigDecimal() {
        String validValue = "5.20932021";
        ObjectOrProblem validResult = ValueParser.parse("foo", "big-decimal", null, validValue);
        assertFalse(validResult.explain(), validResult.hasProblem());

        BigDecimal expectedValue = new BigDecimal(validValue);
        assertEquals(expectedValue, validResult.getObject());

        ObjectOrProblem problemResult = ValueParser.parse("foo", "big-decimal", null, "bar");
        assertTrue(problemResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"big-decimal\".",
                problemResult.getProblem().explain());
    }

    @Test
    public void formattedDate() {
        String format = "yyyyMMdd";
        Map<String, Object> props = mapOf("format", format);

        String unformattedDate = "19950215";
        ObjectOrProblem validResult = ValueParser.parse("foo", "formatted-date", props, unformattedDate);
        assertFalse(validResult.hasProblem());

        Date expectedDate = parseDate(format, unformattedDate);
        assertEquals(expectedDate, validResult.getObject());

        ObjectOrProblem invalidResult = ValueParser.parse("foo", "formatted-date", props, "bar");
        assertTrue(invalidResult.hasProblem());
        assertEquals("Expected field \"foo\" with value \"bar\" to be a \"formatted-date\".",
                invalidResult.getProblem().explain());
    }

    private Date parseDate(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
