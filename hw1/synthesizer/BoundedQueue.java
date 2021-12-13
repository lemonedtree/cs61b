package synthesizer;

import java.util.Iterator;

/**
 * @author XD
 * @create 2021-12-12 15:49
 */
public interface BoundedQueue<T> extends Iterable<T> {
    Iterator<T> iterator();
    int capacity();     // return size of the buffer
    int fillCount();    // return number of items currently in the buffer
    void enqueue(T x);  // add item x to the end
    T dequeue();        // delete and return item from the front
    T peek();           // return (but do not delete) item from the front

    // is the buffer empty (fillCount equals zero)?
    default boolean isEmpty(){
        if (fillCount() == 0) {
           return true;
        }
        return false;
    }


    // is the buffer full (fillCount is same as capacity)?
    default boolean isFull(){
        if (fillCount() == capacity()) {
            return true;
        }
        return false;
    }

}
