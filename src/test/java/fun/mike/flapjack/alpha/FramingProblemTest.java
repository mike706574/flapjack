package fun.mike.flapjack.alpha;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FramingProblemTest {
    @Test
    public void instantiationAndExplain() {
        FramingProblem problem = new FramingProblem(1, 5);
        assertEquals("Column 2 was not properly framed (at character 6).", problem.explain());
    }
}
