package Tree;

import java.util.Comparator;

public class RedBlackTree<E> extends BinarySearchTree<E> {
    public RedBlackTree() {
        this(null);
    }

    public RedBlackTree(Comparator<E> compare) {
        super(compare);
    }

    //用boolean表示红黑树的颜色
    private static  final boolean RED = true ;
    private static  final boolean Black = false;
    static class RBNode<E> extends Node<E>{
        boolean color = RED;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
           if(color==RED)
               return " "+element +"红"+" " ;
           else return" "+element +"黑"+" ";
        }
    }
    //因为要频繁的进行判空操作 所以 把这些给封装起来
    //返回一个节点的颜色
    private Boolean colorOf(Node<E> node){
        if(node==null) return Black ; //空节点默认为黑色(叶子节点有一层虚拟的黑色节点)
        return ((RBNode<E>)node).color;
    }
    //判断一个节点是不是红色的节点
    public boolean isRed(Node<E> node){
        if(node==null)  return false ;
        if(((RBNode<E>)node).color==RED) return true ;
        return false ;
    }
    //旋转的操作
    private void ratateLeft(RBNode<E> grand){
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
    }
    private void ratateRight(RBNode<E> grand){
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
    }

    @Override
    protected Node<E> creatNode(E element, Node<E> parent) {
        return new RBNode<E>(element,parent);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        if(node.parent==null){
            black(node);
            return ;
        }
        //父节点为黑色的时候不用处理
        if(isBlack(node.parent)) return ;
        if(node.parent.parent==null){ return ;}
        //看看叔父节点是不是红色的 如果不是 那么左旋右旋 是红色 那么就递归的调整
        Node<E> uncle = node.parent.sibling();
        if(isRed(uncle)){
            black(node.parent);
            black(uncle);
            //祖父节点变为红色然后 递归的调整红黑树
            red(node.parent.parent);
            afterAdd(node.parent.parent);
        }//叔父节点时黑色的时候 进行旋转操作
        else{

            if(node.parent.isLeftChild()){
                if(node.isLeftChild()){ //LL
                       black(node.parent);
                       red(node.parent.parent);
                       ratateRight((RBNode<E>) node.parent.parent);
                }
                else{
                          black(node);
                          red(node.parent.parent);
                          ratateLeft((RBNode<E>) node.parent);
                          ratateRight((RBNode<E>)node.parent.parent);

                }
            }
            else {
                if(node.isLeftChild()){//RL的情况
                    black(node);
                    red(node.parent.parent);
                    ratateRight((RBNode<E>)node.parent);
                    ratateLeft((RBNode<E>) node.parent.parent);

                }
                else{//RR的情况
                    black(node.parent);
                    red(node.parent.parent);
                    ratateLeft((RBNode<E>) node.parent.parent);
                }
            }

        }
    }
   //删除主要的处理 是满足路径上褐色接点数量相同就可以了
    @Override
    protected void afterRomove(Node<E> node, Node<E> replace) {
        if(node.parent==null){
            black(replace);
            return ;
        }  //处理的是跟节点
        //要删除的节点 是红色的节点 那么不用做任何处理  路径上黑色节点不变 直接返回
        if(isRed(node)) return ;
        //删除的节点 有一个红色的子节点 那么把他的子节点 染成黑色 替换掉就行了
        if(isRed(replace)){
            black(replace); return ;
        }
        //删除黑色的叶子节点(极其麻烦 恶心) 破坏掉了路径上的黑色个数相同
        else{
            //从删除的兄弟节点 借用一个 补在这里 然后黑色个数就相同了
            //先看一下删除的单个节点时左子节点还是右子节点
            boolean left = node.parent.left==null || node.isLeftChild();
            Node<E> sibing =   (left==false)?node.parent.left:node.parent.right;
            //删除的节点在父节点的左边
            if(left==true){
                //兄弟节点是红色的 那么把他调整成兄弟为黑色的情况 并且 兄弟节点满足红黑树的性质
                //父节点变为红色 兄弟节点变为黑色 进行旋转
                if (isRed(sibing)) {
                    red(node.parent);
                    black(sibing);
                    ratateLeft((RBNode<E>) node.parent);
                    sibing = node.parent.right;


                }
                //能到这里的 兄弟节点一定是黑色的
                //兄弟节点没有红色的子节点(兄弟节点没有子节点) 父节点和兄弟节点改变颜色 那么黑色节点的个数就对了
                if (isBlack(sibing.left) && isBlack(sibing.right)) {
                    red(sibing);
                    black(node.parent);
                    //如果父亲本来是黑色的 那么这条路径上就烧出了一个黑色的节点 那么递归的调整父亲节点
                    if (isBlack(node.parent)) {
                        afterRomove(node.parent, null);
                    }

                }
                //兄弟节点有一个红色的子节点 那么把他变为黑色 代替他
                else {
                    //左边没有节点 要用右边的节点取代 那么先把右边的节点旋转到左边 做同一的操作
                    if (isBlack(sibing.right)) {
                        ratateRight((RBNode<E>) sibing);
                        sibing = node.parent.right;
                    }
                    //兄弟节点变为父亲节点的颜色 然后父节点染成黑色(兄弟节点的颜色) 要取代的节点也变成黑色
                    color(sibing, ((RBNode<E>) node.parent).color);
                    black(node.parent);
                    black(sibing.right);
                }

                }
            else{
                //兄弟节点是红色的 那么把他调整成兄弟为黑色的情况 并且 兄弟节点满足红黑树的性质
                //父节点变为红色 兄弟节点变为黑色 进行旋转
                if (isRed(sibing)) {
                    red(node.parent);
                    black(sibing);
                    ratateRight((RBNode<E>) node.parent);
                    sibing = node.parent.left;


                }
                //能到这里的 兄弟节点一定是黑色的
                //兄弟节点没有红色的子节点(兄弟节点没有子节点) 父节点和兄弟节点改变颜色 那么黑色节点的个数就对了
                if (isBlack(sibing.left) && isBlack(sibing.right)) {
                    boolean parentIsBlack = isBlack(node.parent);
                    red(sibing);
                    black(node.parent);
                    //如果父亲本来是黑色的 那么这条路径上就烧出了一个黑色的节点 那么递归的调整父亲节点
                    if (parentIsBlack) {
                        afterRomove(node.parent, null);
                    }

                }
                //兄弟节点有一个红色的子节点 那么把他变为黑色 代替他
                else {
                    //左边没有节点 要用右边的节点取代 那么先把右边的节点旋转到左边 做同一的操作
                    if (isBlack(sibing.left)) {
                        ratateLeft((RBNode<E>) sibing);
                        sibing = node.parent.left;
                    }
                    //兄弟节点变为父亲节点的颜色 然后父节点染成黑色(兄弟节点的颜色) 要取代的节点也变成黑色
                    color(sibing, ((RBNode<E>) node.parent).color);
                    black(node.parent);
                    black(sibing.left);
                }
            }

        }
    }

    private boolean isBlack(Node<E> node){
        if(node==null)  return true ;
        if(((RBNode<E>)node).color==RED) return false ;
        return true ;
    }
    //为一个节点任意着色 并且返回该节点
    private  Node<E> color(Node<E> node,boolean color){
        if(node==null)   return null;
        ((RBNode<E>)node).color = color ;
        return node ;
    }
    //为一个节点着红色
    private Node<E> red(Node<E> node){
        return color( node,RED);
    }
    //为一个节点着黑色
    private Node<E> black(Node<E> node){
        return color( node,Black);
    }
}
