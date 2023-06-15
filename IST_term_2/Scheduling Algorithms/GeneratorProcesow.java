import java.io.*;
import java.util.ArrayList;

public class GeneratorProcesow {
//    public static Proces generator(int timeMin, int timeMax, int lp) {
//
//        int rand = (int) (Math.random() * (timeMax - timeMin + 1) + timeMin);
//        return new Proces(lp,rand,rand);
//    }

    public static void generator(int timeMin, int timeMax,int timeMinZglosz, int timeMaxZglosz,int size, String src){
        ArrayList<Proces> procesy = new ArrayList<>();
        int momentKolenychZgloszen = 0;
        for (int i = 0; i < size; i++) {
            int rand = (int) (Math.random() * (timeMax - timeMin + 1) + timeMin);
            int rand2 = (int) (Math.random() * (timeMax - timeMin + 1) + timeMin);
            momentKolenychZgloszen += rand2;
            Proces proces = new Proces(i,rand,momentKolenychZgloszen);
            procesy.add(proces);
        }
        try {
            saveData(procesy,src);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveData(ArrayList<Proces> procesy, String src) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(src));
        for (Proces proces : procesy) {
            String str = proces.toString();
            writer.write(str);
            writer.newLine();
        }

        writer.close();
    }
    public static void clearDir(String src){
        File dir = new File(src);
        for(File file: dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    public static ArrayList<Proces> odczytajDane(String src){
        ArrayList<Proces> procesy = new ArrayList<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(src));
            String line = reader.readLine();

            while (line != null) {
                String[] values = line.split(" ");
                Proces p = new Proces( Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]));
                procesy.add(p);
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return procesy;
    }
}