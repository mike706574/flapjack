# flapjack

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fun.mike/flapjack-alpha/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fun.mike/flapjack-alpha)
[![Javadocs](https://www.javadoc.io/badge/fun.mike/flapjack-alpha.svg)](https://www.javadoc.io/doc/fun.mike/flapjack-alpha)

Flat file parsing and serialization library for Java.

## Quick Example

```java
Column foo = Column.string("foo");
Column bar = Column.integer("bar");
List<Column> columns = Arrays.asList(foo, bar);
Format format = DelimitedFormat.unframed("baz", "Baz", ',', columns);

ParseResult result = format.parse("bop,1");

Record record = result.getValue();
// => {foo=bop, bar=1}

SerializationResult result = format.serialize(record);

String text = format.serialize(record);
// => "bop,1"
```

## Resources

- [Overview](overview.md)
- [Examples](examples.md)
- [API Docs](https://www.javadoc.io/doc/fun.mike/flapjack-alpha)

## Build

[![CircleCI](https://circleci.com/gh/mike706574/flapjack.svg?style=svg)](https://circleci.com/gh/mike706574/flapjack)

## Copyright and License

The use and distribution terms for this software are covered by the
[Eclipse Public License 1.0] which can be found in the file
epl-v10.html at the root of this distribution. By using this softwaer
in any fashion, you are agreeing to be bound by the terms of this
license. You must not remove this notice, or any other, from this
software.

[Eclipse Public License 1.0]: http://opensource.org/licenses/eclipse-1.0.php
