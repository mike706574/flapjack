package fun.mike.flapjack.beta;

public class InputContextExplainer implements InputContextVisitor {
    private String explanation;

    public InputContextExplainer() {
        explanation = "Nothing.";
    }

    public String explain() {
        return explanation;
    }

    @Override
    public void accept(FlatFileInputContext inputContext) {
        String path = inputContext.getPath();

        explanation = String.join("\n",
                                  "Reading from a flat file.",
                                  "File path: " + path,
                                  "Format: " + formatSummary(inputContext));
    }

    @Override
    public void accept(FlatReaderInputContext inputContext) {
        explanation = String.join("\n",
                                  "Reading from a Reader.",
                                  "Format: " + formatSummary(inputContext));
    }

    private String formatSummary(FlatFileInputContext inputContext) {
        Format format = inputContext.getFormat();
        boolean logFormat = inputContext.logFormat();
        return logFormat ? FormatExplainer.explain(format) : format.getId();
    }

    private String formatSummary(FlatReaderInputContext inputContext) {
        Format format = inputContext.getFormat();
        boolean logFormat = inputContext.logFormat();
        return logFormat ? FormatExplainer.explain(format) : format.getId();
    }

    @Override
    public void accept(IterableInputContext inputContext) {
        explanation = "From an iterable of class " + inputContext.getRecords().getClass().getSimpleName() + ".";
    }

    @Override
    public void accept(CollectionInputContext inputContext) {
        explanation = "From a collection of class " + inputContext.getRecords().getClass().getSimpleName() + " containing " + inputContext.getRecords().size() + " elements.";
    }
}
