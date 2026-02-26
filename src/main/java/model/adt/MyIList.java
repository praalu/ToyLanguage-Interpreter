package model.adt;

import java.util.List;

public interface MyIList<V>{
    public void add(V value);
    public void remove(V value);
    void clear();
    List<V> getList();
}
