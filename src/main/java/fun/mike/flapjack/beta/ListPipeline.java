package fun.mike.flapjack.beta;

import java.util.List;

import fun.mike.record.Record;

public class ListPipeline extends GenericPipeline<List<Record>> {
    public ListPipeline(InputContext inputContext, Transform transform, OutputContext<List<Record>> outputContext) {
        super(inputContext, transform, outputContext);
    }

    public ListResult run() {
        return new ListResult(execute());
    }
}
