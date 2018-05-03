package fun.mike.flapjack.alpha;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TruncationProblemTest {
    @Test
    public void instantiationAndExplain() {
        TruncationProblem problem = new TruncationProblem("foo", "string", 5,"abcdefghi");
        assertEquals("Field \"foo\" of type \"string\" with serialized value \"abcdefghi\" must be 5 characters or less.", problem.explain());
    }
}

