package Lista7;

import java.util.ArrayList;

public class RedBlackTree {
    private ArrayList<String> linearTree = new ArrayList<>();
    public class Node {
        Student value;
        int key;
        Node left;
        Node right;
        Node parent;
        boolean color;//RED - false; BLACK - true

        public Node(Student value) {
            this.value = value;
            this.key = value.getId();
        }
    }
    public class NilNode extends Node {
        private NilNode() {
            super(null);
            this.key = 0;
            this.color = true;
        }
    }

    Node root;

    public RedBlackTree() {}

    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }

    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }
    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }
    public Node find(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.key) {
                return node;
            } else if (key < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return null;
    }
    public void insert(Student value) {
        int key = value.getId();
        Node node = root;
        Node parent = null;

        while (node != null) {
            parent = node;
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                throw new IllegalArgumentException("BST already contains a node with key " + key);
            }
        }

        // Insert new node
        Node newNode = new Node(value);
        newNode.color = false;//RED
        if (parent == null) {
            root = newNode;
        } else if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.parent = parent;

        fixRedBlackPropertiesAfterInsert(newNode);
    }
    private void fixRedBlackPropertiesAfterInsert(Node node) {
        Node parent = node.parent;

        if (parent == null){
            node.color = true;
            return;
        }

        if (parent.color)
            return;

        Node grandparent = parent.parent;
        if (grandparent == null) {
            parent.color = true;
            return;
        }

        Node uncle = getUncle(parent);

        if (uncle != null && !uncle.color) {
            parent.color = true;//BLACK
            grandparent.color = false;//RED
            uncle.color = true;//BLACK
            fixRedBlackPropertiesAfterInsert(grandparent);
        }

        else if (parent == grandparent.left) {
            if (node == parent.right) {
                rotateLeft(parent);

                parent = node;
            }

            rotateRight(grandparent);
            parent.color = true;//Black
            grandparent.color = false;//Red
        }
        else {
            if (node == parent.left) {
                rotateRight(parent);
                parent = node;
            }

            rotateLeft(grandparent);
            parent.color = true;//BLACK
            grandparent.color = false;//RED
        }
    }
    private Node getUncle(Node parent) {
        Node grandparent = parent.parent;
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    public void remove(int key) {
        Node node = root;

        while (node != null && node.key != key) {
            if (key < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        if (node == null)
            return;

        Node movedUpNode;
        boolean deletedNodeColor;

        if (node.left == null || node.right == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.color;
        }

        else {
            Node inOrderSuccessor = findMinimum(node.right);

            node.key = inOrderSuccessor.key;
            node.value = inOrderSuccessor.value;

            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.color;
        }

        if (deletedNodeColor) {
            fixRedBlackPropertiesAfterDelete(movedUpNode);
            if (movedUpNode.getClass() == NilNode.class) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null);
            }
        }
    }
    private void fixRedBlackPropertiesAfterDelete(Node node) {
        if (node == root) {
            node.color = true;
            return;
        }

        Node sibling = getSibling(node);

        if (!sibling.color) {
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }


        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = false;//RED

            if (!node.parent.color) {
                node.parent.color = true;//BLACK
            }

            else {
                fixRedBlackPropertiesAfterDelete(node.parent);
            }
        }

        else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }
    }
    private Node getSibling(Node node) {
        Node parent = node.parent;
        if (node == parent.left) {
            return parent.right;
        } else if (node == parent.right) {
            return parent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    private boolean isBlack(Node node) {
        return node == null || node.color;
    }
    private void handleRedSibling(Node node, Node sibling) {
        sibling.color = true;//BLACK
        node.parent.color = false;//RED

        if (node == node.parent.left) {
            rotateLeft(node.parent);
        } else {
            rotateRight(node.parent);
        }
    }
    private Node deleteNodeWithZeroOrOneChild(Node node) {
        if (node.left != null) {
            replaceParentsChild(node.parent, node, node.left);
            return node.left;
        } else if (node.right != null) {
            replaceParentsChild(node.parent, node, node.right);
            return node.right;
        }else {
            Node newChild = node.color ? new NilNode() : null;
            replaceParentsChild(node.parent, node, newChild);
            return newChild;
        }
    }
    private void handleBlackSiblingWithAtLeastOneRedChild(Node node, Node sibling) {
        boolean nodeIsLeftChild = node == node.parent.left;

        if (nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left.color = true;//BLACK
            sibling.color = false;//RED
            rotateRight(sibling);
            sibling = node.parent.right;
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            sibling.right.color = true;//BLACK
            sibling.color = false;//RED
            rotateLeft(sibling);
            sibling = node.parent.left;
        }

        sibling.color = node.parent.color;
        node.parent.color = true;//BLACK
        if (nodeIsLeftChild) {
            sibling.right.color = true;//BLACK
            rotateLeft(node.parent);
        } else {
            sibling.left.color = true;//BLACK
            rotateRight(node.parent);
        }
    }
    private Node findMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
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

    void display() {
        printLevelOrder();
        System.out.println(linearTree);
        correct();
        System.out.println(linearTree);

        ArrayList<String> copyTree = linearTree;
        int h = treeHeight()+3;
        int iter = 1;
        for (int i = 0; i < (treeHeight()+1)*2; i++) {
            if (i%2 == 0){
                for (int k = 0; k < iter; k++) {
                    for (int j = 0; j < (Math.pow(2,h)-1)/2; j++) {
                        System.out.print(" ");
                    }

                    if (!copyTree.isEmpty() && !(copyTree.get(0) == null))
                        System.out.print(copyTree.remove(0));
                    else{
                        System.out.print(" ");
                        if (!copyTree.isEmpty())
                            copyTree.remove(0);
                    }
                    for (int j = 0; j < ((Math.pow(2,h)-2)/2)+1; j++) {
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
    private void printLevelOrder(){
        int h = treeHeight()+1;
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
            linearTree.add(String.valueOf(root.key+(root.color?"B":"R")));
        } else if (level > 1) {
            printCurrentLevel(root.left, level - 1);
            printCurrentLevel(root.right, level - 1);
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(new Student("Jan","Nowak",10));
        tree.insert(new Student("Krzyś","Nowak",44));
        tree.insert(new Student("Jan","Nowak",45));
        tree.insert(new Student("Jan","Nowak",90));

        tree.display();
    }
}
