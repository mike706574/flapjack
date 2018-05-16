package fun.mike.flapjack.alpha;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FixedWidthFormatTest {
    @Test
    public void builder() {
        FixedWidthFormat format;
        List<Field> fields;

        format = FixedWidthFormat.builder()
                .withId("id")
                .withDescription("desc")
                .addField(Field.string("x", 5))
                .addField(Field.integer("y", 6))
                .skipFirst(1)
                .skipLast(1)
                .build();

        assertEquals("id", format.getId());
        assertEquals("desc", format.getDescription());

        fields = format.getFields();
        assertEquals(2, fields.size());
        assertEquals(Field.string("x", 5), fields.get(0));
        assertEquals(Field.integer("y", 6), fields.get(1));

        assertEquals(1, format.getSkipFirst());
        assertEquals(1, format.getSkipLast());
    }
}
