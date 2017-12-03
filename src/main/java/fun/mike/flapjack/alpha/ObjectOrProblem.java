package fun.mike.flapjack.alpha;

public class ObjectOrProblem {
    private Object object;
    private Problem problem;

    public ObjectOrProblem(Object object,
                           Problem problem) {
        this.object = object;
        this.problem = problem;
    }

    public static ObjectOrProblem problem(Problem problem) {
        return new ObjectOrProblem(null, problem);
    }

    public static ObjectOrProblem object(Object object) {
        return new ObjectOrProblem(object, null);
    }

    public boolean hasProblem() {
        return this.problem != null;
    }

    public Object getObject() {
        return this.object;
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
        return "ObjectOrProblem{" +
                "object=" + object +
                ", problem=" + problem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectOrProblem that = (ObjectOrProblem) o;

        if (object != null ? !object.equals(that.object) : that.object != null) return false;
        return problem != null ? problem.equals(that.problem) : that.problem == null;
    }

    @Override
    public int hashCode() {
        int result = object != null ? object.hashCode() : 0;
        result = 31 * result + (problem != null ? problem.hashCode() : 0);
        return result;
    }
}
