package Backend;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Generators {
    int minTime;
    int maxTime;
    int minExeTime;
    int maxExeTime;
    int minLoad;
    int maxLoad;

    public Generators(int minTime, int maxTime, int minExeTime, int maxExeTime, int minLoad, int maxLoad) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.minExeTime = minExeTime;
        this.maxExeTime = maxExeTime;
        this.minLoad = minLoad;
        this.maxLoad = maxLoad;
    }

    public ArrayList<Process> generator(ArrayList<Process> tasks,int size){
        int time = 0;
        for (int i = 0; i < size; i++) {
            Process p = new Process(i,random(minLoad,maxLoad),random(minExeTime,maxExeTime),time+=random(minTime,maxTime));
            tasks.add(p);
        }
        return tasks;
    }

    public int random(int min,int max){
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }
}
