package fun.mike.flapjack.alpha;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MissingValueProblemTest {
    @Test
    public void instantiationAndExplain() {
        MissingValueProblem problem = new MissingValueProblem("foo", "string");
        assertEquals("Missing required value for field \"foo\" of type \"string\".", problem.explain());
    }
}
