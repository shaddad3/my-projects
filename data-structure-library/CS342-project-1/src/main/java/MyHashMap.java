import java.util.Iterator;
import java.util.ArrayList;

public class MyHashMap<T> implements Iterable<T> {

    private ArrayList<GenericQueue<T>> map = new ArrayList<>(10);
    private int mapSize = 0;

    //This default constructor is used nowhere, but since we created a parameterized constructor
    //it is good practice to create a default constructor as well.
    MyHashMap() {
        for (int i = 0; i < 10; i++) { //initialize the map to contain 10 empty GenericQueue's
            this.map.add(i, null);
        }
    }

    MyHashMap(String key, T value) {
        for (int i = 0; i < 10; i++) { //initialize the map to contain 10 empty GenericQueue's
            this.map.add(i, null);
        }
        this.put(key, value); //add this key-value pair to the map
    }

    @Override
    public Iterator<T> iterator() {
        return (new HMIterator<>(this.map)); //return an iterator that knows how to iterate over our Hash Map
    }

    public void put(String key, T value) {
        //get the hash value and hash index of the key
        int hash = key.hashCode();
        int index = key.hashCode() & 9;

        //if this key already exists in the map, replace the old value with this new value
        if (this.replace(key, value) != null) {
            return;
        }

        //no GenericQueue at this index, so make a new one, set its data and code fields, and add it to the map
        if (this.map.get(index) == null) {
            GenericQueue<T> temp = new GenericQueue<>(value);
            temp.getHead().code = hash;
            this.map.set(index, temp);
        }

        //there is already a GenericQueue at this index, so add this value and hash code with add(T data, int code)
        else {
            GenericQueue<T> temp = this.map.get(index);
            temp.add(value, hash);
        }

        this.mapSize++; //increment the map size
    }

    public boolean contains(String key) {
        //get the hash value and hash index of the key
        int hash = key.hashCode();
        int index = key.hashCode() & 9;

        //there is a GenericQueue at this index, so search each node to see if the code data field matches
        if (this.map.get(index) != null) {
            GenericQueue<T> queue = this.map.get(index);
            GenericList.Node<T> temp = queue.getHead();

            while (temp != null) { //search the queue to see if the code data field matches the hash value
                if (temp.code == hash) {
                    return true; //if found, return true as the key is in the map
                }
                temp = temp.next;
            }
        }

        return false; //no GenericQueue at this index, so the key is not in the map
    }

    public T get(String key) {
        //get the hash value and hash index of the key
        int hash = key.hashCode();
        int index = key.hashCode() & 9;

        //there is a GenericQueue at this index, so search each node to see if the code data field matches
        if (this.map.get(index) != null) {
            GenericQueue<T> queue = this.map.get(index);
            GenericList.Node<T> temp = queue.getHead();

            while (temp != null) { //search the queue to see if the code data field matches the hash value
                if (temp.code == hash) {
                    return temp.data; //if found, return the value of this key
                }
                temp = temp.next;
            }
        }

        return null; //no GenericQueue at this index, so the key is not in the map
    }

    public int size() { //return the mapSize
        return (this.mapSize);
    }

    public boolean isEmpty() {
        //mapSize keeps track of the # of key-value pairs, so if it's 0, the map is empty
        return (this.mapSize == 0);
    }

    public T replace(String key, T value) {
        //get the hash value and hash index of the key
        int hash = key.hashCode();
        int index = key.hashCode() & 9;

        //there is a GenericQueue at this index, so search each node to see if the code data field matches
        if (this.map.get(index) != null) {
            GenericQueue<T> queue = this.map.get(index);
            GenericList.Node<T> temp = queue.getHead();

            while (temp != null) { //search the queue to see if the code data field matches the hash value
                if (temp.code == hash) {
                    //if found, store this old value, replace it with the new value, and return the old value
                    T result = temp.data;
                    temp.data = value;
                    return result;
                }
                temp = temp.next;
            }
        }
        return null; //no GenericQueue at this index, so the key is not in the map
    }

}
