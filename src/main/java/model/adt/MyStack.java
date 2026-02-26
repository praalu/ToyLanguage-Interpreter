package model.adt;

import java.util.Stack;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> tail;
    public MyStack()
    {
        this.tail = new Stack<>();
    }

    @Override
    public void push(T element) {
        this.tail.push(element);
    }

    @Override
    public T pop() {
        return this.tail.pop();
    }

    @Override
    public boolean isEmpty() {
        return this.tail.isEmpty();
    }

    @Override
    public String toString() {
        List<T> safeList = new ArrayList<>(this.tail);

        Collections.reverse(safeList);

        return safeList.toString();
    }

    @Override
    public void clear() {this.tail.clear();}

    @Override
    public List<T> getStack() {
        return new ArrayList<>(this.tail);
    }

}
