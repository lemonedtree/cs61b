package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
    //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    @Override
    public Iterator<T> iterator() {
        return new ARIterator();
    }
//目的是能让她从头到尾走一遍
    private class ARIterator implements Iterator {
        private int p;
        public ARIterator() {
            p = first;
        }

        @Override
        public boolean hasNext() {
            if (p == last) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            T result = rb[p];
            if (p == capacity - 1) {
                p = 0;
            } else {
                p++;
            }
            return result;
        }
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        fillCount++;
        if (last == capacity - 1) {
            last = 0;
        } else {
            last++;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (fillCount == 0){
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T toDequeue = rb[first];
        fillCount--;
        if (first == 0) {
            first = capacity - 1;
        } else {
            first--;
        }
        return toDequeue;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        return rb[first];
    }
}
