package fun.mike.flapjack.alpha;

/**
 * Parses serialized strings.
 */
public interface Parser {
    /**
     * @deprecated Use format.parse(String)
     */
    @Deprecated
    static ParseResult parse(Format format, String line) {
        return ParserFactory.build(format).parse(line);
    }

    /**
     * Parses a line.
     *
     * @param line a line
     * @return a result containing the parsed record if successful, otherwise,
     * a result containing the problems
     */
    ParseResult parse(String line);
}
