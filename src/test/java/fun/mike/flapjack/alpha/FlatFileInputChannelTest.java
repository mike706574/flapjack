package fun.mike.flapjack.alpha;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class FlatFileInputChannelTest {
    private static final String base = "src/test/resources/pipeline/";

    private static final Format format =
            DelimitedFormat.builder()
                    .withId("delimited-animals")
                    .withDescription("Delimited animals format.")
                    .withDelimiter(',')
                    .unframed()
                    .withColumns(Arrays.asList(Column.string("name"),
                                               Column.integer("legs"),
                                               Column.string("size")))
                    .skipFirst(1)
                    .build();


    @Test
    public void justHeader() {
        FlatFileInputChannel chan = new FlatFileInputChannel(base + "animals-just-header.csv",
                                                             format,
                                                             "line",
                                                             true);
        assertFalse(chan.hasMore());
    }

    @Test
    public void empty() {
        FlatFileInputChannel chan = new FlatFileInputChannel(base + "empty.csv",
                                                             format,
                                                             "line",
                                                             true);
        assertFalse(chan.hasMore());
    }
}
