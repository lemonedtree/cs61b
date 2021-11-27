public class LinkedListDeque<T> {
    private int size = 0;
    private Node sentF;
    private Node sentB;

    public LinkedListDeque(){
        size = 0;
        sentB = new Node(null,null,null);
        sentF = new Node(null,null,sentB);
        sentB.prev = sentF;
    }

    private class Node{
        private Node prev;
        private Node last;
        private T item;
        private Node(){
        }
        private Node(Node prev,T item,Node last){
            this.last = last;
            this.prev = prev;
            this.item = item;
        }
    }

    /**Adds an item of type T to the front of the deque.
     * @param item*/
    public void addFirst(T item) {
        //must not involve any looping or recursion.
        // A single such operation must take “constant time”,
        // i.e. execution time should not depend on the size of the deque.
        Node target = new Node(sentF,item,sentF.last);
        sentF.last.prev = target;
        sentF.last = target;
        size++;
    }
    /**
     * Adds an item of type T to the back of the deque.
     * @param item
     */
    public void addLast(T item) {
        //must not involve any looping or recursion.
        // A single such operation must take “constant time”,
        // i.e. execution time should not depend on the size of the deque.
        Node target = new Node(sentB.prev,item,sentB);
        sentB.prev.last = target;
        sentF.prev = target;
        size++;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     * @return boolean
     */
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items in the deque.
     * @return int
     */
    public int size() {
        //must take constant time.
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque(){
        Node p = sentF.last;
        while(p != sentB){
            System.out.print(p.item +" ");
            p = p.last;
        }
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return T
     */
    public T removeFirst() {
        //must not involve any looping or recursion.
        // A single such operation must take “constant time”,
        // i.e. execution time should not depend on the size of the deque.
        if (size == 0){
            return null;
        }
        Node result = sentF.last;
        sentF.last = result.last;
        result.last.prev = sentF;
        size--;
        return result.item;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return T
     */
    public T removeLast() {
        //must not involve any looping or recursion.
        // A single such operation must take “constant time”,
        // i.e. execution time should not depend on the size of the deque.
        if (size == 0){
            return null;
        }
        Node result = sentB.prev;
        sentB.prev = result.prev;
        result.prev.last = sentB;
        size--;
        return result.item;
    }
    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * @param index
     * @return T
     */
    public T get(int index){
        //must use iteration, not recursion
        if (index >= size || index < 0){
            return null;
        }

        if (index < (size/2)){
            Node p = sentF;
            for (int i = 0; i <= index; i++) {
                p = p.last;
            }
            return p.item;
        }
        else{
            Node p = sentB;
            for (int i = size-1; i >= index; i--) {
                p = p.prev;
            }
            return p.item;
        }
    }

    public T getRecursive(int index) {
        //Same as get, but uses recursion.
        if (index >= size || index < 0){
            return null;
        }
        if(index == 0){
            return sentF.last.item;
        }
        this.removeFirst();
        return this.getRecursive(--index);
    }
}
