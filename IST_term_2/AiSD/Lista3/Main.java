package Lista3;

import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        twoWayLinkedList();
//        oneWayLinkedList();
    }
    public static void twoWayLinkedList(){
        int dataSize = 10;
        TwoWayLinkedListWithSentinel<Integer> lista = new TwoWayLinkedListWithSentinel<>();
        System.out.println(lista.isEmpty());
        for (int i = 0; i < dataSize; i++) {
            lista.add(i);
        }
        System.out.println(lista.isEmpty());
        System.out.println("Size: "+lista.size());
//        lista.remove(3);
//        System.out.println(lista.size());

        ListIterator<Integer> iter = lista.listIterator();
        int returnPoint = 5;
        for (int i = 0; i < returnPoint; i++) {
            System.out.println(iter.next());
        }
        for (int i = 0; i < returnPoint; i++) {
            System.out.println(iter.previous());
        }
    }
    public static void oneWayLinkedList(){
        int dataSize = 10;
        OneWayLinkedListWithSentinel<Integer> lista = new OneWayLinkedListWithSentinel<>();
        System.out.println(lista.isEmpty());
        for (int i = 0; i < dataSize; i++) {
            lista.add(i);
        }
        System.out.println(lista.isEmpty());
        System.out.println("Size: "+lista.size());

        ListIterator<Integer> iter = lista.listIterator();

        while (iter.hasNext())
            System.out.println(iter.next());
    }
    public static int random(int min, int max){
        return  (int)Math.floor(Math.random() * (max - min + 1) + min);
    }
}
