package fun.mike.flapjack.beta;

import java.util.Map;

import fun.mike.record.Record;

@FunctionalInterface
public interface ValueSerializationFunction {
    ValueOrProblem<String> serialize(String id,
                                     Map<String, Object> props,
                                     Record record);
}
