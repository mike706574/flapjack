package fun.mike.flapjack.beta;

public class FlatFilePipeline extends GenericPipeline<Nothing> {
    public FlatFilePipeline(InputContext inputContext, Transform transform, OutputContext<Nothing> outputContext) {
        super(inputContext, transform, outputContext);
    }

    public FlatFileResult run() {
        return new FlatFileResult(execute());
    }
}
