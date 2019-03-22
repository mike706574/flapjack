package fun.mike.flapjack.beta;

import java.io.Reader;

public class FlatReaderInputPipelineBuilder extends InputPipelineBuilder {
    private Reader reader;
    private Format inputFormat;
    private String lineKey;
    private boolean logFormat;
    private boolean logLines;

    public FlatReaderInputPipelineBuilder(Reader reader, Format format) {
        super();
        this.reader = reader;
        this.inputFormat = format;
        this.lineKey = null;
        this.logFormat = false;
        this.logLines = false;
    }
    // Options
    public FlatReaderInputPipelineBuilder skipFirst(int count) {
        inputFormat = UpdateSkipFirstVisitor.visit(inputFormat, count);
        return this;
    }

    public FlatReaderInputPipelineBuilder includeLineAs(String key) {
        this.lineKey = key;
        return this;
    }

    public FlatReaderInputPipelineBuilder logLines() {
        this.logLines = true;
        return this;
    }

    public FlatReaderInputPipelineBuilder logFormat() {
        this.logFormat = true;
        return this;
    }

    @Override
    InputContext buildInputContext() {
        return new FlatReaderInputContext(reader, inputFormat, lineKey, logFormat, logLines);
    }
}
