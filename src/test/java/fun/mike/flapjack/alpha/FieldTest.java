package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static fun.mike.map.alpha.Factory.mapOf;

public class FieldTest {
    @Test
    public void building() {
        Field col = null;

        col = Field.with("foo", 5, "string");
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("string", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Field.with("foo", 5, "string", mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("string", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Field.string("foo", 5);
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("string", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Field.string("foo", 5, mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("string", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Field.trimmedString("foo", 5);
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("trimmed-string", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Field.trimmedString("foo", 5, mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("trimmed-string", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Field.integer("foo", 5);
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("integer", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Field.integer("foo", 5, mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("integer", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Field.bigDecimal("foo", 5);
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("big-decimal", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Field.bigDecimal("foo", 5, mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("big-decimal", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Field.date("foo", 5, "yyyyMMdd");
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals(5, col.getLength());
        assertEquals("date", col.getType());
        assertEquals(mapOf("format", "yyyyMMdd"), col.getProps());

        col = Field.optionalDate("foo", 5, "yyyyMMdd");
        assertEquals("foo", col.getId());
        assertEquals(5, col.getLength());
        assertEquals("date", col.getType());
        assertEquals(mapOf("format", "yyyyMMdd",
                           "optional", true), col.getProps());
    }
}
