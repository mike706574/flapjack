package fun.mike.flapjack.beta;

import java.util.List;
import java.util.Map;

import fun.mike.record.Record;

public class GroupPipeline<G> extends GenericPipeline<Map<G, List<Record>>> {
    public GroupPipeline(InputContext inputContext, Transform transform, OutputContext<Map<G, List<Record>>> outputContext) {
        super(inputContext, transform, outputContext);
    }

    public GroupResult<G> run() {
        return new GroupResult<>(execute());
    }
}
