package fun.mike.flapjack.beta;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class DelimitedFormatExplainerTest {
    @Test
    public void explain() {

        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.alwaysFramed("baz", "Baz", ',', '"', columns);
        System.out.println(DelimitedFormatExplainer.explain(format));
    }
}
