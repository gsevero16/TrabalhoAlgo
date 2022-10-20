package src;

import java.util.EmptyStackException;

public class LinkedStackCalculator {
    private class Node {

        public Integer element;
        public Node next;

        /**
         * Instantiates a new Node.
         *
         * @param element the element
         */
        public Node(Integer element) { // constructor without next
            this.element = element;
            next = null;
        }

        /**
         * Instantiates a new Node.
         *
         * @param element the element
         * @param next    the next node
         */
        public Node(Integer element, Node next) { // constructor with next
            this.element = element;
            this.next = next;
        }
    }

    private Node top; // top of the stack
    private int count; // number of elements in the stack

    /**
     * Instantiates a new Linked stack calculator.
     */
    public LinkedStackCalculator() {
        top = null; 
        count = 0;
    }


    /**
     * Pop integer.
     *
     * @return the integer
     */
    public Integer pop(){
        if(count == 0){
            throw new EmptyStackException(); // if stack is empty, throw empty stack error
        }

        count--;
        Integer aux = top.element; // stores removed element so it can be returned
        top = top.next; // second element is now the top of the stack

        return aux; // returns removed (popped) element
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty(){
        return count == 0;
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size(){
        return count;
    }

    /**
     * Top integer.
     *
     * @return the integer
     */
    public Integer top(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return top.element;
    }

    /**
     * Push element into stack.
     *
     * @param e the element to push
     */
    public void push (Integer e){
        Node aux = new Node(e);
        aux.next = top;
        top = aux;
        count++;
    }


    /**
     * Clear stack.
     */
    public void clear(){
        top = null;
        count = 0;
    }


}

