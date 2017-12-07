package fun.mike.flapjack.alpha;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as = RecordInterface.class)
public class Record implements Map<String, Object>, RecordInterface {
    private final Map<String, Object> data;
    private final Set<Problem> problems;

    @JsonCreator
    public Record(@JsonProperty("data") Map<String, Object> data,
                  @JsonProperty("problems") Set<Problem> problems) {
        this.data = new LinkedHashMap<>(data);
        this.problems = new HashSet<>(problems);
    }

    public static Record with(Map<String, Object> data) {
        return new Record(data, new HashSet<>());
    }

    public static Record with(Map<String, Object> data, Problem problem) {
        Set<Problem> problems = new HashSet<>();
        problems.add(problem);
        return with(data, problems);
    }

    public static Record with(Map<String, Object> data, Set<Problem> problems) {
        return new Record(data, problems);
    }

    public <X extends Throwable> Record orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (problems.isEmpty()) {
            return this;
        } else {
            throw exceptionSupplier.get();
        }
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

    public Date getDate(String key) {
        return (Date) this.get(key);
    }

    public BigDecimal getBigDecimal(String key) {
        return (BigDecimal) this.get(key);
    }

    // public Record <T> updateBigDecimal(String key, Function<BigDecimal, T> f) {
    //     BigDecimal value = getBigDecimal(key);
    //     T newValue = f(value);
    //     put(key, newValue);
    //     return this;
    // }

    public Integer getInteger(String key) {
        return (Integer) this.get(key);
    }

    public Map<String, Object> getData() {
        return data;
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
        data.clear();
    }

    @Override
    public Object remove(Object key) {
        return data.remove(key);
    }

    @Override
    public Object put(String key, Object value) {
        return data.put(key, value);
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
        data.putAll(m);
    }

    @Override
    public String toString() {
        return "Record{" +
                "data=" + data +
                ", problems=" + problems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (data != null ? !data.equals(record.data) : record.data != null) return false;
        return problems != null ? problems.equals(record.problems) : record.problems == null;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (problems != null ? problems.hashCode() : 0);
        return result;
    }
}
