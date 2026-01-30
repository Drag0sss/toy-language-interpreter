package model.adt;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary<K, V> {
    V get(K key);
    void put(K key, V value);
    boolean isDefined(K key);
    void remove(K key);
    Collection<V> getAll();
    MyDictionary<K,V> deepCopy();
    Map<K, V> getAllEntries();
}
