package fun.mike.flapjack.alpha;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Record extends LinkedHashMap<String, Object> {
    public Record() {
        super();
    }

    public Record(Record record) {
        super(record);
    }

    public Record(Map<String, Object> map) {
        super(map);
    }

    public static Record of() {
        return new Record();
    }

    public static Record of(String k1, Object v1) {
        Record record = new Record();
        record.put(k1, v1);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2,
                            String k3, Object v3) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        record.put(k3, v3);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2,
                            String k3, Object v3, String k4, Object v4) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        record.put(k3, v3);
        record.put(k4, v4);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2,
                            String k3, Object v3, String k4, Object v4,
                            String k5, Object v5) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        record.put(k3, v3);
        record.put(k4, v4);
        record.put(k5, v5);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2,
                            String k3, Object v3, String k4, Object v4,
                            String k5, Object v5, String k6, Object v6) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        record.put(k3, v3);
        record.put(k4, v4);
        record.put(k5, v5);
        record.put(k6, v6);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2,
                            String k3, Object v3, String k4, Object v4,
                            String k5, Object v5, String k6, Object v6,
                            String k7, Object v7) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        record.put(k3, v3);
        record.put(k4, v4);
        record.put(k5, v5);
        record.put(k6, v6);
        record.put(k7, v7);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2,
                            String k3, Object v3, String k4, Object v4,
                            String k5, Object v5, String k6, Object v6,
                            String k7, Object v7, String k8, Object v8) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        record.put(k3, v3);
        record.put(k4, v4);
        record.put(k5, v5);
        record.put(k6, v6);
        record.put(k7, v7);
        record.put(k8, v8);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2,
                            String k3, Object v3, String k4, Object v4,
                            String k5, Object v5, String k6, Object v6,
                            String k7, Object v7, String k8, Object v8,
                            String k9, Object v9) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        record.put(k3, v3);
        record.put(k4, v4);
        record.put(k5, v5);
        record.put(k6, v6);
        record.put(k7, v7);
        record.put(k8, v8);
        record.put(k9, v9);
        return record;
    }

    public static Record of(String k1, Object v1, String k2, Object v2,
                            String k3, Object v3, String k4, Object v4,
                            String k5, Object v5, String k6, Object v6,
                            String k7, Object v7, String k8, Object v8,
                            String k9, Object v9, String k10, Object v10) {
        Record record = new Record();
        record.put(k1, v1);
        record.put(k2, v2);
        record.put(k3, v3);
        record.put(k4, v4);
        record.put(k5, v5);
        record.put(k6, v6);
        record.put(k7, v7);
        record.put(k8, v8);
        record.put(k9, v9);
        record.put(k10, v10);
        return record;
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

    public Integer getInteger(String key) {
        return (Integer) this.get(key);
    }

    public Record assoc(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public Record dissoc(String key) {
        this.remove(key);
        return this;
    }
}
