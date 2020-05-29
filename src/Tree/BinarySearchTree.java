package Tree;

import org.w3c.dom.Node;

import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.geom.Ellipse2D;
import java.util.Comparator;

public class BinarySearchTree<E> extends BinaryTree<E> {
    private Comparator<E> compare ; //比较器
    public BinarySearchTree(){
        this(null);
    }
    //传入一个比较器进行比较
    public BinarySearchTree(Comparator<E> compare){
          this.compare = compare ;
    }
    public Node<E> getRoot(){
        return root ;
    }
    public int size(){
          return size ;
    }
    public boolean isEmpty(){
        if(size==0)  return true ;
        return false ;
    }
    //为了方便设计 AVL树的节点 以及红黑树的节点都不太一样 所以设计一个这个
    protected Node<E> creatNode(E element,Node<E> parent){
        return new Node<E>(element,parent);
    }

    public void add(E element){
           if(size==0){
              root =  creatNode(element,null);
              size++;
              afterAdd(root);
              return ;
           }
           Node<E> node = root ;//从根节点开始寻找
           Node<E> parent = null ;  //要插入元素的父节点
           int  cmp = 0 ;   //记录要插入的位置
           //找到要插入的位置 并且找到要插入的位置
           while(node!=null){
               parent = node ;
               cmp = compare(element,node.element);
               if(cmp<0){
                       node = node.left;
               }
               else if(cmp>0){
                    node = node.right ;
               }
               else {
                   node.element = element ; //相等更新一下
                   return ;
               }
           }
        //插入左边
        if(cmp<0){
            parent.left = creatNode(element,parent);
            afterAdd(parent.left);
        }
        else{
            parent.right = creatNode(element,parent);
            afterAdd(parent.right);
        }
        size++;


    }
    //AVL树 调整平衡用的
    protected void afterAdd(Node<E> node){

    }
    //replace 在红黑树的时候用 AVL树 不使用这个
    protected void afterRomove(Node<E> node,Node<E> replace){

    }
    protected int compare(E e1 ,E e2){
          if(compare!=null){
              return compare.compare(e1,e2);
          }
        return ((Comparable<E>)e1).compareTo(e2);
    }
   /* public boolean contions(E element){

    }*/
    public void remove(E element){
            if(node(element)==null){
                return ;
            }
            remove(node(element));
    }

    private void remove(Node<E> node){
        //删除度为2的节点 先找到他的前驱或者后继节点 然后把前驱或者后继 覆盖该节点 然后删除掉
        //前驱或者后继节点
        if(node.left!=null&&node.right!=null){
              Node<E> preNode = preNode(node);
              node.element = preNode.element;
              remove(preNode);
        }
        //度为1的节点
        else if(node.left!=null||node.right!=null){
               //要代替父节点的节点
               Node temp = (node.left!=null)?node.left:node.right;
               temp.parent = node.parent;
               if(node.parent==null){
                   root = temp ;
               }
            else  if(node.parent.left==node){
                node.parent.left = temp;
            }
            else if(node.parent.right==node){
                node.parent.right = temp ;
            }
            afterRomove(node,temp);
        }
        //度为0的节点
        else{
            //叶子节点是根 如果在设计的时候没有父亲节点 需要在查找的时候 即找到父节点 也找到当前节点
            if(node.parent==null){
                root = null;
            }
            else{
                if(node.parent.left==node){
                    node.parent.left = null;
                }
                else if(node.parent.right==node){
                    node.parent.right = null;
                }

            }
            afterRomove(node,null);
        }
    }
    public boolean contions(E element){
          if(node(element)==null) return false ;
          return true ;
    }
    public void clear(){
       root = null ;
    }
    private Node<E> node(E element){
        Node<E> node = root ;
        if(root==null) return null;
        while(node!=null){
            if(compare(node.element,element)==0){
                return node ;
            }
            //节点值大于要查找的值
            else if(compare(node.element,element)<0){
                node = node.right ;
            }
            else if(compare(node.element,element)>0){
                node = node.left;
            }
        }
        return null ;
    }
    @Override
    public String toString() {
        return "BinaryTreeNode [data="+ root +"]";
    }

}
