package fun.mike.flapjack.alpha;

public class ValueOrProblem<T> {
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
}
