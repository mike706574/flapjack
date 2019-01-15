package fun.mike.flapjack.alpha;

public class ReducePipeline<T> extends GenericPipeline<T> {
    public ReducePipeline(InputContext inputContext, Transform transform, OutputContext<T> outputContext) {
        super(inputContext, transform, outputContext);
    }

    public PipelineResult<T> run() {
        return execute();
    }
}
