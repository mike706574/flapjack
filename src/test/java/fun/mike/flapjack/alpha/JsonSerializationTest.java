package fun.mike.flapjack.alpha;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import fun.mike.record.alpha.Record;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class JsonSerializationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fixedWidth() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("bar", 5, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

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

        List<Field> fields = Collections.singletonList(new Field("foo", 5, "string", null));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

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

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);

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

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);

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

        List<Column> columns = Collections.singletonList(new Column("foo", "string", null));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        DelimitedFormat deserializedFormat = mapper.readValue(serializedFormat, DelimitedFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void typeProblem() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        TypeProblem problem = new TypeProblem("foo", "string", "bar");

        String serialized = mapper.writeValueAsString(problem);
        // System.out.println(serialized);
        assertEquals("{\"type\":\"type\",\"id\":\"foo\",\"type\":\"string\",\"value\":\"bar\"}",
                     serialized);
        TypeProblem deserialized = mapper.readValue(serialized, TypeProblem.class);
        String reserialized = mapper.writeValueAsString(deserialized);
        assertEquals(serialized, reserialized);
    }

    @Test
    public void parseResult() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        ParseResult result = ParseResult.ok(Record.of("foo", "bar"), "foobar");
        String serialized = mapper.writeValueAsString(result);
        assertEquals("{\"value\":{\"foo\":\"bar\"},\"line\":\"foobar\",\"problems\":[]}",
                     serialized);
        ParseResult deserialized = mapper.readValue(serialized, ParseResult.class);
        String reserialized = mapper.writeValueAsString(deserialized);
        assertEquals(serialized, reserialized);
    }

    @Test
    public void parseResultWithProblem() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        TypeProblem problem = new TypeProblem("foo", "integer", "bar");

        ParseResult result = ParseResult.withProblem(Record.of("foo", "bar"),
                                                     "foobar",
                                                     problem);

        String serialized = mapper.writeValueAsString(result);
        // System.out.println(serialized);
        ParseResult deserialized = mapper.readValue(serialized, ParseResult.class);
        String reserialized = mapper.writeValueAsString(deserialized);
        assertEquals(serialized, reserialized);
    }

    @Test
    public void serializationResult() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        SerializationResult result = SerializationResult.ok("foobar",
                                                            Record.of("foo", "bar"));
        String serialized = mapper.writeValueAsString(result);
        assertEquals("{\"value\":\"foobar\",\"record\":{\"foo\":\"bar\"},\"problems\":[]}",
                     serialized);
        SerializationResult deserialized = mapper.readValue(serialized, SerializationResult.class);
        String reserialized = mapper.writeValueAsString(deserialized);
        assertEquals(serialized, reserialized);
    }

    @Test
    public void serializationResultWithProblem() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        TypeProblem problem = new TypeProblem("foo", "integer", "bar");

        SerializationResult result = SerializationResult
                .withProblem("foobar",
                             Record.of("foo", "bar"),
                             problem);

        String serialized = mapper.writeValueAsString(result);
        // System.out.println(serialized);
        SerializationResult deserialized = mapper.readValue(serialized, SerializationResult.class);
        String reserialized = mapper.writeValueAsString(deserialized);
        assertEquals(serialized, reserialized);
    }

    @Test
    public void fixedWidthViaInterface() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("bar", 5, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        Format deserializedFormat = mapper.readValue(serializedFormat, Format.class);

        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void delimitedViaInterface() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        Format deserializedFormat = mapper.readValue(serializedFormat, Format.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }
}
