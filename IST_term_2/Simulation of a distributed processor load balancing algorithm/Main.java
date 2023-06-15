package Backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Generators generator = new Generators(4,10,2,30,5,25);
        Strategies strategies = new Strategies(20,300,60,30,5,generator);
        System.out.println("\nSTRATEGIA 1");
        System.out.println(strategies.strategia1().toString());
        System.out.println("\nSTRATEGIA 2");
        System.out.println(strategies.strategia2().toString());
        System.out.println("\nSTRATEGIA 3");
        System.out.println(strategies.strategia3().toString());
    }

    public static void datasetCreate() throws IOException {
        int procesorCount = 50;
        int processCount = 400;
        int p = 60;
        int r = 30;
        int z = 5;
        int maxLoad = 5;
        String dataSetName = "maxLoadFrom1to70";

        Generators generator = new Generators(5,10,2,30,5,30);
        Strategies strategies = new Strategies(procesorCount,processCount,p,r,z,generator);
        String src = "src/main/java/Backend/DataSets/Strategia1-"+dataSetName+".csv";

        toCSV(src,"Average_Load,Deviation,Time,Querries_Count,Migration_count,Procesors_Count,Processes,p,r,z,maxLoad");
        toCSV(src,"0,0,0,0,0,"+procesorCount+","+processCount+","+p+","+r+","+z+",0");
        for (int i = 1; i < 71; i++) {
            maxLoad = i;
            generator = new Generators(5,10,2,30,5,maxLoad);
            strategies = new Strategies(procesorCount,processCount,p,r,z,generator);
            toCSV(src,strategies.strategia1().toCSV()/*+",0,0,0,0,0"*/);
            System.out.println(i+"% done strat 1");
        }
        src = "src/main/java/Backend/DataSets/Strategia2-"+dataSetName+".csv";

        toCSV(src,"Average_Load,Deviation,Time,Querries_Count,Migration_count,Procesors_Count,Processes,p,r,z,maxLoad");
        toCSV(src,"0,0,0,0,0,"+procesorCount+","+processCount+","+p+","+r+","+z+",0");
        for (int i = 1; i < 71; i++) {
            maxLoad = i;
            generator = new Generators(5,10,2,30,5,maxLoad);
            strategies = new Strategies(procesorCount,processCount,p,r,z,generator);
            toCSV(src,strategies.strategia2().toCSV()/*+",0,0,0,0,0"*/);
            System.out.println(i+"% done strat 2");
        }
        src = "src/main/java/Backend/DataSets/Strategia3-"+dataSetName+".csv";

        toCSV(src,"Average_Load,Deviation,Time,Querries_Count,Migration_count,Procesors_Count,Processes,p,r,z,maxLoad");
        toCSV(src,"0,0,0,0,0,"+procesorCount+","+processCount+","+p+","+r+","+z+",0");
        for (int i = 1; i < 71; i++) {
            maxLoad = i;
            generator = new Generators(5,10,2,30,5,maxLoad);
            strategies = new Strategies(procesorCount,processCount,p,r,z,generator);
            toCSV(src,strategies.strategia3().toCSV()/*+",0,0,0,0,0"*/);
            System.out.println(i+"% done strat 3");
        }
        System.out.println("Compleated");
    }
    public static void toCSV(String src,String str) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(src, true));
        writer.append(str);
        writer.append('\n');

        writer.close();
    }
}
