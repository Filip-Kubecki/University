import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Result {
    public Result(int how, int interval, ArrayList f ) throws IOException {
        ArrayList<Page> PageReferences = new ArrayList<>();
//        Całkiem rand
        for(int i = 0; i < how; i++){
            int rand =(int)(Math.random()*interval);
            PageReferences.add(new Page(rand, true, 0));
        }
//      TODO:Generowanie próbek lokalnie - kilka generacji lokalnych

//      Zgodne z zasadami
//        int temp = 0;
//        for(int i = 0; i < how; i++){
//            int preRand = (int)(Math.random()*100);
//            if (preRand > 90){
//                int rand = (int)(Math.random()*interval);
//                PageReferences.add(new Page(rand, true, 0));
//            }else{
//                int randStand = (int)(Math.random()*3);
//                temp += randStand;
//                PageReferences.add(new Page(temp, true, 0));
//            }



//        for(int j = 0; j < f.size(); j++){
//            Algorithms a = new Algorithms((int)f.get(j),PageReferences);
//            System.out.println("\nWyniki: " + (int)f.get(j));
//            System.out.println("FIFO: " + a.FIFO() + " RANDOM: " + a.RANDOM()+ " LRU: " + a.LRU()+ " OPT: " + a.OPT()+ " LRU_APX: " + a.LRU_APX());
//        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv"));
        for(int i = 0; i < f.size(); i++){
            Algorithms a = new Algorithms((int)f.get(i),PageReferences);
//            System.out.println("\nResults with frame size: " + (int)f.get(j));
            System.out.println((int)f.get(i)+";" + a.FIFO() + ";" + a.RANDOM()+ ";" + a.LRU()+ ";" + a.OPT()+ ";" + a.ALRU()+";");
            writer.write((int)f.get(i)+";" + a.FIFO() + ";" + a.RANDOM()+ ";" + a.LRU()+ ";" + a.OPT()+ ";" + a.ALRU()+";\n");
        }
        writer.close();
    }
}
