import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main (String[]args) throws IOException {
        ArrayList frames = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        System.out.println("How many page references? ");
        int pageRefSize = reader.nextInt();
        System.out.println("And from what interval? ");
        int maxInterval = reader.nextInt();
//        System.out.println("How many sizes of frames do you want? ");
//        int n = reader.nextInt();
//        for(int  i = 0; i < n; i++) {
//            int b = i + 1;
//            System.out.println("Size of frame "+ b + ":");
//            int p  = reader.nextInt();
//            frames.add(p);
//        }
        for (int i = 1; i < 100; i+=2) {
            frames.add(i);
        }
        Result r = new Result(pageRefSize, maxInterval, frames);
    }

}
