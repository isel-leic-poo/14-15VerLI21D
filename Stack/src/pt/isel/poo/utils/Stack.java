package pt.isel.poo.utils;

public class Stack<T> {
    private T[] array;
    private int length;

    @SuppressWarnings("unchecked")
    public Stack(int cap) {
        length = 0;
        array = (T[]) new Object[cap];
    }

    public void push(T elem) {
        try {
            array[length] = elem;
            ++length;
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new StackFull(array.length);
        }
    }

    public boolean isFull() {
        return array.length == length;
    }

    public boolean isEmpty() {
        return length==0;
    }

    public T pop() {
        try {
            return array[--length];
        } catch(ArrayIndexOutOfBoundsException ex) {
            throw new StackEmpty();
        }
    }

    public T top() {
        return array[length-1];
    }
}
