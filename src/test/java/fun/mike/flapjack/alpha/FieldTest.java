package fun.mike.flapjack.alpha;

import java.util.Collections;

import org.junit.Test;

import static fun.mike.map.alpha.Factory.mapOf;
import static org.junit.Assert.assertEquals;

public class FieldTest {
    @Test
    public void building() {
        Field field;

        field = Field.with("foo", 5, "string");
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("string", field.getType());
        assertEquals(Collections.emptyMap(), field.getProps());

        field = Field.with("foo", 5, "string", mapOf("nullable", true));
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("string", field.getType());
        assertEquals(mapOf("nullable", true), field.getProps());

        field = Field.string("foo", 5);
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("string", field.getType());
        assertEquals(Collections.emptyMap(), field.getProps());

        field = Field.string("foo", 5, mapOf("nullable", true));
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("string", field.getType());
        assertEquals(mapOf("nullable", true), field.getProps());

        field = Field.trimmedString("foo", 5);
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("trimmed-string", field.getType());
        assertEquals(Collections.emptyMap(), field.getProps());

        field = Field.trimmedString("foo", 5, mapOf("nullable", true));
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("trimmed-string", field.getType());
        assertEquals(mapOf("nullable", true), field.getProps());

        field = Field.integer("foo", 5);
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("integer", field.getType());
        assertEquals(Collections.emptyMap(), field.getProps());

        field = Field.integer("foo", 5, mapOf("nullable", true));
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("integer", field.getType());
        assertEquals(mapOf("nullable", true), field.getProps());

        field = Field.bigDecimal("foo", 5);
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("big-decimal", field.getType());
        assertEquals(Collections.emptyMap(), field.getProps());

        field = Field.bigDecimal("foo", 5, mapOf("nullable", true));
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("big-decimal", field.getType());
        assertEquals(mapOf("nullable", true), field.getProps());

        field = Field.date("foo", 5, "yyyyMMdd");
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals(5, field.getLength());
        assertEquals("date", field.getType());
        assertEquals(mapOf("format", "yyyyMMdd"), field.getProps());

        field = Field.optionalDate("foo", 5, "yyyyMMdd");
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("date", field.getType());
        assertEquals(mapOf("format", "yyyyMMdd",
                           "optional", true), field.getProps());

        field = Field.string("foo", 5);
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("string", field.getType());
        assertEquals(Collections.emptyMap(), field.getProps());
        field = field.nullable();
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("string", field.getType());
        assertEquals(mapOf("nullable", true), field.getProps());
        field = field.optional();
        assertEquals("foo", field.getId());
        assertEquals(5, field.getLength());
        assertEquals("string", field.getType());
        assertEquals(mapOf("nullable", true,
                           "optional", true),
                     field.getProps());
    }
}
