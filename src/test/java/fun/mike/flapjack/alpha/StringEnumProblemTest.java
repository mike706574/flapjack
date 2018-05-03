package fun.mike.flapjack.alpha;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringEnumProblemTest {
    @Test
    public void instantiationAndExplain() {
        StringEnumProblem problem = new StringEnumProblem("foo", "bar", Arrays.asList("baz", "bop"));
        assertEquals("Value \"bar\" for field \"foo\" must be one of the following 2 string options: \"baz\", \"bop\"", problem.explain());
    }
}
