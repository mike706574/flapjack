package fun.mike.flapjack.alpha;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DelimitedFormatTest {
    @Test
    public void builder() {
        DelimitedFormat format;

        List<Column> columns;

        format = DelimitedFormat.builder()
                .withId("id")
                .withDescription("desc")
                .withDelimiter(',')
                .unframed()
                .addColumn(Column.string("x"))
                .addColumn(Column.integer("y"))
                .skipFirst(1)
                .build();

        assertEquals("id", format.getId());
        assertEquals("desc", format.getDescription());
        assertEquals(new Character(','), format.getDelimiter());
        assertEquals(Framing.NONE, format.getFraming());

        columns = format.getColumns();
        assertEquals(2, columns.size());
        assertEquals(Column.string("x"), columns.get(0));
        assertEquals(Column.integer("y"), columns.get(1));

        assertEquals(1, format.getSkipFirst());
        assertEquals(0, format.getSkipLast());
        assertEquals(Optional.empty(), format.getFrameDelimiter());
        assertFalse(format.hasHeader());
        assertFalse(format.hasEndingDelimiter());
        assertEquals(0, format.getOffset());
        assertEquals(Optional.empty(), format.getFrameDelimiter());
    }
}
