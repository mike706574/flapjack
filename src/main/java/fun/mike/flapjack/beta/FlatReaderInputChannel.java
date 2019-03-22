package fun.mike.flapjack.beta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// NOTE: Does not support skipLast.
public class FlatReaderInputChannel implements InputChannel {
    private final Logger log = LoggerFactory.getLogger(FlatFileInputChannel.class);

    private final Format format;
    private final String lineKey;
    private final boolean logLines;
    private final int skipFirst;
    private int lineIndex;
    private BufferedReader reader;
    private String nextLine;

    public FlatReaderInputChannel(Reader reader, Format format, String lineKey, boolean logLines) {
        this.reader = new BufferedReader(reader);
        this.format = format;
        this.lineKey = lineKey;
        this.logLines = logLines;
        this.lineIndex = 0;
        this.skipFirst = GetSkipFirstVisitor.visit(format);
    }

    @Override
    public InputResult take() {
        while (lineIndex < skipFirst) {
            lineIndex++;
            readLine(reader);
        }

        int number = lineIndex + 1;
        lineIndex++;

        String line = nextLine == null ? readLine(reader) : nextLine;
        nextLine = null;

        if (logLines) {
            log.debug("Processing record #" + number + ": " + line);
        }

        ParseResult parseResult = format.parse(line);

        if (parseResult.hasProblems()) {
            return InputResult.failure(line, ParseFailure.fromResult(number, line, parseResult));
        }

        if(lineKey != null) {
            parseResult.getValue().set(lineKey, line);
        }

        parseResult.getValue().setMetadataProperty("number", number);

        return InputResult.ok(parseResult.getValue(), line);
    }

    private String readLine(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public boolean hasMore() {
        while (lineIndex < skipFirst) {
            lineIndex++;
            readLine(reader);
        }

        if (nextLine == null) {
            nextLine = readLine(reader);
        }

        return nextLine != null;
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
