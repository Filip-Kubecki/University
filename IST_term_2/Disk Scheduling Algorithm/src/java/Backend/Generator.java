package Backend;

import java.util.ArrayList;

public class Generator {

    public static int random(int min, int max){
        return  (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    public static ArrayList<Request> generatorNormalny(int zgloszenieSize, int discSize){
        ArrayList<Request> zgloszenia = new ArrayList<>(zgloszenieSize);
        int timer = 0;
        for (int i = 0; i < zgloszenieSize; i++) {
            int time = random(3,15);
            timer += time;
            zgloszenia.add(new Request(i+1,random(0,discSize),timer,random(0,3),false));
        }
        return zgloszenia;
    }
    public static ArrayList<Request> generatorZDeadlinami(int zgloszenieSize, int discSize){
        ArrayList<Request> zgloszenia = new ArrayList<>(zgloszenieSize);

        int timer = 0;
        for (int i = 0; i < zgloszenieSize; i++) {
            int rand = random(0,5);
            timer += rand;
            zgloszenia.add(new Request(i+1,random(0,discSize),timer,random(0,3),random(30,100),false));
        }
        return zgloszenia;
    }
    public static ArrayList<Request> generatorHybrydowy(int zgloszenieSize, int discSize, int deadlinePercentage){
        ArrayList<Request> zgloszenia = new ArrayList<>(zgloszenieSize);
//        Wartości funkcji random
        int readingLength = 5;

        int timer = 0;
        for (int i = 0; i < zgloszenieSize; i++) {
            int randomDeadline = random(0,100);
            int randomTime = random(0,5);
            timer += randomTime;
            if (randomDeadline > deadlinePercentage)
                zgloszenia.add(new Request(i+1,random(0,discSize),timer,random(0,readingLength),random(50,100),true));
            else
                zgloszenia.add(new Request(i+1,random(0,discSize),timer,random(0,readingLength),false));
        }
        return zgloszenia;
    }
}
