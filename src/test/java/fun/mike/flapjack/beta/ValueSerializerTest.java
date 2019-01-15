package fun.mike.flapjack.beta;

import java.math.BigDecimal;

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
}
