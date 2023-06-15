package Lista6;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathSolver {
    private String expression;
    private static ArrayList<Operator> symbols = new ArrayList<>();
    public MathSolver(String expression) {
        this.expression = expression;
        this.symbols.add(new Operator("(",0));
        this.symbols.add(new Operator("+",1));
        this.symbols.add(new Operator("-",1));
        this.symbols.add(new Operator(")",1));
        this.symbols.add(new Operator("*",2));
        this.symbols.add(new Operator("/",2));
        this.symbols.add(new Operator("^",3));
    }

    public int solver() throws FullStackException, EmptyStackException {
        Queue<String> onpForm = toONP();
        int output = 0;
        int size = onpForm.size();
        newStack<String> auxStack = new newStack<>();

        for (int i = 0; i < size; i++) {
            String temp = onpForm.remove();
//            Jeśli to liczba - na stos
            if (isNumeric(temp))
                auxStack.push(temp);
            else if (isSymbol(temp,symbols)) {
//                Jeśli to operator - zastosuj do dwóch wartości ze stosu i wynik daj na stos
                int b = Integer.parseInt(auxStack.pop());
                int a = Integer.parseInt(auxStack.pop());
                auxStack.push(String.valueOf(selectOperation(a,b,temp)));
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
        Matcher m = Pattern.compile("\\d+|[+*/^()-]").matcher(exp);
        while (m.find()) {
            expressions.add(m.group());
        }

        newStack<String> auxStack = new newStack<>();
        Queue<String> output = new LinkedList<>();

        for (String symbol : expressions) {
//            Jeśli liczba - dodaj do kolejki
            if (isNumeric(symbol)) {
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

    private static boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
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
            case "+":
                output = a + b;
                break;
            case "-":
                output = a - b;
                break;
            case "*":
                output = a * b;
                break;
            case "/":
                output = a / b;
                break;
            case "^":
                output = (int) Math.pow(a,b);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operator);
        }
        return output;
    }
}
