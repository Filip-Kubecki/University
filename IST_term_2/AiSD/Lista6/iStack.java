package Lista6;

class EmptyStackException extends Exception{
}
class FullStackException extends Exception{
}
public interface iStack<T>{
    boolean isEmpty();
    boolean isFull();
    T pop() throws EmptyStackException;
    void push(T elem) throws FullStackException;
    int size(); // zwraca liczbę elementów na stosie
    T top() throws EmptyStackException;// zwraca element ze szczytu stosu bez usuwania go
}
