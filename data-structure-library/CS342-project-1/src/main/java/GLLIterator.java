import java.util.Iterator;

public class GLLIterator<E> implements Iterator<E> {

    private GenericList.Node<E> head;

    GLLIterator(GenericList.Node<E> head) { //set the passed in head as the head for the iterator
        this.head = head;
    }

    @Override
    public boolean hasNext() {
        if (this.head != null) { //as long as the head is not null, there are more values in the list
            return true;
        }
        return false;
    }

    @Override
    public E next() {
        if (this.head == null) { //if the list is empty, return null
            return null;
        }
        //store the data in a variable, advance the head to the next node, and return that stored variable
        E result = this.head.data;
        this.head = this.head.next;
        return result;
    }

}
