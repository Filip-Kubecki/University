package Lista2;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Series<E> implements Iterable<E> {
    E[] array;

    public Series() {}

    public E[] getArray() {
        return array;
    }

    public void setArray(E[] array) {
        this.array = array;
    }

    @Override
    public Iterator<E> iterator() {
        return new SeriesIterator<>(array);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Iterable.super.spliterator();
    }
}
