package fun.mike.flapjack.beta;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FlatReaderInputChannelTest {
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
        Reader reader = resourceReader("pipeline/animals-just-header.csv");

        FlatReaderInputChannel chan = new FlatReaderInputChannel(reader,
                                                                 format,
                                                                 "line",
                                                                 true);
        assertFalse(chan.hasMore());
    }

    @Test
    public void empty() {
        Reader reader = resourceReader("pipeline/empty.csv");

        FlatReaderInputChannel chan = new FlatReaderInputChannel(reader,
                                                                 format,
                                                                 "line",
                                                                 true);
        assertFalse(chan.hasMore());
    }

    @Test
    public void works() throws IOException {
        Format format =
                DelimitedFormat.builder()
                        .withId("delimited-animals")
                        .withDescription("Delimited animals format.")
                        .withDelimiter(',')
                        .unframed()
                        .withColumns(Arrays.asList(Column.string("name"),
                                                   Column.integer("legs"),
                                                   Column.string("size")))
                        .build();

        Reader reader = resourceReader("pipeline/animals.csv");

        FlatReaderInputChannel chan = new FlatReaderInputChannel(reader,
                                                                 format,
                                                                 "line",
                                                                 true);

        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertNotNull(chan.take());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertNotNull(chan.take());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertNotNull(chan.take());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertNotNull(chan.take());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertNotNull(chan.take());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertTrue(chan.hasMore());
        assertNotNull(chan.take());
        assertFalse(chan.hasMore());
    }

    private Reader resourceReader(String path) {
        InputStream stream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(path);

        return new InputStreamReader(stream);
    }
}
