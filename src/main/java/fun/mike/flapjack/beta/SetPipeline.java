package fun.mike.flapjack.beta;

import java.util.Set;

import fun.mike.record.Record;

public class SetPipeline extends GenericPipeline<Set<Record>> {
    public SetPipeline(InputContext inputContext, Transform transform, OutputContext<Set<Record>> outputContext) {
        super(inputContext, transform, outputContext);
    }

    public SetResult run() {
        return new SetResult(execute());
    }
}
