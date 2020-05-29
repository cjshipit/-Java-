package Tree;

import java.util.Comparator;

public class test {
    public static void testRBtree(){
        RedBlackTree tree = new RedBlackTree();
        for(int i=0;i<100;i++){
            tree.add(i);
        }
        for(int i=100;i>=1;i--){
            tree.remove(i);
        }
        PrintTree.printTree(tree.getRoot());
    }
    public static void testAVLTree(){
        AVLTree<Integer> tree = new AVLTree<>();
        for(int i=0;i<1;i++){
            tree.add(i);
        }
       /* System.out.println("+++++++++++++++++++++++++++++++++");
        PrintTree.printTree(tree.getRoot());
        for(int i=0;i<8;i++){
            tree.remove(0);
            PrintTree.printTree(tree.getRoot());
            System.out.println("+++++++++++++++++++++++++++++++++++");
        }*/

    }
    public  static void testBinarySearchTree(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
        for(int i=0;i<15;i++){
            binarySearchTree.add(i);
        }
        binarySearchTree.remove(3);
        PrintTree.printTree(binarySearchTree.getRoot());
    }
    public static void main(String[] args) {
       // testBinarySearchTree();
        //testAVLTree();
        testRBtree();
    }
}
