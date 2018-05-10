# Overview

`flapjack` is a library for parsing and serializing fixed-width or delimited records to and from maps. This is a brief overview of its functionality.

## Formats

In order to do any parsing or serialization, you'll need to define a format. A format defines how to parse and serialize a record or set of records. At the moment, there are 2 types of formats: fixed-width and delimited.

In Java, this means building a `Format` object. `Format` is an interface, and the corresponding implementations for delimited and fixed-width are `DelimitedFormat` and `FixedWidthFormat`, respectfully. Each of these implementations has a number of factory methods that can be used to create a build an instance. Alternatively, these objects can be parsed from JSON.

### Delimited formats

A delimited format specifies how a record is delimited and framed as well as how to parse and serialize each column.

Here's an example of building a delimited format called `baz` for the record "bop,1", which contains 2 columns - `foo`, a string, and `bar`, an integer:

```java
import fun.mike.flapjack.alpha.DelimitedFormat
import fun.mike.flapjack.alpha.Format;
import fun.mike.flapjack.alpha.

Column foo = Column.string("foo");
Column bar = Column.string("bar");
List<Column> columns = Arrays.asList(foo, bar);
Format baz = DelimitedFormat.unframed("baz", "Baz", ',', columns);
```

Here's what it looks like as JSON:

```json
{
  "type": "delimited",
  "id": "baz",
  "description": "Baz",
  "delimiter": ",",
  "framing": "NONE",
  "frameDelimiter": null,
  "offset": 0,
  "columns": [
    {
      "id": "foo",
      "type": "string",
      "props": null
    },
    {
      "id": "bar",
      "type": "string",
      "props": null
    }
  ],
  "skipFirst": 0,
  "skipLast": 0
}
```

For more examples of building delimited formats, please see the [Delimited guide](delimited.md).

### Fixed-width formats

A fixed-width record is made up of fields. The format specifies the name, type, and length of each column.

Here's an example of building a fixed-width format called `baz` for the record "bop 1", which contains 2 fields - `foo`, a string of length 3, and `bar`, an integer of length 2:

List<Field> fields = Arrays.asList(Field.string("foo", 3),
                                   Field.integer("bar", 2));

```java
import fun.mike.flapjack.alpha.FixedWidthFormat;˘
import fun.mike.flapjack.alpha.Format;
import fun.mike.flapjack.alpha.Field;

Field foo = Field.string("foo", 3);
Field bar = Field.string("bar", 2);
List<Field> fields = Arrays.asList(foo, bar);
Format baz = FixedWidthFormat.basic("baz", "Baz", fields);
```

Here's what it looks like as JSON:

```json
{
  "type": "fixed-width",
  "id": "baz",
  "description": "Baz",
  "fields": [
    {
      "id": "foo",
      "length": 3,
      "type": "string",
      "props": null
    },
    {
      "id": "bar",
      "length": 2,
      "type": "string",
      "props": null
    }
  ],
  "skipFirst": 0,
  "skipLast": 0
}
```

For more examples of building fixed-width formats, please see the [Fixed-width guide](fixed-width.md).

## Parsing

To parse a delimited record, use `parse` on your format, passing the raw text in as the only argument:

```java
ParseResult result = format.parse("bop,1");

Record record = result.getValue();
// => Record.of("foo", "bop", "bar", 1);
```

`parse` returns a `ParseResult` object. You can check if the record was parsed successfully by calling `isOk` on the result:

```java
result.isOk();
// => true
```

If parsing was successful, the result will contain the parsed value, which you can access directly via `getValue`:

```java
Record record = result.getValue();
```

If you'd rather just get a plain old map instead, just assign the result to a `Map<String, Object>`, since a `Record` is a `Map`.

```java
Map<String, Record> map = result.getValue();
```

If there were any errors parsing the record, `getValue` will throw a `ParseException`:

```java
Map<String, Record> map = result.getValue();
format.parse("bop,x");
// => fun.mike.flapjack.alpha.ParseException
```

Rather than checking the result with `isOk` and using `getValue`, you can provide a default with `orElse`:

```java
Record record = result.orElse(Record.of("foo", "bop", "bar", 0));
```

Or throw a custom exception with `orElseThrow`:

```java
Record record = result.orElseThrow(result -> throw new RuntimeException("Failed to parse record: " + result.explain()));
// => java.lang.RuntimeException
```

## Serialization

To serialize a record, call `serialize` on your format with a record or map as the argument:

```java
Record record = Record.of("foo", "bop", "bar", 1);
SerializationResult result = format.serialize(record);
```

`serialize` returns a `SerializationResult`. You can check if the record was serialized successfully by calling `isOk` on the result:

```java
result.isOk();
// => true
```

If serialization was successful, the result will contain a parsed value, which you can access directly via `getValue`:

```java
String line = result.getValue();
// => "bop,1"
```

`SerializationResult` has the same API as `ParseResult`, so you can use `orElse` and `orElseThrow` just like above.
