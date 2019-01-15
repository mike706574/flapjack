package fun.mike.flapjack.alpha;

import java.util.List;

import fun.mike.record.alpha.Record;

public class ListPipeline extends GenericPipeline<List<Record>> {
    public ListPipeline(InputContext inputContext, Transform transform, OutputContext<List<Record>> outputContext) {
        super(inputContext, transform, outputContext);
    }

    public ListResult run() {
        return new ListResult(execute());
    }
}
