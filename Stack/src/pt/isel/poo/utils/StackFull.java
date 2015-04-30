package pt.isel.poo.utils;


public class StackFull extends StackException {
    private int capacity;
    public StackFull(int cap) {
        capacity = cap;
    }
    @Override
    public String toString() {
        return super.toString()+": "+capacity;
    }
}
