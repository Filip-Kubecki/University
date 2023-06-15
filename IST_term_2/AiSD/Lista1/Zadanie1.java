package Lista1;

public class Zadanie1 {
    public static void main(String[] args) {
        drawPyramid(1,3);
//        drawAFigure(4);
    }

    public static void drawPyramid(int n, int h){
        for (int i = 0; i < h; i++) {
            for (int j = h-i-1; j > 0; j--) {
                System.out.print(" ");
            }
            for (int j = 0; j < 2*n+1+(i*2); j++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }


    public static void drawAFigure(int n){
        for (int i = 0; i < n; i++) {
            drawPyramid(i,n-i);
        }
    }
}
