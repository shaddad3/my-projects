import java.util.ArrayList;
import java.util.Iterator;

abstract class GenericList<T> implements Iterable<T> {

    private Node<T> head = new Node<>();
    private int length;

    @Override
    public Iterator<T> iterator() { //return an iterator that knows how to iterate over our GenericList
        return (new GLLIterator<>(this.head));
    }

    public void print() {
        Node<T> temp = this.head; //create a temporary node, temp, and set it equal to head

        if (temp == null) { //if temp is null, the list is empty
            System.out.println("Empty List");
            return;
        }
        while (temp != null) { //while temp is not null, print out its data and advance it
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    abstract void add(T data); //create an abstract method add, which is defined in GenericQueue

    public abstract T delete(); //create an abstract method delete, which is defined in GenericQueue

    public ArrayList<T> dumpList() {
        //create a new ArrayList and a temporary node, temp, set to the head of the list
        ArrayList<T> list = new ArrayList<>();
        Node<T> temp = this.head;

        while (temp != null) { //while temp is not null, add the value stored into the list and advance it
            list.add(temp.data);
            temp = temp.next;
        }

        return list;
    }

    public T get(int index) {
        //create an index variable and a temporary node, temp, set to the head of the list
        Node<T> temp = this.head;
        int currIndex = 0;

        while (temp != null) {
            //if you find the index you want in the list,
            //return the data at this index
            if (index == currIndex) {
                return (temp.data);
            }
            else { //if not found, move head and currIndex to keep searching
                temp = temp.next;
                currIndex++;
            }
        }
        //if the index is out of bounds, return null
        return null;
    }

    public T set(int index, T element) {
        //create an index variable and a temporary node, temp, set to the head of the list
        Node<T> temp = this.head;
        int currIndex = 0;
        while (temp != null) {
            //if you find the index you want in the list, store the data
            //currently there in a variable, replace the current data with
            //the new data, and return the prev value at this index
            if (index == currIndex) {
                T prev = temp.data;
                temp.data = element;
                return prev;
            }
            else { //if not found, move head and currIndex to keep searching
                temp = temp.next;
                currIndex++;
            }
        }
        //if the index is out of bounds, return null
        return null;
    }

    public void setHead(Node<T> head) { //set the head of the GenericList
        this.head = head;
    }

    public void setLength(int length) { //set the length of the GenericList
        this.length = length;
    }

    public Node<T> getHead() { //return the head of the GenericList
        return (this.head);
    }

    public int getLength() { //return the length of the GenericList
        return (this.length);
    }

    public Iterator<T> descendingIterator() { //return an iterator that knows how to iterate over our GenericList in reverse
        return (new ReverseGLLIterator<>(this));
    }

    static class Node<T> { //the Node class, which is what our GenericList and GenericQueue are built of
        T data;
        int code;
        Node<T> next;
    }

}
