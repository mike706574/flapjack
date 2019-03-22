package fun.mike.flapjack.beta;

import java.io.Reader;
import java.util.Objects;

public class FlatReaderInputContext implements InputContext {
    private final Reader reader;
    private final Format format;
    private final String lineKey;
    private final boolean logFormat;
    private final boolean logLines;

    public FlatReaderInputContext(Reader reader, Format format, String lineKey, boolean logFormat, boolean logLines) {
        this.reader = reader;
        this.format = format;
        this.lineKey = lineKey;
        this.logFormat = logFormat;
        this.logLines = logLines;
    }

    @Override
    public String toString() {
        return "FlatReaderInputContext{" +
                "reader=" + reader +
                ", format=" + format +
                ", lineKey='" + lineKey + '\'' +
                ", logFormat=" + logFormat +
                ", logLines=" + logLines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatReaderInputContext that = (FlatReaderInputContext) o;
        return logFormat == that.logFormat &&
                logLines == that.logLines &&
                Objects.equals(reader, that.reader) &&
                Objects.equals(format, that.format) &&
                Objects.equals(lineKey, that.lineKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reader, format, lineKey, logFormat, logLines);
    }

    public Reader getReader() {
        return reader;
    }

    public Format getFormat() {
        return format;
    }

    public String getLineKey() {
        return lineKey;
    }

    public boolean logFormat() { return logFormat; }

    public boolean logLines() {
        return logLines;
    }

    @Override
    public InputChannel buildChannel() {
        return new FlatReaderInputChannel(reader, format, lineKey, logLines);
    }

    @Override
    public void accept(InputContextVisitor visitor) {
        visitor.accept(this);
    }
}