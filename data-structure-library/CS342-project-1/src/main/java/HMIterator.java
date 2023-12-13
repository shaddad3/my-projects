import java.util.Iterator;
import java.util.ArrayList;

public class HMIterator<E> implements Iterator<E> {

    private ArrayList<GenericQueue<E>> map;
    Iterator<E> iter;
    private int index = 0;

    HMIterator(ArrayList<GenericQueue<E>> map) {
        //set this map to be equal to the passed in map, and set the iterator, iter
        this.map = map;
        this.iter = this.nextIterator();
    }

    private Iterator<E> nextIterator() {
        Iterator<E> it;
        while (this.index < 10) { //while we are between indices 0-9
            if (this.map.get(index) != null) {
                //if the index is not null, set the iterator to be equal to the GenericQueue iterator at this index and return it
                it = this.map.get(index).iterator();
                this.index++;
                return it;
            }
            this.index++;
        }

        return null; //if we are out of indices 0-9, our map is out of bounds
    }

    @Override
    public boolean hasNext() {
        if (this.iter.hasNext()) { //if the GenericQueue iterator has more values, return true
            return true;
        }

        //if not, set iter to the next non-null iterator in the map
        this.iter = this.nextIterator();
        if (this.iter != null) {
            return true;
        }

        return false; //if iter is set to null, our map is out of bounds
    }

    @Override
    public E next() {
        if (this.iter.hasNext()) { //if the GenericQueue iterator has more values, return the value of that iterators next() call
            return (iter.next());
        }

        //if not, set iter to the next non-null iterator in the map and return that iterators next() call
        this.iter = this.nextIterator();
        if (this.iter != null) {
            return (iter.next());
        }

        return null; //if iter is set to null, our map is out of bounds
    }

}
