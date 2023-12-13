import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReverseGLLIterator<E> implements Iterator<E> {

    private GenericList.Node<E> head;
    private int length;

    ReverseGLLIterator(GenericList<E> list) { //set the head node to be equal to the passed in list's head
        this.head = list.getHead();
        this.length = list.getLength(); //store the length of the list
    }

    @Override
    public boolean hasNext() { //as long as the length is > 0, there are still values in the list
        if (this.length > 0) {
            return true;
        }
        return false;
    }

    @Override
    public E next() {
        if (!hasNext()) { //return null if there are no more values in the list
            return null;
        }
        GenericList.Node<E> prev = this.head;
        for (int i = 0; i < length - 1; i++) { //move the temporary node prev to the node at length - 1
            prev = prev.next;
        }
        //decrement the length for the next iteration, and return the data found from the last node in the list
        this.length--;
        return (prev.data);
    }

}
