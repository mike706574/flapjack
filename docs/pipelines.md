# Pipelines

Pipelines let you parse, transform, and serialize flat file records using a functional, data-driven API.

The examples below use this example CSV file, `animals.csv`:

```
dog,4,medium
elephant,4,huge
fox,4,medium
ostrich,2,medium
whale,0,gigantic
snake,0,small
```

## Flat file to flat file

`Example.java`

```java
// Define an input format
Format inputFormat =
    DelimitedFormat.unframed("animals",
                             "A bunch of animals.",
                             ',',
                             Arrays.asList(Column.string("name"),
                                           Column.integer("legs"),
                                           Column.string("size")));

// Define an output format
Format outputFormat =
    FixedWidthFormat("medium-sized-animals",
                     "A bunch of medium-sized animals.",
                     Arrays.asList(Field.string("name", 10),
                                   Field.string("size", 10)));

// Build a pipeline
FlatFilePipeline pipeline = Pipeline.fromFile("animals.csv", inputFormat)
    .filter(x -> x.getString("size").equals("MEDIUM"))
    .map(x -> x.updateString("name", String::toUpperCase))
    .toFile("medium-sized-animals.txt", outputFormat)
    .build();

// Run it
FlatFileResult result = pipeline.run();

// Check for failures
result.isOk();
// => true

result.getFailureCount();
// => 0

// See how many animals went in
result.getInputCount();
// => 6

// See how many medium-sized animals came out
result.getOutputCount();
// => 3
```

`medium-sized-animals.txt`

```
DOG       4
FOX       4
OSTRICH   2
```

## Flat file to list

```java
// Define an input format
Format inputFormat =
    DelimitedFormat.unframed("animals",
                             "A bunch of animals.",
                             ',',
                             Arrays.asList(Column.string("name"),
                                           Column.integer("legs"),
                                           Column.string("size")));

ListPipeline pipeline = Pipeline.fromFile("animals.csv", inputFormat)
        .map(x -> x.updateString("size", String::toUpperCase))
        .filter(x -> x.getString("size").equals("MEDIUM"))
        .toList();

ListResult result = pipeline.run();

assertTrue(result.isOk());
assertEquals(6, result.getInputCount());
assertEquals(3, result.getOutputCount());

List<Record> animals = result.orElseThrow();
// => [{name=dog, legs=4, size=MEDIUM},
       {name=fox, legs=4, size=MEDIUM},
       {name=ostrich, legs=2, size=MEDIUM}]
```
