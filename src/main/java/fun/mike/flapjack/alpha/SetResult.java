package fun.mike.flapjack.alpha;

import java.util.Set;

import fun.mike.record.alpha.Record;

public class SetResult extends PipelineResult<Set<Record>> {
    public SetResult(PipelineResult<Set<Record>> result) {
        super(result.getValue(), result.getInputContext(), result.getOutputContext(), result.getInputCount(), result.getOutputCount(), result.getFailures());
    }
}
