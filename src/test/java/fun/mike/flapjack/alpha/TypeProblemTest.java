package fun.mike.flapjack.alpha;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypeProblemTest {
    @Test
    public void instantiationAndExplain() {
        TypeProblem problem = new TypeProblem("foo", "string", "abcdefghi");
        assertEquals("Value \"abcdefghi\" for field \"foo\" must be a \"string\".", problem.explain());

    }
}
