package fun.mike.flapjack.beta;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import fun.mike.record.Record;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReaderToListPipelineTest {

    private static final Format inputFormat =
            DelimitedFormat.unframed("delimited-animals",
                                     "Delimited animals format.",
                                     ',',
                                     Arrays.asList(Column.string("name"),
                                                   Column.integer("legs"),
                                                   Column.string("size")));

    @Test
    public void success() {
        InputStream stream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("pipeline/animals.csv");

        Reader reader = new InputStreamReader(stream);

        ListPipeline pipeline = Pipeline.fromFlatReader(reader, inputFormat)
                .includeLineAs("message")
                .map(x -> x.updateString("size", String::toUpperCase))
                .filter(x -> x.getString("size").equals("MEDIUM"))
                .toList();

        PipelineResult<List<Record>> result = pipeline.run();

        assertTrue(result.isOk());
        assertEquals(6, result.getInputCount());
        assertEquals(3, result.getOutputCount());

        List<Record> values = result.orElseThrow();

        assertEquals(3, values.size());

        assertEquals(Record.of("name", "dog",
                               "legs", 4,
                               "size", "MEDIUM",
                               "message", "dog,4,medium"),
                     values.get(0));

        assertEquals(Record.of("name", "fox",
                               "legs", 4,
                               "size", "MEDIUM",
                               "message", "fox,4,medium"),
                     values.get(1));

        assertEquals(Record.of("name", "ostrich",
                               "legs", 2,
                               "size", "MEDIUM",
                               "message", "ostrich,2,medium"),
                     values.get(2));
    }
}
