package Lista3;

import java.util.Iterator;
import java.util.ListIterator;

public class TwoWayLinkedListWithSentinel<E> implements IList<E>{
    private class node {
        private E value;
        private node next;
        private node prev;
        public E getValue() {
            return value;
        }
        public void setValue(E value) {
            this.value = value;
        }
        public node getNext() {
            return next;
        }
        public void setNext(node next) {
            this.next = next;
        }
        public node getPrev() {
            return prev;
        }
        public void setPrev(node prev) {
            this.prev = prev;
        }
        node(E data){
            this.value=data;
        }
        public void insertAfter(node elem){
            elem.setNext(this.getNext());
            elem.setPrev(this);
            this.getNext().setPrev(elem);
            this.setNext(elem);
        }
        public void insertBefore(node elem){
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }
        public void remove(){
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }
    node head = null;
    node tail = null;

    public TwoWayLinkedListWithSentinel() {
        head = new node(null);
        tail = new node(null);
        head.setNext(tail);
        tail.setPrev(head);
    }
    private node getElement(E value) {
        node tempElement = head.getNext();
        while (tempElement != head && !value.equals(tempElement.getValue())){
            tempElement = tempElement.getNext();
        }
        if (tempElement == head)
            return null;
        return tempElement;
    }
    private node getElement(int index){
        node tempElement = head.getNext();
        int counter = 0;
        while(tempElement != head && counter < index){
            counter++;
            tempElement = tempElement.getNext();
        }
        if(tempElement == head)
            throw new IndexOutOfBoundsException();
        return tempElement;
    }

    @Override
    public boolean add(E element) {
        node newNode = new node(element);
        tail.insertBefore(newNode);
        return true;
    }
    @Override
    public void add(int index, E element) {
        node newElement = new node(element);
        if(index == 0)
            head.insertAfter(newElement);
        else{
            node tempElement = getElement(index-1);
            tempElement .insertAfter(newElement);
        }
    }

    @Override
    public void clear() {
        head.setNext(tail);
        tail.setPrev(head);
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E get(int index) {
        node tempElement = getElement(index);
        return tempElement.getValue();
    }

    @Override
    public E set(int index, E element) {
        node elem = getElement(index);
        E retValue = elem.getValue();
        elem.setValue(element);
        return retValue;
    }

    @Override
    public int indexOf(E element) {
        node tempElement = head.getNext();
        int counter = 0;
        while(tempElement != head && !tempElement.getValue().equals(element)){
            counter++;
            tempElement=tempElement.getNext();
        }
        if(tempElement == head)
            return -1;
        return counter;
    }

    @Override
    public boolean isEmpty() {
        return this.head.getNext() == tail;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator();
    }

    @Override
    public E remove(int index) {
        node tempElement = getElement(index);
        tempElement.remove();
        return tempElement.getValue();
    }

    @Override
    public boolean remove(E element) {
        node tempElement = getElement(element);
        if(tempElement == null)
            return false;
        tempElement.remove();
        return true;
    }

    @Override
    public int size() {
        node elem = head.getNext();
        int counter = 0;
        while(elem != tail){
            counter++;
            elem = elem.getNext();
        }
        return counter;
    }

    private class CustomListIterator implements ListIterator<E>{

        boolean wasNext = false;
        boolean wasPrevious = false;
        node _current = head;

        @Override
        public boolean hasNext() {
            return _current.getNext() != tail;
        }

        @Override
        public boolean hasPrevious() {
            return _current != head;
        }

        @Override
        public E next() {
            wasNext = true;
            wasPrevious = false;
            _current = _current.getNext();
            return _current.getValue();
        }

        @Override
        public E previous() {
            wasNext=false;
            wasPrevious=true;
            E retValue=_current.getValue();
            _current=_current.getPrev();
            return retValue;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E data) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E data) {
            throw new UnsupportedOperationException();
        }
    }
}
