# flapjack

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fun.mike/flapjack-alpha/badge.svg)](http://search.maven.org/#artifactdetails%7Cfun.mike%7Cflapjack-alpha%7C0.0.26%7Cjar)

Flat file parsing and serialization library for Java.

## Usage

Here are a few examples. Look at the tests if you want more.

### Parsing a delimited record

```java
List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                     Column.with("bar", "integer"));

DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);
DelimitedParser parser = new DelimitedParser(format);

Result result = parser.parse("bop,1");

result.isOk();
// => true

result.recordOrElseThrow(result -> new IllegalArgumentException(result.explain()));
// => {foo=bop, bar=1} (fun.mike.Record)
```

### Serializing a delimited record

```java
List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                     Column.with("bar", "integer"));

DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ',', columns);
DelimitedSerializer serializer = new DelimitedSerializer(format);

Record record = Record.of("foo", "abcde", "bar", 23);

Result result = serializer.serialize(record);

result.isOk();
// => true

result.lineOrElseThrow(result -> new RuntimeException(result.explain()));
// => "abcde,23" (String)
```

### Parsing a fixed-width record

```java
List<Field> fields = Arrays.asList(Field.with("foo", 3, "string"),
                                   Field.with("bar", 2, "integer"));

FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

FixedWidthParser parser = new FixedWidthParser(format);

Result result = parser.parse("bop 1");

result.isOk();
// => true

result.recordOrElseThrow(result -> new RuntimeException(result.explain()));
// => {foo=bop, bar=1} (fun.mike.Record)
```

### Serializing a fixed-width record

```java
List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                   Field.with("bar", 5, "integer"));

FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);
FixedWidthSerializer serializer = new FixedWidthSerializer(format);

Record record = Record.of("foo", "abcde", "bar", 23);

Result result = serializer.serialize(record);

result.isOk();
// => true

result.lineOrElseThrow(result -> new RuntimeException(result.explain()));
// => "abcde23   " (String)
```

## Copyright and License

The use and distribution terms for this software are covered by the
[Eclipse Public License 1.0] which can be found in the file
epl-v10.html at the root of this distribution. By using this softwaer
in any fashion, you are agreeing to be bound by the terms of this
license. You must not remove this notice, or any other, from this
software.

[Eclipse Public License 1.0]: http://opensource.org/licenses/eclipse-1.0.php
