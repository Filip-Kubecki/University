package Lista4;

import Lista4.Generic.List;

import java.util.Iterator;
import java.util.ListIterator;

public class OneWayLinkedListStudent<Student> implements List<Student> {

    private class node{
        private Student value;
        private node next;
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

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }
    }

    private node head = null;

    public OneWayLinkedListStudent() {}

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
    public boolean add(Student element) {
        node newElem = new node(element);
        if(head == null){
            head = newElem;
            return true;
        }
        node tail = head;
        while(tail.getNext() != null)
            tail = tail.getNext();
        tail.setNext(newElem);
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
        newElem.setNext(temp);
    }

    @Override
    public void addBefore(int key, Student element) {
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

    private class InnerIterator implements Iterator<Student> {
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
}

