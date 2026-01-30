package model.adt;

import model.values.IValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public Collection<V> getAll() {
        return map.values();
    }

    @Override
    public MyDictionary<K,V> deepCopy(){
        MyDictionary<K,V> copy = new MyDictionary<>();
        for(K key:map.keySet()){
            copy.put(key, map.get(key));
        }
        return copy;
    }

    @Override
    public Map<K, V> getAllEntries() {
        return new HashMap<>(map);
    }


}
