package fun.mike.flapjack.alpha;

import java.io.Serializable;

public interface Parser {
    Result parse(String line);

    public static Result parse(Format format, String line) {
        return ParserFactory.build(format).parse(line);
    }
}
