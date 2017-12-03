package fun.mike.flapjack.alpha;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class Record implements Map<String, Object> {
    private final Long index;
    private final Map<String, Object> data;
    private final Set<Problem> problems;
    private final String line;

    private Record(Record record) {
        this.index = record.getIndex();
        this.line = record.getLine().orElse(null);
        this.data = new LinkedHashMap<String, Object>(record);
        this.problems = new HashSet<Problem>(record.getProblems());
    }

    private Record(Record record, Problem problem) {
        this.index = record.getIndex();
        this.line = record.getLine().orElse(null);
        this.data = new LinkedHashMap<String, Object>(record);
        this.problems = new HashSet<Problem>(record.getProblems());
        this.problems.add(problem);
    }

    private Record(Long index, Map<String, Object> data, Set<Problem> problems) {
        this.index = index;
        this.data = new LinkedHashMap<String, Object>(data);
        this.problems = new HashSet<Problem>(problems);
        this.line = null;
    }

    private Record(Long index, Map<String, Object> data, Problem problem, String line) {
        this.index = index;
        this.data = new LinkedHashMap<String, Object>(data);
        this.problems = new HashSet<Problem>();
        this.problems.add(problem);
        this.line = line;
    }

    public static Record with(Long index, Map<String, Object> data, Set<Problem> problems) {
        return new Record(index, data, problems);
    }

    public Record withProblem(Problem problem) {
        return new Record(this, problem);
    }

    public <X extends Throwable> Record orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (problems.isEmpty()) {
            return this;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public Long getIndex() {
        return this.index;
    }

    public Optional<String> getLine() {
        return Optional.of(this.line);
    }

    public boolean hasProblems() {
        return !this.problems.isEmpty();
    }

    public Set<Problem> getProblems() {
        return new HashSet<Problem>(this.problems);
    }

    public String getString(String key) {
        return (String) this.get(key);
    }

    public Long getLong(String key) {
        return (Long) this.get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) this.get(key);
    }

    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
    }

    @Override
    public Collection<Object> values() {
        return data.values();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String put(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(Object key) {
        return data.get(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
