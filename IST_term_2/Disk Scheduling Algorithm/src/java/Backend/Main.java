package Backend;

import java.util.ArrayList;

public class Main {
    static int discSize = 200;
    static int zgloszeniaSize = 200;
    static ArrayList<Request> zgloszenia = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            ArrayList<Request> zgloszenia = Generator.generatorZDeadlinami(zgloszeniaSize,discSize);
            System.out.println("FCFS");
            System.out.println(Algorytmy.FCFS(zgloszenia,zgloszeniaSize,discSize).allHeadMovements);
            System.out.println("SSTF");
            System.out.println(Algorytmy.SSTF(zgloszenia,zgloszeniaSize,discSize).allHeadMovements);
            System.out.println("SCAN");
            System.out.println(Algorytmy.SCAN(zgloszenia,zgloszeniaSize,discSize).allHeadMovements);
            System.out.println("CSCAN");
            System.out.println(Algorytmy.CSCAN(zgloszenia,zgloszeniaSize,discSize).allHeadMovements);
            System.out.println("EDF");
            System.out.println(Algorytmy.EDF(zgloszenia,zgloszeniaSize,discSize).allHeadMovements);
            System.out.println("FD_SCAN");
            System.out.println(Algorytmy.FD_SCAN(zgloszenia,zgloszeniaSize,discSize).allHeadMovements);
        }
        System.out.println("Koniec");
    }
//    Methods for easy debugging
    public static void displayDeadlines(ArrayList<Request> zgloszenia){
        for (Request request : zgloszenia) {
            System.out.print(request.getDeadline()+" ");
        }
        System.out.println();
    }
}
