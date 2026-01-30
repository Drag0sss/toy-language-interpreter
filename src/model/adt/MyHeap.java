package model.adt;

import exception.MyException;
import model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap<Integer, IValue> {

    private Map<Integer, IValue> heap;
    private int freeAddress;

    public MyHeap() {
        heap = new HashMap<>();
        freeAddress = 1;
    }

    @Override
    public IValue get(Integer key) throws MyException {
        if (!heap.containsKey(key))
            throw new MyException("Heap: address " + key + " not found!");
        return heap.get(key);
    }

    @Override
    public void put(Integer key, IValue value) {
        heap.put(key, value);
    }

    @Override
    public boolean containsKey(Integer key) {
        return heap.containsKey(key);
    }

    @Override
    public void remove(Integer key) {
        heap.remove(key);
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, IValue> newContent) {
        heap = newContent;
    }

    @Override
    public int allocate(IValue value) {
        heap.put(freeAddress, value);
        freeAddress++;
        return freeAddress - 1;
    }

    public String toString() {
        return heap.toString();
    }
}