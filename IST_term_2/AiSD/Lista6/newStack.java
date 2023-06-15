package Lista6;

public class newStack<E> implements iStack<E>{
    IList<E> defList;
    public newStack(){
        defList = new OneWayLinkedList<>();
    }
    @Override
    public boolean isEmpty() {
        return defList.isEmpty();
    }
    @Override
    public boolean isFull() {
        return false;
    }
    @Override
    public E pop() throws EmptyStackException {
        E value = defList.remove(0);
        if(value==null) throw new EmptyStackException();
        return value;
    }
    @Override
    public void push(E elem) throws FullStackException {
        defList.add(0,elem);
    }
    @Override
    public int size() {
        return defList.size();
    }
    @Override
    public E top() throws EmptyStackException {
        E value= defList.get(0);
        if(value==null) throw new EmptyStackException();
        return value;
    }
}
