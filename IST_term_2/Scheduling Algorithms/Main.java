import java.io.File;

public class Main {
    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            GeneratorProcesow.generator(1,5,0,2,8000,"GeneratedProcesses/s"+i);
//        }
        for (int i = 0; i < 5; i++) {
            System.out.println("FCFS");
            Algorytmy.FCFS("GeneratedProcesses/s"+i);
            System.out.println("SJF");
            Algorytmy.SJF("GeneratedProcesses/s"+i);
            System.out.println("SRTF");
            Algorytmy.SRTF("GeneratedProcesses/s"+i);
            System.out.println("RR");
            Algorytmy.RR("GeneratedProcesses/s"+i,22);
            System.out.println("\n \n");
        }
    }
}