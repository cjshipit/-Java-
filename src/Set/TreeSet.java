package Set;

import Tree.PrintTree;
import Tree.RedBlackTree;

import java.util.Comparator;

//通过红黑树来实现 java源码是封装了一个TreeMap
public class TreeSet<E> implements Set<E> {
    private RedBlackTree<E> tree = null;
    public TreeSet(){
        this(null);

    }
    public TreeSet(Comparator com){
         tree = new RedBlackTree<>(com);

    }
    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public boolean contions(E element) {
              return tree.contions(element);
    }

    @Override
    public void add(E element) {
          tree.add(element);
    }

    @Override
    public void remove(E element) {
                 tree.remove(element);
    }

    @Override
    public void printSet() {
        PrintTree.printTree(tree.getRoot());
    }
}
