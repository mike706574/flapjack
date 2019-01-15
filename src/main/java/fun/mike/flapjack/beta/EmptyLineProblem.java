package fun.mike.flapjack.beta;

import java.io.Serializable;

/**
 * Problem used the line is empty.
 */
public class EmptyLineProblem implements Problem, Serializable {
    @Override
    public String explain() {
        return "Line is empty.";
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }
}
