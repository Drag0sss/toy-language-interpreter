package model.adt;

import exception.MyException;

import java.util.Map;

public interface MyIHeap<K, V> {
    V get(K key) throws MyException;
    void put(K key, V value);
    boolean containsKey(K key);
    void remove(K key);
    Map<K, V> getContent();
    void setContent(Map<K, V> newContent);

    int allocate(V value);
}
