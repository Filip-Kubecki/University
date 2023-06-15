package Lista4;

import Lista4.Generic.List;

import java.util.Iterator;
import java.util.ListIterator;

public class TwoWayLinkedListStudent<Student> implements List<Student>{
    private class node {
        private Student value;
        private node next;
        private node prev;
        private int key;

        public node(Student value) {
            this.value = value;
            this.key = ((Lista4.Student) value).getYearOfUniversity();
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
            if (this.next != null && this.prev != null){
                element.setPrev(this);
                element.setNext(this.next);
                this.setNext(element);
            } else if (this.next == null && this.prev != null) {
                element.setPrev(this);
                this.setNext(element);
            }else{
                element.setNext(this.next);
                this.setNext(element);
            }
        }
        public void inserBefore(node element){
            if (this.next != null && this.prev != null){
                element.setNext(this);
                element.setPrev(this.prev);
                this.setPrev(element);
            } else if (this.next == null && this.prev != null) {
                element.setPrev(this.prev);
                this.setPrev(element);
            }else{
                element.setNext(this);
                this.setPrev(element);
            }
        }
        public void remove(){
            if (this.next != null && this.prev != null){
                this.prev.setNext(this.next);
                this.next.setPrev(this.prev);
            } else if (this.next == null && this.prev != null) {
                this.prev.setNext(this.next);
            }else
                this.next.setPrev(this.prev);
        }
    }

    private node head = null;

    public TwoWayLinkedListStudent() {}

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Iterator<Student> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<Student> listIterator() {
        return null;
    }

    @Override
    public Student returnOnKey(int key) {
        if (head.key == key)
            return head.value;

        node tail = head;
        while(tail.next != null && tail.next.key != key)
            tail = tail.getNext();

        if (tail.next == null){
            return null;
        }
        return tail.next.getValue();
    }

    @Override
    public Student removeOnKey(int key) {
        Student out = head.getValue();
        if(head.getKey() == key){
            head = head.next;
            return out;
        }
        node tail = head;
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
        head = null;
    }

    @Override
    public boolean add(Student element) {
        node newElem = new node(element);
        if(head == null){
            head = newElem;
            return true;
        }
        node tail = head;
        while(tail.getNext() != null)
            tail = tail.getNext();
        tail.insertAfter(newElem);
        return true;
    }

    @Override
    public void addAtBeginning(Student element) {
        node newElem = new node(element);
        if(head == null){
            head = newElem;
            return;
        }
        node temp = head;
        head = newElem;
        head.setNext(temp);
    }

    @Override
    public void addBefore(int key, Student element) {
        node newElem = new node(element);
        if(head == null){
            head = newElem;
            return;
        }
        if(head.getKey() == key){
            node temp = head;
            head = newElem;
            head.setNext(temp);
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
    public void addAfter(int key, Student element) {
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

    private class InnerIterator implements Iterator<Student>{
        node actElem;
        public InnerIterator() {
            actElem = head;
        }
        @Override
        public boolean hasNext() {
            return actElem != null;
        }
        @Override
        public Student next() {
            Student value=actElem.getValue();
            actElem=actElem.getNext();
            return value;
        }
    }

    private class TWCListIterator implements ListIterator<Student>{

        boolean wasNext = false;
        boolean wasPrevious = false;
        /** stra�nik */
        node _current = head;


        @Override
        public boolean hasNext() {
            return _current.getNext() != head;
        }

        @Override
        public boolean hasPrevious() {
            return _current != head;
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


