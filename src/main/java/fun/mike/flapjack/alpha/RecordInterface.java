package fun.mike.flapjack.alpha;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface RecordInterface {
    public Map<String, Object> getData();
    public Set<Problem> getProblems();
}
