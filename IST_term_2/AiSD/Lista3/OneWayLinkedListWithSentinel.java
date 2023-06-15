package Lista3;

import java.util.Iterator;
import java.util.ListIterator;

public class OneWayLinkedListWithSentinel<E> implements IList<E> {
    private class node {
        private E value;
        private node next;
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
        node(E data){
            this.value=data;
        }
    }
    node head = null;
    public OneWayLinkedListWithSentinel(){}

    private node getElement(int index){
        if(index<0) throw new IndexOutOfBoundsException();
        node actElem = head;
        while(index > 0 && actElem != null){
            index--;
            actElem=actElem.getNext();
        }
        if (actElem == null)
            throw new IndexOutOfBoundsException();
        return actElem;
    }

    @Override
    public boolean add(E e) {
        node newElem = new node(e);
        if(head == null){
            head = newElem;
            return true;
        }
        node tail = head;
        while(tail.getNext()!=null)
            tail = tail.getNext();
        tail.setNext(newElem);
        return true;
    }

    @Override
    public void add(int index, E element) {
        if(index < 0) throw new IndexOutOfBoundsException();
        node newElem = new node(element);
        if(index == 0){
            newElem.setNext(head);
            head = newElem;
            return;
        }
        node actElem=getElement(index-1);
        newElem.setNext(actElem.getNext());
        actElem.setNext(newElem);
    }

    @Override
    public void clear() {
        head=null;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element)>=0;
    }

    @Override
    public E get(int index) {
        node actElem=getElement(index);
        return actElem.getValue();
    }

    @Override
    public E set(int index, E element) {
        node actElem=getElement(index);
        E elemData=actElem.getValue();
        actElem.setValue(element);
        return elemData;
    }

    @Override
    public int indexOf(E element) {
        int pos = 0;
        node actElem = head;
        while(actElem != null){
            if(actElem.getValue().equals(element))
                return pos;
            pos++;
            actElem = actElem.getNext();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return head==null;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    @Override
    public E remove(int index) {
        if(index<0 || head == null) throw new IndexOutOfBoundsException();
        if(index == 0){
            E retValue = head.getValue();
            head = head.getNext();
            return retValue;}
        node actElem=getElement(index-1);
        if(actElem.getNext() == null)
            throw new IndexOutOfBoundsException();
        E retValue=actElem.getNext().getValue();
        actElem.setNext(actElem.getNext().getNext());
        return retValue;
    }

    @Override
    public boolean remove(E element) {
        if(head == null)
            return false;
        if(head.getValue().equals(element)){
            head=head.getNext();
            return true;}
        node actElem = head;
        while(actElem.getNext() != null && !actElem.getNext().getValue().equals(element))
            actElem=actElem.getNext();
        if(actElem.getNext() == null)
            return false;
        actElem.setNext(actElem.getNext().getNext());
        return true;
    }

    @Override
    public int size() {
        int pos=0;
        node actElem = head;
        while(actElem!=null){
            pos++;
            actElem=actElem.getNext();
        }
        return pos;
    }

    private class InnerListIterator implements ListIterator<E>{
        node actElem;

        public InnerListIterator() {
            this.actElem = head;
        }

        @Override
        public boolean hasNext() {
            return actElem!=null;
        }

        @Override
        public E next() {
            E element = actElem.getValue();
            actElem = actElem.getNext();
            return element;
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException();
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
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }
}


