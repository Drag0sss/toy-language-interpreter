package model.adt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private final Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {

        Stack<T> newStack = new Stack<>();
        Stack<T> copyStack = new Stack<>();
        copyStack.addAll(stack);
        while (!copyStack.isEmpty()) {
            newStack.push(copyStack.pop());
        }
        return newStack.toString();
    }

    @Override
    public List<T> getStackAsList() {
        return new ArrayList<>(stack);
    }

}
