public class ArrayDeque<T> {
    private int size = 0;
    private T[] items = (T[]) new Object[8];
    private int nextFirst = 4;
    private int nextLast = 5;

    public ArrayDeque() {
        //Creates an empty array deque
    }

    private void resize(int eLength) {
        T[] eItems = (T[]) new Object[eLength];

        //复制了以后，nextFirst 去了第一个位置,即 0
        for (int i = 1, j = toFirst(); i <= size; i++) {

            //copy the item
            eItems[i] = items[j];

            //to the next item
            if (j == items.length - 1) {
                j = 0;
            } else {
                j++;
            }
        }
        items = eItems;
        nextFirst = 0;
        nextLast = size + 1;
    }


    /**Adds an item of type T to the front of the deque.
     * @param item*/
    public void addFirst(T item) {
        // take constant time, except during resizing operations.
        items[nextFirst] = item;
        size++;

        //resize
        if (items.length == size + 2) {
            this.resize(size * 2);
        }

        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }
    }

    /**
     * Adds an item of type T to the back of the deque.
     * @param item
     */
    public void addLast(T item) {
        // take constant time, except during resizing operations.
        items[nextLast] = item;

        size++;

        //resize
        if (items.length == size + 2) {
            this.resize(size * 2);
        }

        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast++;
        }
    }

    /**
     * Returns true if deque is empty, false otherwise.
     * @return boolean
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items in the deque.
     * @return int
     */
    public int size() {
       // take constant time.
        return size;
    }

    private int toFirst() {
        int first;
        if (nextFirst == items.length - 1) {
            first = 0;
        } else {
            first = nextFirst + 1;
        }
        return first;
    }

    private int toLast() {
        int last;
        if (nextLast == 0) {
            last = items.length - 1;
        } else {
            last = nextLast - 1;
        }
        return last;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        while (true) {
            int i = toFirst();
            System.out.println(items[i] + " ");
            if (i == items.length - 1) {
                i = 0;
            } else {
                i++;
            }

            if (i == toLast()) {
                break;
            }
        }
    }


    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return T
     */
    public T removeFirst() {
        // take constant time, except during resizing operations.
        if (isEmpty()) {
            return null;
        }
        T target = items[toFirst()];
        items[toFirst()] = null;

        //改变nextfirst
        nextFirst = toFirst();

        //downsize
        if ((size * 1.0 / (items.length)) < 0.25) {
            resize(size / 2);
        }

        return target;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return T
     */
    public T removeLast() {
        // take constant time, except during resizing operations.
        if (isEmpty()) {
            return null;
        }
        T target = items[toLast()];
        items[toLast()] = null;

        //改变nextLast
        nextLast = toLast();

        //downsize
        if (size * 1.0 / (items.length) < 0.25) {
            resize(size / 2);
        }

        return target;
    }
    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * @param index
     * @return T
     */
    public T get(int index) {
        //take constant time.
        if (index >= size) {
            return null;
        }
        if (nextFirst + index + 1 <= items.length - 1) {
            return items[nextFirst + index + 1];
        } else {
            return items[index - items.length + nextFirst + 1];
        }
    }
}
