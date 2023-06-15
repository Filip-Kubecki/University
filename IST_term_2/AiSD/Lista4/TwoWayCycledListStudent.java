package Lista4;

import Lista4.Generic.List;

import java.util.Iterator;
import java.util.ListIterator;

public class TwoWayCycledListStudent<Student> implements List<Student>{
    private class node {
        private Student value;
        private node next;
        private node prev;
        private int key;
        public node(Student value) {
            this.value = value;
            this.key = ((Lista4.Student)value).getYearOfUniversity();
        }

        public Student getValue() {
            return value;
        }

        public void setValue(Student value) {
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

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public void insertAfter(node element){
            element.setNext(this.getNext());
            element.setPrev(this);
            this.getNext().setPrev(element);
            this.setNext(element);
        }
        public void inserBefore(node element){
            element.setNext(this);
            element.setPrev(this.getPrev());
            this.getPrev().setNext(element);
            this.setPrev(element);
        }
        public void remove(){
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }
    private node sentinel = null;

    public TwoWayCycledListStudent() {
        sentinel = new node(null);
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    @Override
    public boolean isEmpty() {
        return sentinel.getNext() == sentinel;
    }

    @Override
    public Iterator<Student> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<Student> listIterator() {
        return new InnerListIterator();
    }

    @Override
    public Student returnOnKey(int key) {
        if (sentinel.key == key)
            return sentinel.value;

        node tail = sentinel;
        while(tail.next != null && tail.next.key != key)
            tail = tail.getNext();

        if (tail.next == null){
            return null;
        }
        return tail.next.getValue();
    }

    @Override
    public Student removeOnKey(int key) {
        Student out = sentinel.getValue();
        if(sentinel.getKey() == key){
            sentinel = sentinel.next;
            return out;
        }
        node tail = sentinel;
        while(tail.next != null && tail.next.getKey() != key)
            tail = tail.getNext();
        if (tail.next != null){
            out = tail.next.value;
            tail.next.remove();
            return out;
        }
        return null;
    }

    @Override
    public void clear() {
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    @Override
    public boolean add(Student element) {
        node newElem = new node(element);
        sentinel.inserBefore(newElem);
        return true;
    }

    @Override
    public void addAtBeginning(Student element) {
        node newElem = new node(element);
        sentinel.insertAfter(newElem);
    }

    @Override
    public void addBefore(int key, Student element) {
        node newElem = new node(element);
        if(sentinel == null){
            sentinel = newElem;
            return;
        }
        if(sentinel.getKey() == key){
            node temp = sentinel;
            sentinel = newElem;
            sentinel.setNext(temp);
            return;
        }

        node tail = sentinel;
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
            sentinel.setNext(newElem);
        }
    }
    @Override
    public void addAfter(int key, Student element) {
        node newElem = new node(element);
        if(sentinel == null){
            sentinel = newElem;
            return;
        }
        node tail = sentinel;
        while(tail != null && tail.getKey() != key)
            tail = tail.getNext();
        newElem.setNext(tail.next);
        tail.setNext(newElem);
    }

    private class InnerIterator implements Iterator<Student>{
        node _current = sentinel;
        @Override
        public boolean hasNext() {
            return _current != sentinel;
        }

        @Override
        public Student next() {
            _current=_current.getNext();
            return _current.getValue();
        }
    }

    private class InnerListIterator implements ListIterator<Student>{

        boolean wasNext = false;
        boolean wasPrevious = false;
        node _current = sentinel;


        @Override
        public boolean hasNext() {
            return _current.getNext() != sentinel;
        }

        @Override
        public boolean hasPrevious() {
            return _current != sentinel;
        }

        @Override
        public Student next() {
            wasNext = true;
            wasPrevious = false;
            _current = _current.getNext();
            return _current.getValue();
        }

        @Override
        public Student previous() {
            wasNext=false;
            wasPrevious=true;
            Student retValue=_current.getValue();
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
            if(wasNext){
                node curr=_current.getPrev();
                _current.remove();
                _current=curr;
                wasNext=false;
            }
            if(wasPrevious){
                _current.getNext().remove();
                wasPrevious=false;
            }
        }

        @Override
        public void add(Student data) {
            node newElem = new node(data);
            _current.insertAfter(newElem);
            _current=_current.getNext();
        }

        @Override
        public void set(Student data) {
            if(wasNext){
                _current.setValue(data);
                wasNext=false;
            }
            if(wasPrevious){
                _current.getNext().setValue(data);
                wasNext=false;
            }
        }
    }
}
