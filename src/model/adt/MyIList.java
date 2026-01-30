package model.adt;

import java.util.List;
import java.util.stream.DoubleStream;

public interface MyIList<T> {
    void add(T value);

    public List<T> getList();

    boolean contains(T key);
}
