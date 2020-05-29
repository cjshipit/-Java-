package Tree;

import java.util.Comparator;

public class AVLTree<E> extends BinarySearchTree<E> {
    //为了方便计算平衡因子 在设计上每个节点保存一下该节点的高度 。。。。没有也可以 每次求一下
    // 高度就可以了
    static  class AVLNode<E> extends Node{
        int height = 1 ;
        public AVLNode(Object element, Node parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
          return " "+element+" "+height+" ";
        }
    }
    public AVLTree(Comparator<E> com){
        super(com);
    }
    public AVLTree(){
        this(null);
    }

    @Override
    protected Node<E> creatNode(E element, Node<E> parent) {
        return new AVLNode<E>(element,parent);
    }
    protected  void upDate(AVLNode<E> node){
        int leftHight = (node.left==null)?0:((AVLNode<E>)node.left).height;
        int rightHight = (node.right==null)?0:((AVLNode<E>)node.right).height;
        if(leftHight==0&&rightHight==0)
        {
            node.height = 1 ;
            return ;
        }
        node.height = Math.max(leftHight,rightHight)+1;
    }
    private AVLNode<E> maxHightChild(AVLNode<E> node){
        if(node.left==null)  return (AVLNode<E>) node.right;
        if(node.right==null) return (AVLNode<E>) node.left;
      return   ((AVLNode<E>)node.left).height> ((AVLNode<E>)node.right).height
                ?(AVLNode<E>)node.left:(AVLNode<E>)node.right;
    }
    private void ratateLeft(AVLNode<E> grand){
        Node<E> parent = grand.right;
        grand.right = parent.left;
        parent.left = grand ;
        //更改父节点
        if(grand.right!=null) grand.right.parent = grand ;
        parent.parent = grand.parent;
        //将grand的父亲挂上parent
        if(grand.parent==null) root = parent;
        else if(grand == grand.parent.left){
            grand.parent.left = parent;
        }
        else{
            grand.parent.right = parent;
        }
        grand.parent = parent ;
        upDate(grand);
        upDate((AVLNode<E>) parent);


    }
    private void ratateRight(AVLNode<E> grand){
        Node<E> parent = grand.left;
        grand.left = parent.right;
        parent.right = grand;
        //更改父节点
        if(grand.left!=null)  grand.left.parent = grand ;
        parent.parent = grand.parent;
        //将grand的父亲挂上parent

        if(grand.parent==null) root = parent;
        else if(grand == grand.parent.left){
            grand.parent.left = parent;
        }
        else{
            grand.parent.right = parent;
        }
        grand.parent = parent ;
        upDate(grand);
        upDate((AVLNode<E>) parent);
    }
    private void reBalance(AVLNode<E> grandParent){
        //拿到parent
        AVLNode<E> parent = maxHightChild(grandParent);
        AVLNode<E> node =   maxHightChild(parent);
        if(parent==grandParent.left){
             //LL型的旋转
             if(node==parent.left){
                ratateRight(grandParent);
             }
             //LR型号的
             if(node==parent.right){
                 //旋转两次
                 ratateLeft(parent);
                  ratateRight(grandParent);

             }
        }
        else{
            //RR型的旋转
            if(node==parent.right){
                ratateLeft(grandParent);
            }
            //RL型号的
            if(node==parent.left){
                //旋转两次
                ratateRight(parent);
                ratateLeft(grandParent);
            }
        }

    }
   //和添加的操作一样 只不过 调整平衡的时候 不平衡节点有很多
    @Override
    protected void afterRomove(Node<E> node,Node<E> replace) {
        while((node = node.parent)!=null){
            //平衡如何操作
            if(isBalance(node)){
                //自下而上的更新一下高度
                upDate((AVLNode<E>) node);
            }
            //不平衡 又如何操作
            else{
                reBalance((AVLNode<E>) node);
            }
        }

    }

    @Override
    protected void afterAdd(Node<E> node) {
        //找到 距离插入节点最近的不平衡节点 然后 把他调整平衡
        while((node = node.parent)!=null){
            //平衡如何操作
            if(isBalance(node)){
                //自下而上的更新一下高度
                upDate((AVLNode<E>) node);
            }
            //不平衡 又如何操作
            else{
                reBalance((AVLNode<E>) node);
                return;
            }
        }

    }

    private boolean  isBalance(Node<E> node){
        int leftHight = node.left==null?0:((AVLNode)node.left).height;
        int rightHight = node.right==null?0:((AVLNode)node.right).height;
        if(Math.abs(leftHight-rightHight)<=1) return true ;
        return false ;

    }
}
