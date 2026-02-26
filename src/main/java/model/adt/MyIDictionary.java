package model.adt;

import exception.MyException;

import java.util.Map;

public interface MyIDictionary<K,V> {
    public void put(K key, V value);
    public boolean isDefined(K key);
    public V getValue (K key);
    void remove(K key) throws MyException;
    Map<K, V> getContent();
    void clear();
    MyIDictionary<K,V> deepCopy() throws MyException;
}

