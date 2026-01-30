package model.adt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class MyList<T>  implements MyIList<T> {
    private final List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public boolean contains(T key) {
        return list.contains(key);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
