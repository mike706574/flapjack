package fun.mike.flapjack.alpha;

public class InputContextPipelineBuilder extends InputPipelineBuilder {
    private final InputContext inputContext;

    public InputContextPipelineBuilder(InputContext inputContext) {
        this.inputContext = inputContext;
    }

    @Override
    InputContext buildInputContext() {
        return inputContext;
    }
}
