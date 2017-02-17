/**
 * Created by lpason on 2017-02-11.
 */
public class Stock {
    private String[] stackArray;
    private int top;

    public Stock(int s) {
        stackArray = new String[s];
        top = -1;
    }
    public void push(String j) {
        stackArray[++top] = j;
    }
    public String pop() {
        return stackArray[top--];
    }
    public String peek() {
        return stackArray[top];
    }
    public boolean isEmpty() {
        return (top == -1);
    }
    public boolean isFull() {
        return (top == stackArray.length - 1);
    }

}