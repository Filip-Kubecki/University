import java.util.ArrayList;
import java.util.Collections;

public class Algorytmy {
    public static void FCFS(String src) {
        ArrayList<Proces> procesyFull = GeneratorProcesow.odczytajDane(src);
        int arraySize = procesyFull.size();
        ArrayList<Proces> procesy = new ArrayList<>();
        int doneProcesses = 0;
        int czasProcesora = 0;
        int waitingTimeSum = 0;

        do {
            for (Proces proces : procesyFull) {
                if (proces.getMomentZgloszenia() <= czasProcesora) {
                    procesy.add(proces);
                }
            }
            for (Proces proces : procesy) {
                int index = -1;
                for (Proces proces1 : procesyFull) {
                    if (proces1.getNumer() == proces.getNumer()) {
                        index = procesyFull.indexOf(proces1);
                    }
                }
                if (index != -1) {
                    procesyFull.remove(index);
                }
            }


            int temp = 1;
            if (procesy.size() > 0) {
                Proces p1 = procesy.get(0);
                temp = p1.getPhaseLength();
                p1.setPhaseLength(0);
                if (p1.getPhaseLength() == 0) {
                    waitingTimeSum += p1.getWaitingTime();
                    procesy.remove(0);
                    doneProcesses++;
                }
            }

            for (Proces backgroundProces : procesy) {
                backgroundProces.setWaitingTime(backgroundProces.getWaitingTime() + temp);
            }
            czasProcesora += temp;
        }while(doneProcesses != arraySize);

        System.out.println(
                "Avg waiting time: "+waitingTimeSum/arraySize+" ms\n"
                        +"Clock time: "+czasProcesora+" ms"
        );
    }
    public static void SJF(String src) {
        ArrayList<Proces> procesyFull = GeneratorProcesow.odczytajDane(src);
        int procesySizeDone = procesyFull.size();
        ArrayList<Proces> procesy = new ArrayList<>();
        int doneProcesses = 0;
        int czasProcesora = 0;
        int waitingTimeSum = 0;
        boolean ifDone = true;
        int temp;

        do{
            temp = 0;
            for (Proces proces : procesyFull) {
                if (proces.getMomentZgloszenia() <= czasProcesora) {
                    procesy.add(proces);
                }
            }
            for (Proces proces : procesy) {
                int index = -1;
                for (Proces proces1 : procesyFull) {
                    if (proces1.getNumer() == proces.getNumer()){
                        index = procesyFull.indexOf(proces1);
                    }
                }
                if (index != -1){
                    procesyFull.remove(index);
                }
            }
            Collections.sort(procesy, Proces::compareTo);

            if (procesy.size() > 0) {
                Proces p1 = procesy.get(0);
                temp = p1.getPhaseLength();
                waitingTimeSum += p1.getWaitingTime();
                procesy.remove(0);
                doneProcesses++;
                czasProcesora += temp;
            }
            if (procesy.size() == 0){
                czasProcesora ++;
            }

            for (Proces backgroundProces : procesy) {
                backgroundProces.setWaitingTime(backgroundProces.getWaitingTime()+temp);
            }
            if (doneProcesses == procesySizeDone){
                ifDone = false;
            }
        }while(ifDone);

        System.out.println(
                "Avg waiting time: "+waitingTimeSum/procesySizeDone+" ms\n"
                        +"Clock time: "+czasProcesora+" ms"
        );
    }
    public static void SRTF(String src) {
        ArrayList<Proces> procesyFull = GeneratorProcesow.odczytajDane(src);
        ArrayList<Proces> procesy = new ArrayList<>();
        int doneProcesses = 0;
        int czasProcesora = 0;
        int waitingTimeSum = 0;
        boolean ifDone = true;

        do{
            for (Proces proces : procesyFull) {
                if (proces.getMomentZgloszenia() == czasProcesora) {
                    procesy.add(proces);
                }
            }
            Collections.sort(procesy, Proces::compareTo);
            czasProcesora++;

            if (procesy.size() > 0){
                Proces p1 = procesy.get(0);
                p1.setPhaseLength(p1.getPhaseLength() - 1);
                if (p1.getPhaseLength() == 0){
                    waitingTimeSum += p1.getWaitingTime();
                    procesy.remove(0);
                    doneProcesses ++;
                }
            }

            for (Proces backgroundProces : procesy) {
                backgroundProces.setWaitingTime(backgroundProces.getWaitingTime()+1);
            }
            if (doneProcesses == procesyFull.size()){
                ifDone = false;
            }
        }while(ifDone);

        System.out.println(
                "Avg waiting time: "+waitingTimeSum/procesyFull.size()+" ms\n"
                        +"Clock time: "+czasProcesora+" ms"
        );
    }
    public static void RR(String src,int q){
        ArrayList<Proces> procesyFull = GeneratorProcesow.odczytajDane(src);
        ArrayList<Proces> procesy = new ArrayList<>();
        int sizeOfArrayslist = procesyFull.size();
        int doneProcesses = 0;
        int czasProcesora = 0;
        int waitingTimeSum = 0;
        int temp;

        int i = 0;
        do {
            for (Proces proces : procesyFull) {
                if (proces.getMomentZgloszenia() <= czasProcesora) {
                    procesy.add(proces);
                }
            }
            for (Proces proces : procesy) {
                int index = -1;
                for (Proces proces1 : procesyFull) {
                    if (proces1.getNumer() == proces.getNumer()){
                        index = procesyFull.indexOf(proces1);
                    }
                }
                if (index != -1){
                    procesyFull.remove(index);
                }
            }

            if (i >= procesy.size()){
                i = 0;
            }

            if (procesy.size() > 0){
                Proces p = procesy.get(i);
                if (p.getPhaseLength() < q){
                    temp = p.getPhaseLength();
                    p.setPhaseLength(0);
                    for (Proces proces : procesy) {
                        if (procesy.indexOf(proces) != i){
                            proces.setWaitingTime(proces.getWaitingTime()+temp);
                        }
                    }
                    czasProcesora += temp;
                }else {
                    p.setPhaseLength(p.getPhaseLength()-q);
                    for (Proces proces : procesy) {
                        if (procesy.indexOf(proces) != i){
                            proces.setWaitingTime(proces.getWaitingTime()+q);
                        }
                    }
                    czasProcesora += q;
                }
                if (p.getPhaseLength() == 0){
                    waitingTimeSum += p.getWaitingTime();
                    procesy.remove(p);
                    doneProcesses ++;
                }
            }else{
                czasProcesora++;
            }
            i++;

        }while(doneProcesses != sizeOfArrayslist);

        System.out.println(
                "Avg waiting time: "+waitingTimeSum/sizeOfArrayslist+" ms\n"
                        +"Clock time: "+czasProcesora+" ms"
        );
    }
}
