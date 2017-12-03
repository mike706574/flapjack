package fun.mike.flapjack.alpha;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class SerializationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fixedWidth() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Field> fields = Arrays.asList(Field.with("foo", 1, 5, "string"),
                Field.with("bar", 6, 10, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", 10, fields);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        FixedWidthFormat deserializedFormat = mapper.readValue(serializedFormat, FixedWidthFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void fixedWidthNoLength() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Field> fields = Arrays.asList(Field.with("foo", 1, 5, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", null, fields);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        FixedWidthFormat deserializedFormat = mapper.readValue(serializedFormat, FixedWidthFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void fixedWidthNoProps() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Field> fields = Arrays.asList(new Field("foo", 1, 5, "string", null));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", 10, fields);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        FixedWidthFormat deserializedFormat = mapper.readValue(serializedFormat, FixedWidthFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void unframedDelimited() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ",", columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        DelimitedFormat deserializedFormat = mapper.readValue(serializedFormat, DelimitedFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void framedDelimited() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.framed("baz", "Baz", ",", "\"", columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        DelimitedFormat deserializedFormat = mapper.readValue(serializedFormat, DelimitedFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void delimitedNoProps() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Column> columns = Arrays.asList(new Column("foo", "string", null));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ",", columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        DelimitedFormat deserializedFormat = mapper.readValue(serializedFormat, DelimitedFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }
}
