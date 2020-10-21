package bg.sofia.uni.fmi.mjt.cache;

import java.util.Map;

public class RRCache<K, V> extends MemoryCache<K, V> {
    public RRCache() {
        super();
    }

    public RRCache(long capacity) {
        super(capacity);
    }

    @Override
    public void set(K key, V value) {
        if (!setWhenNotFull(key, value)) {
            K keyToRemove = getFirstElement();
            remove(keyToRemove);
            addToMaps(key, value);
        }
    }

    @Override
    public long getUsesCount(K key) {
        throw new UnsupportedOperationException("You cannot get uses count in RR cache.");
    }

    private K getFirstElement() {
        if (!getCache().isEmpty()) {
            Map.Entry<K, V> entries = getCache().entrySet().iterator().next();
            return entries.getKey();
        }
        return null;
    }
}
