package model.adt;

import exception.MyException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final Map<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public V getValue(K key) {
        return dictionary.get(key);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public void remove(K key) throws MyException {
        if (!isDefined(key)) {
            throw new MyException("Key " + key.toString() + " is not defined in the dictionary. Cannot remove.");
        }
        dictionary.remove(key);
    }

    @Override
    public Map<K,V> getContent()
    {
        return this.dictionary;
    }

    @Override
    public void clear() {this.dictionary.clear();}

    @Override
    public MyIDictionary<K,V> deepCopy() throws MyException {
        MyDictionary<K, V> toReturn = new MyDictionary<>();
        for(Map.Entry<K,V> entry : dictionary.entrySet()){
            if(entry.getValue() instanceof IValue){
                toReturn.put(entry.getKey(),(V) ((IValue)entry.getValue()).deepCopy());
            } else {
                toReturn.put(entry.getKey(), entry.getValue());
            }
        }
        return toReturn;
    }
}
