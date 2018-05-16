## Examples

Here are a few examples. Look at the tests if you want more.

### Parsing a delimited record

```java
List<Column> columns = Arrays.asList(Column.string("foo"),
                                     Column.integer("bar"));

Format format = DelimitedFormat.builder()
    .withId("baz")
    .withDescription("Baz")
    .withDelimiter(',')
    .unframed()
    .withColumns(columns)
    .build();

ParseResult result = format.parse("bop,1");

result.isOk();
// => true

result.orElseThrow(result -> new IllegalArgumentException(result.explain()));
// => {foo=bop, bar=1} (fun.mike.Record)
```

### Serializing a delimited record

```java
List<Column> columns = Arrays.asList(Column.string("foo"),
                                     Column.integer("bar"));

Format format = DelimitedFormat.builder()
    .withId("baz")
    .withDescription("Baz")
    .unframed()
    .withDelimiter(',')
    .withColumns(columns)
    .build();

Record record = Record.of("foo", "abcde", "bar", 23);

SerializationResult result = format.serialize(record);

result.isOk();
// => true

result.orElseThrow(result -> new RuntimeException(result.explain()));
// => "abcde,23" (String)
```

### Parsing a fixed-width record

```java
List<Field> fields = Arrays.asList(Field.string("foo", 3),
                                   Field.integer("bar", 2));

FixedWidthFormat format = FixedWidthFormat.builder()
    .withId("baz")
    .withDescription("Baz")
    .withFields(fields)
    .build();

ParseResult result = format.parse("bop 1");

result.isOk();
// => true

result.orElseThrow(result -> new RuntimeException(result.explain()));
// => {foo=bop, bar=1} (fun.mike.Record)
```

### Serializing a fixed-width record

```java
List<Field> fields = Arrays.asList(Field.string("foo", 5),
                                   Field.integer("bar", 5));

FixedWidthFormat format = FixedWidthFormat.builder()
    .withId("baz")
    .withDescription("Baz")
    .withFields(fields)
    .build();

Record record = Record.of("foo", "abcde", "bar", 23);

SerializationResult result = format.serialize(record);

result.isOk();
// => true

result.orElseThrow(result -> new RuntimeException(result.explain()));
// => "abcde23   " (String)
```
