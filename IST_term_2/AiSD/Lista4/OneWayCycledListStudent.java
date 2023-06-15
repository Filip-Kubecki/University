package Lista4;

import Lista4.Generic.List;
import java.util.Iterator;
import java.util.ListIterator;

public class OneWayCycledListStudent<E> implements List<E>{

    private class node{
        private E value;
        private node next;
        private int key;

        public node(E value) {
            this.value = value;
            this.key = ((Student)value).getYearOfUniversity();
        }

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

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }
    }

    private node head = null;

    public OneWayCycledListStudent() {}

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public E returnOnKey(int key) {
        if (head.key == key)
            return head.value;

        node tail = head;
        while(tail.next != null && tail.next.key != key && tail.next != head)
            tail = tail.getNext();

        if (tail.next == head){
            return null;
        }
        return tail.next.getValue();
    }

    @Override
    public E removeOnKey(int key) {
        E out = head.getValue();
        if(head.getKey() == key){
            head = head.next;
            return out;
        }
        node tail = head;
        while(tail.next != null && tail.next.getKey() != key)
            tail = tail.getNext();
        if (tail.next != null){
            out = tail.next.value;
            tail.setNext(tail.next.next);
            return out;
        }
        return null;
    }

    @Override
    public void clear() {
        head = null;
    }
    @Override
    public boolean add(E element) {
        node newElem = new node(element);
        if(head == null){
            head = newElem;
            head.setNext(head);
            return true;
        }
        node tail = head;
        while(tail.getNext() != head)
            tail = tail.getNext();
        tail.setNext(newElem);
        newElem.setNext(head);
        return true;
    }

    @Override
    public void addAtBeginning(E element) {
        node newElem = new node(element);
        if(head == null){
            head = newElem;
            head.setNext(head);
            return;
        }
        node tail = head;
        while(tail.getNext() != head)
            tail = tail.getNext();

        node temp = head;
        head = newElem;
        newElem.setNext(temp);
        tail.setNext(newElem);
    }

    @Override
    public void addBefore(int key, E element) {
        node newElem = new node(element);
        if(head == null){
            head = newElem;
            return;
        }
        node tail = head;
        node prev = null;
        while(tail.next != null && tail.next.getKey() != key){
            prev = tail;
            tail = tail.getNext();
        }
        if (prev != null){
            newElem.setNext(tail.next);
            prev.next.setNext(newElem);
        }else{
            newElem.setNext(tail.next);
            head.setNext(newElem);
        }
    }
    @Override
    public void addAfter(int key, E element) {
        node newElem = new node(element);
        if(head == null){
            head = newElem;
            return;
        }
        node tail = head;
        while(tail != null && tail.getKey() != key)
            tail = tail.getNext();
        newElem.setNext(tail.next);
        tail.setNext(newElem);
    }

    private class InnerIterator implements Iterator<E>{
        node actElem;
        public InnerIterator() {
            actElem = head;
        }
        @Override
        public boolean hasNext() {
            return actElem.next != head;
        }
        @Override
        public E next() {
            E value=actElem.getValue();
            actElem=actElem.getNext();
            return value;
        }
    }
}