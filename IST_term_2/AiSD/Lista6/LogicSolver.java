package Lista6;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicSolver {
    private String expression;
    private static ArrayList<Operator> symbols = new ArrayList<>();
    public LogicSolver(String expression) {
        this.expression = expression;
        this.symbols.add(new Operator("(",0));
        this.symbols.add(new Operator("N",1));
        this.symbols.add(new Operator("K",1));
        this.symbols.add(new Operator("A",1));
        this.symbols.add(new Operator(")",1));
        this.symbols.add(new Operator("E",2));
        this.symbols.add(new Operator("I",2));
        this.symbols.add(new Operator("^",3));
    }

    public int solver() throws FullStackException, EmptyStackException {
        Queue<String> onpForm = toONP();
        int output = -1;
        int size = onpForm.size();
        newStack<String> auxStack = new newStack<>();

        onpForm = selectValues(onpForm);

        for (int i = 0; i < size; i++) {
            String temp = onpForm.remove();
//            Jeśli to liczba - na stos
            if (isBool(temp))
                auxStack.push(temp);
            else if (isSymbol(temp,symbols)) {
//                Jeśli to operator - zastosuj do dwóch wartości ze stosu i wynik daj na stos
                if (temp.equals("N")){
                    auxStack.push(String.valueOf(negate(Integer.parseInt(auxStack.pop()))));
                }else{
                    int b = Integer.parseInt(auxStack.pop());
                    int a = Integer.parseInt(auxStack.pop());
                    auxStack.push(String.valueOf(selectOperation(a,b,temp)));
                }
            }
        }
        output = Integer.parseInt(auxStack.pop());
        return output;
    }

    public Queue<String> toONP() throws EmptyStackException, FullStackException {
        String exp = expression;
        //W przypadku pustego wejścia
        if (exp == null) throw new RuntimeException();

        List<String> expressions = new ArrayList<String>();
        Matcher m = Pattern.compile("[a-zA-Z()]+").matcher(exp);
        while (m.find()) {
            expressions.add(m.group());
        }

        newStack<String> auxStack = new newStack<>();
        Queue<String> output = new LinkedList<>();

        for (String symbol : expressions) {
//            Jeśli liczba - dodaj do kolejki
            if (isVariable(symbol)) {
                output.add(symbol);

//             Jeśli symbol - dodaj na stos
            } else if (isSymbol(symbol, symbols)) {
                if (!auxStack.isEmpty()) {
                    if (Objects.equals(symbol, ")")) {
                        String temp = auxStack.pop();
                        while (!Objects.equals(temp, "(")) {
                            output.add(temp);
                            temp = auxStack.pop();
                        }
                    }else if (Objects.equals(symbol, "(")) {
                        auxStack.push(symbol);
                    } else if (changeToOparator(symbol).priority <= changeToOparator(auxStack.top()).priority && changeToOparator(auxStack.top()).priority != 0) {
                        while (!auxStack.isEmpty() && changeToOparator(symbol).priority <= changeToOparator(auxStack.top()).priority && changeToOparator(auxStack.top()).priority != 0) {
                            output.add(auxStack.pop());
                        }
                        auxStack.push(symbol);
                    } else {
                        auxStack.push(symbol);
                    }
                } else {
                    auxStack.push(symbol);
                }
            }
        }
        int size = auxStack.size();
        for (int i = 0; i < size; i++) {
            output.add(auxStack.pop());
        }
        return output;
    }

    private static boolean isVariable(String value) {
        return Character.isLowerCase(value.charAt(0));
    }
    private static boolean isBool(String value) {
        return Objects.equals(value, "1") || Objects.equals(value, "0");
    }
    private static boolean isSymbol(String value, ArrayList<Operator> symbols) {
        for (Operator symbol : symbols) {
            if (symbol.getName().equals(value)){
                return true;
            }
        }
        return false;
    }
    private static Operator changeToOparator(String symbol){
        for (Operator operator : symbols) {
            if (operator.getName().equals(symbol)){
                return operator;
            }
        }
        return null;
    }

    private static int selectOperation(int a,int b, String operator){
        int output = 0;
        switch (operator){
            case "I":
                if (a == 1 && b == 0)
                    output = 0;
                else
                    output = 1;
                break;
            case "K":
                if (a == 1 && b == 1)
                    output = 1;
                else
                    output = 0;
                break;
            case "A":
                if (a == 1 || b == 1)
                    output = 1;
                else
                    output = 0;
                break;
            case "E":
                if (a == b)
                    output = 1;
                else
                    output = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operator);
        }
        return output;
    }

    private static int negate(int a){
        return a == 1 ? 0 : 1;
    }
    private static Queue<String> selectValues(Queue<String> inputForm){
        String[] ref = inputForm.toArray(new String[0]);
        Queue<String> output = new LinkedList<>();
        List<String> vars = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < ref.length; i++) {
            if (Character.isLowerCase(ref[i].charAt(0))){
                System.out.print("Wpisz wartość dla zmiennej "+ref[i]+": ");
                ref[i] = scanner.nextLine();
            }
        }

        inputForm.clear();
        inputForm.addAll(Arrays.asList(ref));
        return inputForm;
    }
}
