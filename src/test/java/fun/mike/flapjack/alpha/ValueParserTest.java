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
    public void nullableInteger() {
        ValueOrProblem result = ValueParser.parse("foo",
                                                            "integer",
                                                            mapOf("nullable", true),
                                                            "");
        assertFalse(result.explain(), result.hasProblem());
        assertNull(result.getValue());
    }


    @Test
    public void integerNullableIsFalse() {
        ValueOrProblem result = ValueParser.parse("foo",
                                                  "integer",
                                                  mapOf("nullable", false),
                                                  "");
        assertTrue(result.hasProblem());
        assertEquals(new MissingValueProblem("foo", "integer"),
                     result.getProblem());
    }

    @Test
    public void stringNullableIsFalse() {
        ValueOrProblem result = ValueParser.parse("foo",
                                                  "string",
                                                  mapOf("nullable", false),
                                                  "");
        assertFalse(result.hasProblem());
        assertEquals("", result.getValue());
    }

    @Test
    public void nullableString() {
        ValueOrProblem whitespaceResult = ValueParser.parse("foo",
                                                            "string",
                                                            mapOf("nullable", true),
                                                            " ");
        assertFalse(whitespaceResult.explain(), whitespaceResult.hasProblem());
        assertNull(whitespaceResult.getValue());

        ValueOrProblem blankResult = ValueParser.parse("foo",
                                                       "string",
                                                       mapOf("nullable", true),
                                                       "");
        assertFalse(blankResult.explain(), blankResult.hasProblem());
        assertNull(blankResult.getValue());

        ValueOrProblem populatedResult = ValueParser.parse("foo",
                                                           "string",
                                                           mapOf("nullable", true),
                                                           " bar ");
        assertFalse(populatedResult.explain(), populatedResult.hasProblem());
        assertEquals(" bar ", populatedResult.getValue());
    }

    @Test
    public void nullableTrimmedString() {
        ValueOrProblem whitespaceResult = ValueParser.parse("foo",
                                                            "trimmed-string",
                                                            mapOf("nullable", true),
                                                            " ");
        assertFalse(whitespaceResult.explain(), whitespaceResult.hasProblem());
        assertNull(whitespaceResult.getValue());

        ValueOrProblem blankResult = ValueParser.parse("foo",
                                                       "trimmed-string",
                                                       mapOf("nullable", true),
                                                       "");
        assertFalse(blankResult.explain(), blankResult.hasProblem());
        assertNull(blankResult.getValue());

        ValueOrProblem populatedResult = ValueParser.parse("foo",
                                                           "trimmed-string",
                                                           mapOf("nullable", true),
                                                           "bar");
        assertFalse(populatedResult.explain(), populatedResult.hasProblem());
        assertEquals("bar", populatedResult.getValue());
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
        assertEquals(new TypeProblem("foo", "integer", "bar"),
                     invalidResult.getProblem());
    }

    @Test
    public void missingInteger() {
        ValueOrProblem result = ValueParser.parse("foo",
                                                       "integer",
                                                       mapOf(),
                                                       "");

        assertTrue(result.hasProblem());
        assertEquals(new MissingValueProblem("foo", "integer"),
                     result.getProblem());
    }

    @Test
    public void integerWithCommas() {
        ValueOrProblem result = ValueParser.parse("foo",
                                                  "integer",
                                                  mapOf(),
                                                  "1,000,000");
        assertFalse(result.explain(),
                    result.hasProblem());
        assertEquals(1000000, result.getValue());
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
        assertEquals(new TypeProblem("foo", "big-decimal", "bar"),
                     problemResult.getProblem());
    }

    @Test
    public void bigDecimalWithCommas() {
        ValueOrProblem result = ValueParser.parse("foo",
                                                  "big-decimal",
                                                  mapOf(),
                                                  "1,000,000.0");
        assertFalse(result.explain(),
                    result.hasProblem());
        assertEquals(new BigDecimal("1000000.0"), result.getValue());
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
    public void parsingDoubles() {
        String validValue = "5.20932021";
        ValueOrProblem validResult = ValueParser.parse("foo", "double", mapOf(), validValue);
        assertFalse(validResult.explain(), validResult.hasProblem());

        Double expectedValue = new Double(validValue);
        assertEquals(expectedValue, validResult.getValue());

        ValueOrProblem problemResult = ValueParser.parse("foo", "double", mapOf(), "bar");
        assertTrue(problemResult.hasProblem());
        assertEquals(new TypeProblem("foo", "double", "bar"),
                     problemResult.getProblem());
    }

    @Test
    public void doubleWithCommas() {
        ValueOrProblem result = ValueParser.parse("foo",
                                                  "double",
                                                  mapOf(),
                                                  "1,000,000.0");
        assertFalse(result.explain(),
                    result.hasProblem());
        assertEquals(new Double("1000000.0"), result.getValue());
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
        assertEquals(new StringEnumProblem("foo", "orange", options),
                     orangeResult.getProblem());
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
        assertEquals(new TypeProblem("foo", "date", "bar"),
                     invalidResult.getProblem());
    }

    @Test
    public void nullableFormattedDate() {
        String format = "yyyyMMdd";
        Map<String, Object> props = mapOf("format", format,
                                          "nullable", true);

        ValueOrProblem result = ValueParser.parse("foo", "date", props, "        ");
        assertFalse(result.hasProblem());
        assertNull(result.getValue());
    }

    @Test
    public void missingDateFormat() {
        Map<String, Object> props = mapOf();

        ValueOrProblem result = ValueParser.parse("foo", "date", props, "01012025");
        assertTrue(result.hasProblem());
        assertEquals(new FormatProblem("Property \"format\" is required for date field \"foo\"."),
                     result.getProblem());
    }

    @Test
    public void wrongDateFormatType() {
        Map<String, Object> props = mapOf("format", 5);

        ValueOrProblem result = ValueParser.parse("foo", "date", props, "01012025");
        assertTrue(result.hasProblem());
        assertEquals(new FormatProblem("Property \"format\" for date field \"foo\" must be a string - got value \"5\" of type \"java.lang.Integer\"."),
                     result.getProblem());
    }

    @Test
    public void wrongNullableType() {
        Map<String, Object> props = mapOf("nullable", 5);
        ValueOrProblem result = ValueParser.parse("foo", "string", props, "");
        assertTrue(result.hasProblem());
        assertEquals(new FormatProblem("Property \"nullable\" for field \"foo\" must be a boolean - got value \"5\" of type \"class java.lang.Integer\"."),
                     result.getProblem());
    }

    @Test
    public void wrongDefaultType() {
        Map<String, Object> props = mapOf("default", "5",
                                          "nullable", true);
        ValueOrProblem result = ValueParser.parse("foo", "integer", props, "");
        assertTrue(result.hasProblem());
        assertEquals(new FormatProblem("Default value for field \"foo\" must be an integer - got value \"5\" of type \"java.lang.String\"."),
                     result.getProblem());
    }

    @Test
    public void stringEnumNoOptions() {
        Map<String, Object> props = mapOf();
        ValueOrProblem result = ValueParser.parse("foo", "string-enum", props, "X");
        assertTrue(result.hasProblem());
        assertEquals(new FormatProblem("Property \"options\" is required for string enumeration field \"foo\"."),
                     result.getProblem());
    }

    @Test
    public void stringEnumWrongOptionsType() {
        Map<String, Object> props = mapOf("options", 5);
        ValueOrProblem result = ValueParser.parse("foo", "string-enum", props, "X");
        assertTrue(result.hasProblem());
        assertEquals(new FormatProblem("Property \"options\" for string enumeration field \"foo\" must be a list of strings - got value \"5\" of type \"java.lang.Integer\"."),
                     result.getProblem());
    }

    @Test
    public void noSuchType() {
        ValueOrProblem result = ValueParser.parse("foo", "zoogaloo", mapOf(), "");
        assertTrue(result.hasProblem());
        assertEquals(new NoSuchTypeProblem("foo", "zoogaloo"), result.getProblem());
    }

    private Date parseDate(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
