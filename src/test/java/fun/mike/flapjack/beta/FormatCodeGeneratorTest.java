package fun.mike.flapjack.beta;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatCodeGeneratorTest {

    @Test
    public void generateDelimitedColumns() {
        assertEquals("List<Column> columns = Arrays.asList(Column.string(foo),\n     Column.string(bar),\n     Column.string(baz.bop),\n     Column.string(1),\n     Column.string(2),\n     Column.string(3A),\n     Column.string(Grunt Gulp));",
                     FormatCodeGenerator.generateDelimitedColumns("foo,bar,baz.bop,1,2,3A,Grunt Gulp", ','));
    }
}
