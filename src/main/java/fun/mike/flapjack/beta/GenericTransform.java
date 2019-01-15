package fun.mike.flapjack.beta;

import java.util.List;
import java.util.Optional;

import fun.mike.record.Record;

public class GenericTransform implements Transform {
    private final List<CompiledOperation> compiledOperations;

    public GenericTransform(List<Operation> operations) {
        this.compiledOperations = Operations.compile(operations);
    }

    public TransformResult run(Record record) {
        Record outputRecord = record;
        for (CompiledOperation compiledOperation : compiledOperations) {
            try {
                Optional<Record> result = compiledOperation.getOperation().run(outputRecord);

                if (!result.isPresent()) {
                    return TransformResult.empty(outputRecord, record, compiledOperation.getInfo());
                }

                outputRecord = result.get();
            } catch (Exception ex) {
                return TransformResult.failure(outputRecord, record, compiledOperation.getInfo(), ex);
            }
        }
        return TransformResult.ok(outputRecord, record);
    }
}
