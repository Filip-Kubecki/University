package Lista7;

import Lista7.Student;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UI {
    public static void start(){
        String userInput;
        Scanner scanner = new Scanner(System.in);
        mainloop:while(true){
            System.out.println("What type of tree do you want to operate on?" +
                    "\n1. Binary Search Tree" +
                    "\n2. Red Black Tree" +
                    "\n0. Exit");
            userInput = scanner.nextLine();
            Pattern pattern = Pattern.compile("[0-2]");
            Matcher matcher = pattern.matcher(userInput);
            boolean matchFound = matcher.find();
            if (!matchFound){
                System.out.println("Error:Invalid input");
                continue;
            }else {
                switch (userInput){
                    case "1":
                        BSTPath();
                        break;
                    case "2":
                        RBTPath();
                        break;
                    case "0":
                        break mainloop;
                }
            }
        }
    }

    private static void BSTPath(){
        BST tree = new BST();

        tree.insert(new Student("Jan","Nowak",7));
        tree.insert(new Student("John","Nowak",5));
        tree.insert(new Student("Marcin","Nowak",10));
        tree.insert(new Student("Krzysztof","Nowak",3));
        tree.insert(new Student("Krzysztof","Nowak",66));
        tree.insert(new Student("Krzysztof","Nowak",90));
        tree.insert(new Student("Bogdan","Nowak",8));
        tree.insert(new Student("Nie wiem","Nowak",12));
        tree.insert(new Student("Nie wiem","Nowak",1));

        String userInput;
        Scanner scanner = new Scanner(System.in);
        inner:while(true){
            System.out.println("What type of tree do you want to operate on?" +
                    "\n1. Insert" +
                    "\n2. Remove" +
                    "\n3. Search" +
                    "\n4. Display" +
                    "\n0. Exit");
            userInput = scanner.nextLine();
            Pattern pattern = Pattern.compile("[0-4]");
            Matcher matcher = pattern.matcher(userInput);
            boolean matchFound = matcher.find();
            if (!matchFound){
                System.out.println("Error:Invalid input");
                continue;
            }else {
                switch (userInput){
                    case "1":
                        userInput = userAnswer("Input student data(name surname id):","\\w+ \\w+ \\d+");
                        String[] studentData = userInput.split(" ");
                        if (tree.find(Integer.parseInt(studentData[2])) != null){
                            System.out.println("Record with this ID already exists.");
                            continue;
                        }else {
                            Student s = new Student(studentData[0],studentData[1],Integer.parseInt(studentData[2]));
                            tree.insert(s);
                        }
                        break;
                    case "2":
                        userInput = userAnswer("Input key to remove:","[1-100]");
                        if (tree.find(Integer.parseInt(userInput)) == null){
                            System.out.println("Record with this ID does not exists.");
                            continue;
                        }else {
                            tree.remove(Integer.parseInt(userInput));
                        }
                        continue;
                    case "3":
                        userInput = userAnswer("Input key to find:","[1-100]");
                        if (tree.find(Integer.parseInt(userInput)) == null){
                            System.out.println("Record with this ID does not exists.");
                            continue;
                        }else {
                            System.out.println(tree.find(Integer.parseInt(userInput)).value);
                        }
                        continue;
                    case "4":
                        tree.display();
                        continue;
                    case "0":
                        break inner;
                }

            }
        }
    }

    private static void RBTPath(){
        RedBlackTree tree = new RedBlackTree();

        tree.insert(new Student("Jan","Nowak",7));
        tree.insert(new Student("John","Nowak",5));
        tree.insert(new Student("Marcin","Nowak",10));
        tree.insert(new Student("Krzysztof","Nowak",3));
        tree.insert(new Student("Krzysztof","Nowak",66));
        tree.insert(new Student("Krzysztof","Nowak",90));
        tree.insert(new Student("Bogdan","Nowak",8));
        tree.insert(new Student("Nie wiem","Nowak",12));
        tree.insert(new Student("Nie wiem","Nowak",1));

        String userInput;
        Scanner scanner = new Scanner(System.in);
        inner:while(true){
            System.out.println("What type of tree do you want to operate on?" +
                    "\n1. Insert" +
                    "\n2. Remove" +
                    "\n3. Search" +
                    "\n4. Display" +
                    "\n0. Exit");
            userInput = scanner.nextLine();
            Pattern pattern = Pattern.compile("[0-4]");
            Matcher matcher = pattern.matcher(userInput);
            boolean matchFound = matcher.find();
            if (!matchFound){
                System.out.println("Error:Invalid input");
                continue;
            }else {
                switch (userInput){
                    case "1":
                        userInput = userAnswer("Input student data(name surname id):","\\w+ \\w+ \\d+");
                        String[] studentData = userInput.split(" ");
                        if (tree.find(Integer.parseInt(studentData[2])) != null){
                            System.out.println("Record with this ID already exists.");
                            continue;
                        }else {
                            Student s = new Student(studentData[0],studentData[1],Integer.parseInt(studentData[2]));
                            tree.insert(s);
                        }
                        break;
                    case "2":
                        userInput = userAnswer("Input key to remove:","[1-100]");
                        if (tree.find(Integer.parseInt(userInput)) == null){
                            System.out.println("Record with this ID does not exists.");
                            continue;
                        }else {
                            tree.remove(Integer.parseInt(userInput));
                        }
                        continue;
                    case "3":
                        userInput = userAnswer("Input key to find:","[1-100]");
                        if (tree.find(Integer.parseInt(userInput)) == null){
                            System.out.println("Record with this ID does not exists.");
                            continue;
                        }else {
                            System.out.println(tree.find(Integer.parseInt(userInput)).value);
                        }
                        continue;
                    case "4":
                        tree.display();
                        continue;
                    case "0":
                        break inner;
                }

            }
        }
    }

    private static String userAnswer(String question, String regex){
        String userInput;
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println(question);
            userInput = scanner.nextLine();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userInput);
            boolean matchFound = matcher.find();
            if (!matchFound){
                System.out.println("Error:Invalid input");
                continue;
            }else {
                return userInput;
            }
        }
    }

    public static void main(String[] args) {
        start();
    }
}
