package Lista2;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Przykład 1:");
        przyklad1();
        System.out.println("\n\nPrzykład 2:");
        przyklad2();
    }
    public static void  przyklad1(){
        int dataSize = 20;//Ilość generowanych elementów
        Integer[] intArray = new Integer[dataSize];
        Series<Integer> seria = new Series<>();
        przyklad1Generator generator = new przyklad1Generator<>();

//        Generowanie zawartości struktury danych
        for (int i = 1; i < dataSize+1; i++) {
            intArray[i-1] = generator.generate(i);
        }
        seria.setArray(intArray);
//        Iterator
        Iterator<Integer> iterator = seria.iterator();
        System.out.println("Iterator w pętli while");
        while (iterator.hasNext())
            System.out.print(iterator.next()+" ");
//        Pętla foreach
        System.out.println("\nForeach");
        for (Integer integer : seria) {
            System.out.print(integer+" ");
        }
    }
    public static void  przyklad2(){
        int dataSize = 5;//Ilość generowanych elementów
        String[] stringArray = new String[dataSize];
        Series<String> seria = new Series<>();
        przyklad2Generator generator = new przyklad2Generator<>();

//        Generowanie zawartości struktury danych
        for (int i = 1; i < dataSize+1; i++) {
            stringArray[i-1] = generator.generate(i);
        }
        seria.setArray(stringArray);
//        Iterator
        Iterator<String> iterator = seria.iterator();
        System.out.println("Iterator w pętli while");
        while (iterator.hasNext())
            System.out.print(iterator.next()+" ");
//        Pętla foreach
        System.out.println("\nForeach");
        for (String s : seria) {
            System.out.print(s+" ");
        }
    }
}
