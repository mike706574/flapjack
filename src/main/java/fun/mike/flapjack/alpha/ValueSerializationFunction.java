package fun.mike.flapjack.alpha;

import java.util.Map;

import fun.mike.record.alpha.Record;

@FunctionalInterface
public interface ValueSerializationFunction {
    ValueOrProblem<String> serialize(String id,
                                     Map<String, Object> props,
                                     Record record);
}
