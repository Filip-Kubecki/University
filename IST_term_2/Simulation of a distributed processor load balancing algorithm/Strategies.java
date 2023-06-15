package Backend;

import java.util.ArrayList;
import java.util.List;

public class Strategies {
    private final int n;//Ilość CPU
    private final int size;//Ilość procesów dla każdego procesora
    private final int p;//Maksymalne obciążenie
    private final int r;//Minimalne obciążenie
    private final int z;//Ilość zapytań o obciązenie innych procesorów
    private Generators generator;

    public Strategies(int n, int size, int p, int r, int z, Generators generator) {
        this.n = n;
        this.size = size;
        this.p = p;
        this.r = r;
        this.z = z;
        this.generator = generator;
    }

    public Results strategia1(){
        List<Integer> allLoads = new ArrayList<>();
        Results wyniki = new Results();
        ArrayList<CPU> procesory = new ArrayList<>();
        ArrayList<Process> procesy = new ArrayList<>();

        int time = 0;

        //Tworzenie kolejki "n" procesorów
        for (int i = 0; i < n; i++) {
            procesory.add(new CPU(i,generator.generator(procesy,size)));
        }

        //Symulacja
        while(!procesory.isEmpty()){
            for (int p = procesory.size()-1; p > -1; p--) {
                CPU cpu = procesory.get(p);
                if (cpu.getTasks().size() == 0 && cpu.getInProgress().size() == 0){
                    procesory.remove(p);
                    break;
                }
                //Sprawdzanie czy pojawił sie proces do wykonania
                for (int i = cpu.getTasks().size()-1; i > -1; i--) {
                    Process task = cpu.getTasks().get(i);
                    //Jeżeli pojawi się zadanie do wykonania
                    if (task.getReportTime() <= time){
                        boolean migrated = false;
                        //Zapytaj "z" razy czy losowy procesor "y" ma wystarczająco zasobów
                        for (int j = 0; j < z; j++) {
                            int y = random(0,n-1);
                            wyniki.increaseQueryCount();
                            if (procesory.get(y).getResourcesInUse() < p){
                                procesory.get(y).addToInProgress(task);
                                wyniki.increaseMigrationCount();
                                migrated = true;
                                break;
                            }
                        }
                        boolean taskDone = false;
                        //Jeżeli nie uda się zmigrować zadania
                        if (!migrated){
                            if (cpu.getResourcesInUse()+task.getLoad() < 100){
                                cpu.addToInProgress(task);
                                taskDone = true;
                            }
                        }
                        if (migrated || taskDone)
                            cpu.getTasks().remove(task);
                    }
                }
                //Wykonywanie zadań przypisanych do procesora
                for (Process inProgress : cpu.getInProgress()) {
                    inProgress.increaseWaitingTime();
                }
                cpu.doOneCycle();
                wyniki.increaseAvgLoad(cpu.getResourcesInUse());
                allLoads.add(cpu.getResourcesInUse());
            }
            time++;
        }
        wyniki.setAllLoads(allLoads);
        wyniki.setAvgLoad(wyniki.getAvgLoad()/(n*time));
        wyniki.setTime(time);
        wyniki.calculateDeviation();
        return wyniki;
    }

    public Results strategia2(){
        List<Integer> allLoads = new ArrayList<>();
        Results wyniki = new Results();
        ArrayList<CPU> procesory = new ArrayList<>();
        ArrayList<Process> procesy = new ArrayList<>();

        int time = 0;


        //Tworzenie kolejki "n" procesorów
        for (int i = 0; i < n; i++) {
            procesory.add(new CPU(i,generator.generator(procesy,size)));
        }

        //Symulacja
        while(!procesory.isEmpty()){
            for (int p = procesory.size()-1; p > -1; p--) {
                CPU cpu = procesory.get(p);
                if (cpu.getTasks().size() == 0 && cpu.getInProgress().size() == 0){
                    procesory.remove(p);
                    break;
                }
                //Sprawdzanie czy pojawił sie proces do wykonania
                for (int i = cpu.getTasks().size()-1; i > -1; i--) {
                    Process task = cpu.getTasks().get(i);
                    //Jeżeli pojawi się zadanie do wykonania
                    if (task.getReportTime() <= time){
                        boolean migrated = false;
                        boolean taskDone = false;
                        if (cpu.getResourcesInUse() > p){
                            //Zapytaj czy losowy procesor "y" ma wystarczająco zasobów
                            ArrayList<CPU> copyProcesory = new ArrayList<>(procesory);
                            while (!copyProcesory.isEmpty()){
                                int y = random(0,copyProcesory.size()-1);
                                wyniki.increaseQueryCount();
                                if (copyProcesory.get(y).getResourcesInUse() < p){
                                    int index = findCPU(procesory,copyProcesory.get(y).getLp());
                                    procesory.get(index).addToInProgress(task);
                                    wyniki.increaseMigrationCount();
                                    migrated = true;
                                    break;
                                }else{
                                    copyProcesory.remove(y);
                                }
                            }
                        }else{
                            if (cpu.getResourcesInUse()+task.getLoad() < 100){
                                cpu.addToInProgress(task);
                                taskDone = true;
                            }
                        }
                        //Jeżeli nie uda się zmigrować zadania
                        if (!migrated && !taskDone){
                            if (cpu.getResourcesInUse()+task.getLoad() < 100){
                                cpu.addToInProgress(task);
                                taskDone = true;
                            }
                        }
                        if (migrated || taskDone)
                            cpu.getTasks().remove(task);
                    }
                }
                //Wykonywanie zadań przypisanych do procesora
                for (Process inProgress : cpu.getInProgress()) {
                    inProgress.increaseWaitingTime();
                }
                cpu.doOneCycle();
                wyniki.increaseAvgLoad(cpu.getResourcesInUse());
                allLoads.add(cpu.getResourcesInUse());
            }
            time++;
        }
        wyniki.setAllLoads(allLoads);
        wyniki.setAvgLoad(wyniki.getAvgLoad()/(n*time));
        wyniki.setTime(time);
        wyniki.calculateDeviation();
        return wyniki;
    }

    public Results strategia3(){
        List<Integer> allLoads = new ArrayList<>();
        Results wyniki = new Results();
        ArrayList<CPU> procesory = new ArrayList<>();
        ArrayList<Process> procesy = new ArrayList<>();

        int time = 0;

        //Tworzenie kolejki "n" procesorów
        for (int i = 0; i < n; i++) {
            procesory.add(new CPU(i,generator.generator(procesy,size)));
        }

        //Symulacja
        while(!procesory.isEmpty()){
            for (int p = procesory.size()-1; p > -1; p--) {
                CPU cpu = procesory.get(p);
                if (cpu.getTasks().size() == 0 && cpu.getInProgress().size() == 0){
                    procesory.remove(p);
                    break;
                }
                //Sprawdzanie czy pojawił sie proces do wykonania
                for (int i = cpu.getTasks().size()-1; i > -1; i--) {
                    Process task = cpu.getTasks().get(i);
                    //Jeżeli pojawi się zadanie do wykonania
                    if (task.getReportTime() <= time){
                        boolean migrated = false;
                        boolean taskDone = false;
                        if (cpu.getResourcesInUse() > p){
                            //Zapytaj czy losowy procesor "y" ma wystarczająco zasobów
                            ArrayList<CPU> copyProcesory = new ArrayList<>(procesory);
                            while (!copyProcesory.isEmpty()){
                                int y = random(0,copyProcesory.size()-1);
                                wyniki.increaseQueryCount();
                                if (copyProcesory.get(y).getResourcesInUse() < p){
                                    int index = findCPU(procesory,copyProcesory.get(y).getLp());
                                    procesory.get(index).addToInProgress(task);
                                    wyniki.increaseMigrationCount();
                                    migrated = true;
                                    break;
                                }else{
                                    copyProcesory.remove(y);
                                }
                            }
                        }else{
                            if (cpu.getResourcesInUse()+task.getLoad() < 100){
                                cpu.addToInProgress(task);
                                taskDone = true;
                            }
                            if (cpu.getResourcesInUse() < r){
                                for (int j = 0; j < z; j++) {
                                    wyniki.increaseQueryCount();
                                    int y = random(0,procesory.size()-1);
                                    if (procesory.get(y).getResourcesInUse() > p){
                                        while(procesory.get(y).getResourcesInUse() > p){
                                            wyniki.increaseMigrationCount();
                                            cpu.addToInProgress(procesory.get(y).giveBackProcess());
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        //Jeżeli nie uda się zmigrować zadania
                        if (!migrated && !taskDone){
                            if (cpu.getResourcesInUse()+task.getLoad() < 100){
                                cpu.addToInProgress(task);
                                taskDone = true;
                            }
                        }
                        if (migrated || taskDone)
                            cpu.getTasks().remove(task);
                    }
                }
                //Wykonywanie zadań przypisanych do procesora
                for (Process inProgress : cpu.getInProgress()) {
                    inProgress.increaseWaitingTime();
                }
                cpu.doOneCycle();
                wyniki.increaseAvgLoad(cpu.getResourcesInUse());
                allLoads.add(cpu.getResourcesInUse());
            }
            time++;
        }
        wyniki.setAllLoads(allLoads);
        wyniki.setAvgLoad(wyniki.getAvgLoad()/(n*time));
        wyniki.setTime(time);
        wyniki.calculateDeviation();
        return wyniki;
    }

    public int random(int min,int max){
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    public int findCPU(ArrayList<CPU> procesory,int lp){
        for (CPU cpu : procesory) {
            if (cpu.getLp() == lp){
                return procesory.indexOf(cpu);
            }
        }
        return -1;
    }
}
