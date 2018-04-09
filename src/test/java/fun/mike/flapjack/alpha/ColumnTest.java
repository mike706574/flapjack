package fun.mike.flapjack.alpha;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static fun.mike.map.alpha.Factory.mapOf;

public class ColumnTest {
    @Test
    public void building() {
        Column col = null;

        col = Column.with("foo", "string");
        assertEquals("foo", col.getId());
        assertEquals("string", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Column.with("foo", "string", mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals("string", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Column.string("foo");
        assertEquals("foo", col.getId());
        assertEquals("string", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Column.string("foo", mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals("string", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Column.trimmedString("foo");
        assertEquals("foo", col.getId());
        assertEquals("trimmed-string", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Column.trimmedString("foo", mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals("trimmed-string", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Column.integer("foo");
        assertEquals("foo", col.getId());
        assertEquals("integer", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Column.integer("foo", mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals("integer", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Column.bigDecimal("foo");
        assertEquals("foo", col.getId());
        assertEquals("big-decimal", col.getType());
        assertEquals(Collections.emptyMap(), col.getProps());

        col = Column.bigDecimal("foo", mapOf("nullable", true));
        assertEquals("foo", col.getId());
        assertEquals("big-decimal", col.getType());
        assertEquals(mapOf("nullable", true), col.getProps());

        col = Column.date("foo", "yyyyMMdd");
        assertEquals("foo", col.getId());
        assertEquals("date", col.getType());
        assertEquals(mapOf("format", "yyyyMMdd"), col.getProps());

        col = Column.optionalDate("foo", "yyyyMMdd");
        assertEquals("foo", col.getId());
        assertEquals("date", col.getType());
        assertEquals(mapOf("format", "yyyyMMdd",
                           "optional", true), col.getProps());
    }
}
