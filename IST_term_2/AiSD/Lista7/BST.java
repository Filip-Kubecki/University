package Lista7;

import Lista7.Student;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;

import java.util.ArrayList;

public class BST {
    private ArrayList<String> linearTree = new ArrayList<>();
    public class Node {
        Student value;
        Node left;
        Node right;
        int key;

        public Node(Student value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.key = value.getId();
        }
    }

    private Node root;

    public BST() {
        this.root = null;
    }

    public BST(Student value) { root = new Node(value); }

    //Search
    public Node find(int key){
        return findRec(root,key);
    }
    private Node findRec(Node root,int key){
        if (root == null || root.key == key)
            return root;

        if (root.key < key)
            return findRec(root.right, key);

        return findRec(root.left, key);
    }
    public int nodeHeight(int key){
        int height = 1;
        return nodeHeightRec(root,key,height);
    }
    private int nodeHeightRec(Node root,int key,int height){
        if (root == null || root.key == key)
            return height;

        if (root.key < key)
            return nodeHeightRec(root.right, key,height+1);

        return nodeHeightRec(root.left, key,height+1);
    }
    //Insertion
    void insert(Student value) { root = insertRec(root, value); }
    private Node insertRec(Node root, Student value){
        // If the tree is empty return a new node
        if (root == null) {
            root = new Node(value);
            return root;
        }

        // Otherwise, recur down the tree
        if (value.getId() < root.key)
            root.left = insertRec(root.left, value);
        else if (value.getId() > root.key)
            root.right = insertRec(root.right, value);

        // return the (unchanged) node pointer
        return root;
    }
    //Removal
    void remove(int key) { root = deleteRec(root, key); }
    private Node deleteRec(Node root, int key) {
        /* Base Case: If the tree is empty */
        if (root == null)
            return root;

        /* Otherwise, recur down the tree */
        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);

            // if key is same as root's
            // key, then This is the
            // node to be deleted
        else {
            // node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // node with two children: Get the inorder
            // successor (smallest in the right subtree)
            root.key = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }
    private int minValue(Node root) {
        int minValue = root.key;
        while (root.left != null) {
            minValue = root.left.key;
            root = root.left;
        }
        return minValue;
    }
    private int maxValue(Node root) {
        int maxValue = root.key;
        while (root.right != null) {
            maxValue = root.right.key;
            root = root.right;
        }
        return maxValue;
    }

//    private int height(){
//        return Math.max(minValue(root),maxValue(root))+1;
//    }

    private int treeHeight(){
        Node temproot = root;
        int leftHeight = 0;
        while (temproot.left != null) {
            leftHeight++;
            temproot = temproot.left;
        }
        temproot = root;
        int rightHeight = 0;
        while (temproot.right != null) {
            rightHeight++;
            temproot = temproot.right;
        }
        return Math.max(leftHeight,rightHeight)+1;
    }
    //Traversing
    void inorderTravers() { inorderTraversRec(root); }
    private void inorderTraversRec(Node root) {
        if (root != null) {
            inorderTraversRec(root.left);
            System.out.print(root.key + " ");
            inorderTraversRec(root.right);
        }
    }
    void preOrderTravers() { preOrderTraversRec(root); }
    private void preOrderTraversRec(Node root) {
        if (root != null) {
            System.out.print(root.key + " ");
            preOrderTraversRec(root.left);
            preOrderTraversRec(root.right);
        }
    }
    void postOrderTravers() { postOrderTraversRec(root); }
    private void postOrderTraversRec(Node root) {
        if (root != null) {
            postOrderTraversRec(root.left);
            postOrderTraversRec(root.right);
            System.out.print(root.key + " ");
        }
    }

    //Display tree
    void display() {
        printLevelOrder();
        correct();

        ArrayList<String> copyTree = linearTree;
        int h = treeHeight()+3;
        int iter = 1;
        for (int i = 0; i < treeHeight()*2; i++) {
            if (i%2 == 0){
                for (int k = 0; k < iter; k++) {
                    for (int j = 0; j < (Math.pow(2,h)-2)/2; j++) {
                        System.out.print(" ");
                    }

                    if (!copyTree.isEmpty() && !(copyTree.get(0) == null))
                        System.out.print(copyTree.remove(0));
                    else{
                        System.out.print("n");
                        if (!copyTree.isEmpty())
                            copyTree.remove(0);
                    }
                    for (int j = 0; j < (Math.pow(2,h)-2)/2; j++) {
                        System.out.print(" ");
                    }
                }
                iter *= 2;
                h--;
                System.out.println();
            }else{
                System.out.println();
            }
        }
    }
    private void correct(){
        ArrayList<Integer> errors = new ArrayList<>();
        if (linearTree.size()+1 != Math.pow(2,treeHeight())){
            for (int i = 0; i < linearTree.size() - 1; i++) {
                if (linearTree.get(i) == null){
                    errors.add(i+1);
                }
            }
        }
        for (Integer error : errors) {
            if (Math.floor(log2(error))+1 < treeHeight()){
                linearTree.add((int) Math.pow(2,(int)Math.floor(log2(error)))+(error),null);
            }
        }
    }
    public int log2(int n){
        return (int) (Math.log(n)/Math.log(2));
    }
    private void printLevelOrder(){
        int h = treeHeight();
        int i;
        for (i = 1; i <= h; i++)
            printCurrentLevel(root, i);
    }
    private void printCurrentLevel(Node root, int level){
        if (root == null){
            linearTree.add(null);
            return;
        }
        if (level == 1){
            linearTree.add(String.valueOf(root.key));
        } else if (level > 1) {
            printCurrentLevel(root.left, level - 1);
            printCurrentLevel(root.right, level - 1);
        }
    }
    public static void main(String[] args) {
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


        // Print inorder traversal of the BST
//        tree.inorderTravers();
//        System.out.println();
//        tree.preOrderTravers();
//        System.out.println();
//        tree.postOrderTravers();
//        System.out.println();
//        System.out.println(tree.find(1).value);

        tree.display();
    }
}
