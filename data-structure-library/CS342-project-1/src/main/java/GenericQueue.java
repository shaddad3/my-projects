import java.util.Iterator;

public class GenericQueue <T> extends GenericList<T> {

    private Node<T> tail;

    //This default constructor is used nowhere, but since we created a parameterized constructor
    //it is good practice to create a default constructor as well.
    GenericQueue() {
        Node<T> head = new Node<>();
        this.setHead(head);
        this.tail = head;
        this.setLength(0);
    }

    GenericQueue(T val) {
        //create a new node, set its data to the passed in value, and set the head of the list to be this node
        Node<T> head = new Node<>();
        head.data = val;
        this.setHead(head);
        //head and tail both point to the first node in the list, since it is both the front and back of the list
        this.tail = head;
        this.setLength(1);
    }

    @Override
    void add(T data) { //add new node to the tail, which is the back of the queue
        Node<T> newNode = new Node<>();
        newNode.data = data;

        if (this.getHead() == null) { //add new node to the front of the queue
            this.setHead(newNode);
        }
        else { //add new node to the back of the queue (the tail)
            this.tail.next = newNode;
        }

        //update the tail to point to this new node, and increment and set the length
        this.tail = newNode;
        int length = this.getLength();
        this.setLength(++length);
    }

    @Override
    public T delete() { //since new nodes are added to the tail (back of list), we delete from the head (front of list)
        Node<T> temp = this.getHead();
        if (temp == null) { //if the head is null, there is nothing to delete, so return null
            return null;
        }
        //store the result at this node, and then advance it and set the head to the advanced node
        T result = temp.data;
        temp = temp.next;
        this.setHead(temp);
        //decrement and set the new length, and then return the stored value
        int length = this.getLength();
        this.setLength(--length);
        return result;
    }

    void add(T data, int code) { //same as add(T data), with one addition line to set the code data field
        Node<T> newNode = new Node<>();
        newNode.data = data;
        newNode.code = code; //only difference from add(T data) method

        if (this.getHead() == null) { //add new node to the front of the queue
            this.setHead(newNode);
        }
        else { //add new node to the back of the queue (the tail)
            this.tail.next = newNode;
        }

        //update the tail to point to this new node, and increment and set the length
        this.tail = newNode;
        int length = this.getLength();
        this.setLength(++length);
    }

    public void enqueue(T data) {
        //enqueue is expected in a queue data structure, and it functions the same as add, so call add(T data)
        add(data);
    }

    public T dequeue() {
        //dequeue is expected in a queue data structure, and it functions the same as delete, so call delete()
        return delete();
    }

}
