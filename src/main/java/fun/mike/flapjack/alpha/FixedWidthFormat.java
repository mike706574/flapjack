package fun.mike.flapjack.alpha;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fun.mike.record.alpha.Record;

/**
 * A fixed-width format used to parse and serialize fixed-width records.
 */
public class FixedWidthFormat implements Format, Serializable {
    private final String id;
    private final String description;
    private final List<Field> fields;
    private final int skipFirst;
    private final int skipLast;

    private final FixedWidthParser parser;
    private final FixedWidthSerializer serializer;

    /**
     * Builds a fixed-width format.
     *
     * @param id          an identifier for the format
     * @param description a description of the format
     * @param fields      the fields
     */
    public FixedWidthFormat(String id,
                            String description,
                            List<Field> fields) {
        this(id, description, fields, 0, 0);
    }

    /**
     * Builds a fixed-width format.
     *
     * @param id          an identifier for the format
     * @param description a description of the format
     * @param fields      the fields
     * @param skipFirst   the number of records to skip when parsing a set
     *                    of records
     * @param skipLast    the number of ending records to skip when parsing
     *                    a set of records
     */
    @JsonCreator
    public FixedWidthFormat(@JsonProperty("id") String id,
                            @JsonProperty("description") String description,
                            @JsonProperty("fields") List<Field> fields,
                            @JsonProperty("skipFirst") int skipFirst,
                            @JsonProperty("skipLast") int skipLast) {
        this.id = id;
        this.description = description;
        this.fields = Collections.unmodifiableList(fields);
        this.skipFirst = skipFirst;
        this.skipLast = skipLast;
        this.parser = new FixedWidthParser(this);
        this.serializer = new FixedWidthSerializer(this);
    }

    private FixedWidthFormat(Builder builder) {
        this(builder.id, builder.description, builder.fields, builder.skipFirst, builder.skipLast);
    }

    public static FixedWidthFormat basic(String id, String description, List<Field> fields) {
        return new FixedWidthFormat(id, description, fields);
    }

    public static IId builder() {
        return new Builder();
    }

    /**
     * Returns a version of the format with the given number of records skipped
     * when parsing a set of records.
     *
     * @param count the number of records to skip when parsing a set of records
     * @return a fixed-width format with the given number of records skipped
     * skipped
     */
    public FixedWidthFormat skipFirst(int count) {
        return new FixedWidthFormat(id, description, fields, count, skipLast);
    }

    /**
     * Returns a version of the format with the given number of ending records skipped
     * when parsing a set of records.
     *
     * @param count the number of ending records to skip when parsing a set
     *              of records
     * @return a fixed-width format with the given number of ending records
     * skipped
     */
    public FixedWidthFormat skipLast(int count) {
        return new FixedWidthFormat(id, description, fields, skipFirst, count);
    }

    /**
     * @return an identifier for the format
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return a description of the format
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the fields
     */
    public List<Field> getFields() {
        return this.fields;
    }


    /**
     * @return the number of records to skip when parsing
     * a set of records
     */
    public int getSkipFirst() {
        return skipFirst;
    }

    /**
     * @return the number of ending records to skip when parsing
     * a set of records
     */
    public int getSkipLast() {
        return skipLast;
    }

    @Override
    public String toString() {
        return "FixedWidthFormat{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", fields=" + fields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedWidthFormat that = (FixedWidthFormat) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return fields != null ? fields.equals(that.fields) : that.fields == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        return result;
    }

    /**
     * Parses a fixed-width string.
     *
     * @param line a fixed-width string
     * @return a ParseResult containing a record if parsing was successful;
     * otherwise, a ParseResult containing parsing problems.
     */
    @Override
    public ParseResult parse(String line) {
        return parser.parse(line);
    }

    /**
     * Parses a fixed-width string. Throws a parse exception if parsing is
     * unsuccessful.
     *
     * @param line a fixed-width string
     * @return the parsed record
     */
    @Override
    public Record parseAndThrow(String line) {
        return parser.parse(line).orElseThrow();
    }

    /**
     * Serializes a map to a fixed-width string.
     *
     * @param map a map
     * @return a SerializationResult containing a fixed-width string if
     * serialization was successful; otherwise, a SerializationResult
     * containing serialization problems.
     */
    @Override
    public SerializationResult serialize(Map<String, Object> map) {
        return serializer.serialize(map);
    }

    /**
     * Serializes a record to a fixed-width string.
     *
     * @param record a record
     * @return a Result containing a fixed-width string if serialization was
     * successful; otherwise, a Result containing serialization problems.
     */
    @Override
    public SerializationResult serialize(Record record) {
        return serializer.serialize(record);
    }

    /**
     * Serializes a record to a fixed-width string. Throws a
     * SerializationException if parsing is unsuccessful.
     *
     * @param record a record
     * @return a Result containing a fixed-width string if serialization was
     * successful; otherwise, a Result containing serialization problems.
     */
    @Override
    public String serializeAndThrow(Record record) {
        return serializer.serialize(record).orElseThrow();
    }

    @Override
    public void visit(FormatVisitor visitor) {
        visitor.accept(this);
    }

    interface ISkipping {
        ISkipping skipLast(int count);
        ISkipping skipFirst(int count);
        FixedWidthFormat build();
    }

    interface IFields {
        ISkipping withFields(List<Field> fields);
        IFields addField(Field field);
        IFields addFields(List<Field> fields);
        ISkipping skipLast(int count);
        ISkipping skipFirst(int count);
        FixedWidthFormat build();
    }

    interface IDescription {
        IFields withDescription(String description);
        ISkipping withFields(List<Field> fields);
        IFields addField(Field field);
        IFields addFields(List<Field> fields);
    }

    interface IId {
        IDescription withId(String id);
        ISkipping withFields(List<Field> fields);
        IFields addField(Field field);
        IFields addFields(List<Field> fields);
    }

    /**
     * {@code FixedWidthFormat} builder static inner class.
     */
    public static final class Builder implements ISkipping, IFields, IDescription, IId {
        private int skipLast = 0;
        private int skipFirst = 0;
        private List<Field> fields = new LinkedList<>();
        private String description;
        private String id;

        private Builder() {}

        /**
         * @param count the number of ending records to skip when parsing
         *              a set of records
         */
        @Override
        public ISkipping skipLast(int count) {
            this.skipLast = count;
            return this;
        }

        /**
         * @param count the number of records to skip when parsing a set of
         *              records
         */
        @Override
        public ISkipping skipFirst(int count) {
            this.skipFirst = count;
            return this;
        }

        /**
         * @param fields fields
         */
        @Override
        public ISkipping withFields(List<Field> fields) {
            this.fields = fields;
            return this;
        }

        /**
         * @param field a field to add
         */
        @Override
        public IFields addField(Field field) {
            this.fields.add(field);
            return this;
        }

        /**
         * @param fields fields to add
         */
        @Override
        public IFields addFields(List<Field> fields) {
            this.fields.addAll(fields);
            return this;
        }

        /**
         * @param description a description of the format
         */
        @Override
        public IFields withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * @param id an identifier for the format
         */
        @Override
        public IDescription withId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Returns a {@code FixedWidthFormat} built from the parameters previously set.
         *
         * @return a {@code FixedWidthFormat} built with parameters of this {@code FixedWidthFormat.Builder}
         */
        public FixedWidthFormat build() {
            return new FixedWidthFormat(this);
        }
    }
}
