package mike706574;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.function.Function;

public class Record implements Map<String, String> {
    private final Long index;
    private final String line;
    private final Map<String, String> data;
    private final String errorCategory;

    private Record( Long index, Map<String, String> data ) {
        this.index = index;
        this.data = data;
        this.line = null;
        this.errorCategory = null;
    }

    public Record( Long index,
                   String errorCategory,
                   String line ) {
        this.index = index;
        this.line = line;
        this.data = null;
        this.errorCategory = errorCategory;
    }

    public static Record error( Long index,
                                String errorCategory,
                                String line ) {
        return new Record( index, errorCategory, line );
    }

    public static Record of( Long index, Map<String, String> data ) {
        return new Record( index, data );
    }

    public <X extends Throwable> Record orElseThrow( Function<Record, ? extends X> exceptionSupplier ) throws X {
        if( data != null ) {
            return this;
        }
        else {
            throw exceptionSupplier.apply( this );
        }
    }

    public Long getIndex() {
        return this.index;
    }

    public Optional<String> getLine() {
        return Optional.of( this.line );
    }

    public Optional<String> getErrorCategory() {
        return Optional.of( this.errorCategory );
    }

    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return data.entrySet();
    }

    @Override
    public Collection<String> values() {
        return data.values();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove( Object key ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String put( String key, String values ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String get( Object key ) {
        return data.get( key );
    }

    @Override
    public boolean containsValue( Object value ) {
        return data.containsValue( value );
    }

    @Override
    public boolean containsKey( Object key ) {
        return data.containsKey( key );
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
    public void putAll( Map<? extends String, ? extends String> m ) {
        throw new UnsupportedOperationException();
    }
}
