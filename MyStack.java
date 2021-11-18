/**
 * @author Dhyey Patel
 *
 * This class is made to create a stack of a generaic datatype using an array
 */

public class MyStack<T> {
    private T[] arrayStack;
    private int numItems=0, size;

    // This is the constructor of the class it will create a stack of size 10
    public MyStack() {
        arrayStack = (T[]) new Object[10];
        size = 10;
    }

    // This method is used to add something to the stack
    public void push (T dataItem){
        // If the stack is full then expand it using the helper function
        if(numItems+1>size) {
            expand();
        }
        // Insert the item and increase numItems
        arrayStack[numItems] = dataItem;
        numItems++;
    }

    // This method is used to remove something from the stack return it
    public T pop (){
        // Decrease numItems and return the value stored at it
        numItems--;
        return(arrayStack[numItems]);

    }

    // This method is used to check if the stack is empty
    public boolean isEmpty() {
        // If numItems is equal to 0 then it is empty otherwise it is not
        if (numItems == 0) {
            return true;
        }
        return false;
    }

    // This is a private helper function that will expand the size of stack
    private void expand() {
        // It will create a new array that is double the size
        T[] largeArrayStack;
        size = size*2;
        largeArrayStack = (T[])new Object[size];
        // Move everything into the new array
        for (int i=0; i<arrayStack.length; i++) {
            largeArrayStack[i] = arrayStack[i];
        }
        arrayStack = largeArrayStack;
    }
}
