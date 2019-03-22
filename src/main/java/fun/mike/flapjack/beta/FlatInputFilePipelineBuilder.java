package fun.mike.flapjack.beta;

// TODO: Next major verison - rename to FlatFileInputPipelineBuilder
public class FlatInputFilePipelineBuilder extends InputPipelineBuilder {
    private final String inputPath;
    private Format inputFormat;
    private String lineKey;
    private boolean logFormat;
    private boolean logLines;

    public FlatInputFilePipelineBuilder(String inputPath, Format format) {
        super();
        this.inputPath = inputPath;
        this.inputFormat = format;
        this.lineKey = null;
        this.logFormat = false;
        this.logLines = false;
    }

    // Options
    public FlatInputFilePipelineBuilder skipFirst(int count) {
        inputFormat = UpdateSkipFirstVisitor.visit(inputFormat, count);
        return this;
    }

    public FlatInputFilePipelineBuilder skipLast(int count) {
        inputFormat = UpdateSkipLastVisitor.visit(inputFormat, count);
        return this;
    }

    public FlatInputFilePipelineBuilder includeLineAs(String key) {
        this.lineKey = key;
        return this;
    }

    public FlatInputFilePipelineBuilder logLines() {
        this.logLines = true;
        return this;
    }

    public FlatInputFilePipelineBuilder logFormat() {
        this.logFormat = true;
        return this;
    }

    @Override
    InputContext buildInputContext() {
        return new FlatFileInputContext(inputPath, inputFormat, lineKey, logFormat, logLines);
    }
}
