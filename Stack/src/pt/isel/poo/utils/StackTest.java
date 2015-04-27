package pt.isel.poo.utils;

public class StackTest {
    public static void main(String[] args) {
        //try {
            Stack<Integer> stk = new Stack<Integer>(8);
            for (int v : new int[]{10, 48, 127, 0, -30})
                stk.push(v);
            while (!stk.isFull())
                stk.push(0);
            System.out.println(stk.top());
            int x = stk.pop();  // Unboxing
            System.out.println("x="+x);
            while (!stk.isEmpty())
                System.out.println(stk.pop());
        /*} catch (StackFull sf) {
           System.out.println("Cheio");
        } catch (StackException ex) {
            System.out.println(ex);
        }*/
        System.out.println("FIM");
    }
}
