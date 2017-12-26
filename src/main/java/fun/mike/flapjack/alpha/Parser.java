package fun.mike.flapjack.alpha;

import java.io.Serializable;

public class Parser implements Serializable {
    public Result parse(DelimitedFormat format, String line) {
        return new DelimitedParser(format).parse(line);
    }

    public Result parse(FixedWidthFormat format, String line) {
        return new FixedWidthParser(format).parse(line);
    }
}
