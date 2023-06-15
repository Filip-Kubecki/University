package Lista2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SeriesIterator<E> implements Iterator<E> {
    private final E[] array;
    private int position = 0;

    public SeriesIterator(E[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return position < array.length;
    }

    @Override
    public E next() throws NoSuchElementException {
        if (hasNext())
            return array[position++];
        else
            throw new NoSuchElementException();
    }
}
