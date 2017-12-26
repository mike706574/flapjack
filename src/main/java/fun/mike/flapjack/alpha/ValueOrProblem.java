package fun.mike.flapjack.alpha;

import java.io.Serializable;

public class ValueOrProblem<T> implements Serializable {
    private T value;
    private Problem problem;

    public ValueOrProblem(T value, Problem problem) {
        this.value = value;
        this.problem = problem;
    }

    @SuppressWarnings("unchecked")
    public static ValueOrProblem problem(Problem problem) {
        return new ValueOrProblem(null, problem);
    }

    @SuppressWarnings("unchecked")
    public static ValueOrProblem value(Object value) {
        return new ValueOrProblem(value, null);
    }

    public boolean hasProblem() {
        return this.problem != null;
    }

    public T getValue() {
        return this.value;
    }

    public Problem getProblem() {
        return this.problem;
    }

    public String explain() {
        if (hasProblem()) {
            return this.problem.explain();
        }
        return "No problem.";
    }

    @Override
    public String toString() {
        return "ValueOrProblem{" +
                "value=" + value +
                ", problem=" + problem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueOrProblem<?> that = (ValueOrProblem<?>) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return problem != null ? problem.equals(that.problem) : that.problem == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (problem != null ? problem.hashCode() : 0);
        return result;
    }
}
