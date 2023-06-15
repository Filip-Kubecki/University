package Lista6;

import java.util.Queue;

public class Main {
    public static void main(String[] args) throws EmptyStackException, FullStackException {
        System.out.println("Przyklad 1");
        przyklad1();
        System.out.println("\nPrzyklad 2");
        przyklad2();
    }

    public static void przyklad1() throws EmptyStackException, FullStackException {
        String input = " 5*((3 - 7)*2 - 3*(5 + 1)) - 3";
        MathSolver solver = new MathSolver(input);
        Queue<String> q = solver.toONP();
        System.out.println(q);
        System.out.println("Wynik");
        System.out.print(solver.solver());
    }

    public static void przyklad2() throws EmptyStackException, FullStackException {
        String theMorgan = " ( N ( p K q ) ) E ( N p A N q )";
        String input = "p K q A ( r I s )";
        LogicSolver solver = new LogicSolver(input);
        Queue<String> q = solver.toONP();
        System.out.println(q);
        System.out.println("Wynik");
        System.out.print(solver.solver());
    }
}
