package fun.mike.flapjack.beta;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import fun.mike.record.Record;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static fun.mike.map.alpha.Factory.mapOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ValueSerializerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void string() {
        ValueOrProblem<String> result = ValueSerializer.serializeValue("foo",
                                                                       "string",
                                                                       mapOf(),
                                                                       Record.of("foo", "a"));

        assertTrue(result.isOk());
        assertEquals("a", result.getValue());
    }

    @Test
    public void integer() {
        ValueOrProblem<String> result = ValueSerializer.serializeValue("foo",
                                                                       "integer",
                                                                       mapOf(),
                                                                       Record.of("foo", 1));

        assertTrue(result.isOk());
        assertEquals("1", result.getValue());
    }

    @Test
    public void invalidInteger() {
        ValueOrProblem<String> result = ValueSerializer.serializeValue("foo",
                                                                       "integer",
                                                                       mapOf(),
                                                                       Record.of("foo", "a"));

        assertTrue(result.hasProblem());
        assertEquals(new TypeProblem("foo", "integer", "a"),
                     result.getProblem());
    }

    @Test
    public void bigDecimal() {
        ValueOrProblem<String> result = ValueSerializer.serializeValue("foo",
                                                                       "big-decimal",
                                                                       mapOf(),
                                                                       Record.of("foo", new BigDecimal(1.1111)));
        assertTrue(result.isOk());
        assertEquals("1.1111", result.getValue());
    }

    @Test
    public void doubles() {
        ValueOrProblem<String> result = ValueSerializer.serializeValue("foo",
                                                                       "double",
                                                                       mapOf(),
                                                                       Record.of("foo", 2.5d));
        assertTrue(result.isOk());
        assertEquals("2.5000", result.getValue());
    }

    @Test
    public void date() {
        ValueOrProblem<String> result = ValueSerializer
                .serializeValue("foo",
                                "date",
                                mapOf("format", "yyyyMMdd"),
                                Record.of("foo", parseDate("yyyyMMdd", "20250105")));

        assertTrue(result.isOk());
        assertEquals("20250105", result.getValue());
    }

    @Test
    public void localDate() {
        ValueOrProblem<String> result = ValueSerializer
                .serializeValue("foo",
                                "local-date",
                                mapOf("format", "yyyyMMdd"),
                                Record.of("foo", LocalDate.of(2025, 1, 5)));

        assertTrue(result.isOk());
        assertEquals("20250105", result.getValue());
    }

    @Test
    public void localDateTime() {
        ValueOrProblem<String> result = ValueSerializer
                .serializeValue("foo",
                                "local-date-time",
                                mapOf("format", "yyyy-MM-dd HH:mm:ss"),
                                Record.of("foo", LocalDateTime.of(2025, 1, 5, 5, 12, 56)));

        assertTrue(result.isOk());
        assertEquals("2025-01-05 05:12:56", result.getValue());
    }

    private Date parseDate(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
