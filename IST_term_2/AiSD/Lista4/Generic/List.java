package Lista4.Generic;

import java.util.Iterator;
import java.util.ListIterator;

public interface List<E> extends Iterable<E> {
    boolean isEmpty(); // czy lista jest pusta
    Iterator<E> iterator(); // zwraca iterator przed pierwszą pozycją
    ListIterator<E> listIterator(); // j.w. dla ListIterator
    E returnOnKey(int key); // pozycja szukanego elementu po kluczu
    E removeOnKey(int key); // usuwa element z podanym kluczem
    void clear(); // skasowanie wszystkich elementów
    boolean add(E e); // dodanie elementu na koniec listy
    void addAtBeginning(E element); // dodanie elementu na koniec struktury
    void addBefore(int key, E element); // dodanie elementu na podanej pozycji
    void addAfter(int key, E element); // dodanie elementu na podanej pozycji
}

