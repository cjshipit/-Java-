package Map;
import java.util.Comparator;

public class TreeMap<K,V> implements Map<K,V> {
    //表示颜色
    private static  final boolean RED = true ;
    private static  final boolean Black = false;
    private Comparator<K> compare; //比较器
    private int size = 0  ;
    private Node<K,V> root ;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.compare = comparator;
    }

    @Override
    public V put(K key, V value) {
        if (size == 0) {
            root = new Node<K, V>(key,value,null);
            size++;
            afterPut(root);
            return null;
        }
        Node<K,V> node = root;//从根节点开始寻找
        Node<K,V> parent = null;  //要插入元素的父节点
        int cmp = 0;   //记录要插入的位置
        //找到要插入的位置 并且找到要插入的位置
        while (node != null) {
            parent = node;
            cmp = compare(key, node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                V old = node.value;
                node.key = key; //相等更新一下
                node.value = value ;
                return old;
            }
        }
        //插入左边
        if(cmp<0){
            parent.left = new Node<K, V>(key,value,parent);
            afterPut(parent.left);
        }
        else{
            parent.right = new Node<K, V>(key,value,parent);
            afterPut(parent.right);
        }
        size++;
        return value;
    }
    private int compare(K e1 ,K e2){
        if(compare!=null){
            return compare.compare(e1,e2);
        }
        return ((Comparable<K>)e1).compareTo(e2);
    }

    @Override
    public V get (K key){
           Node<K,V> node = node(key);
           if(node==null){
               return null;
           }
           return node.value;
        }
    @Override
    public boolean contions (K key){
        Node<K,V> node = node(key);
        if(node==null) return false ;
        else return true ;
        }

    @Override
    public int size () {
            return size ;
        }
    public static class Node<K, V> {
        K key;
        V value;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;
        boolean color = RED;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;

        }
        public boolean isLeftChild(){
            if(this==null) return false ;
            if(this.parent == null) return false ;
            if(this.parent.left==this) return true ;
            return false ;
        }
        public boolean isRightChild(){
            if(this==null) return false ;
            if(this.parent == null)  return false ;
            if(this.parent.right==this) return true ;
            return false ;
        }
        public Node<K,V> sibling(){
            if(this==null) return null ;
            if(this.parent==null) return null;
            if(this.isLeftChild()){
                return this.parent.right;
            }
            else{
                return this.parent.left;
            }
        }
    }
    public void remove(K key){
        if(node(key)==null){
            return ;
        }
        remove(node(key));
    }
    //判断一个节点是不是红色的节点
    private boolean isRed(Node<K,V> node){
        if(node==null)  return false ;
        if(((Node<K,V>)node).color==RED) return true ;
        return false ;
    }
    //旋转的操作
    private void ratateLeft(Node<K,V> grand){
        Node<K,V> parent = grand.right;
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
    private void ratateRight(Node<K,V> grand){
        Node<K,V> parent = grand.left;
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
    private boolean isBlack(Node<K,V> node){
        if(node==null)  return true ;
        if(node.color==RED) return false ;
        return true ;
    }
    //为一个节点任意着色 并且返回该节点
    private Node<K,V> color(Node<K,V> node, boolean color){
        if(node==null)   return null;
        node.color = color;
        return node ;
    }
    //为一个节点着红色
    private Node<K,V> red(Node<K,V> node){
        return color( node,RED);
    }
    //为一个节点着黑色
    private Node<K,V> black(Node<K,V> node){
        return color( node,Black);
    }
    private Node<K,V> node(K key){
        Node<K,V> node = root ;
        if(root==null) return null;
        while(node!=null){
            if(compare(node.key,key)==0){
                return node ;
            }
            //节点值大于要查找的值
            else if(compare(node.key,key)<0){
                node = node.right ;
            }
            else if(compare(node.key,key)>0){
                node = node.left;
            }
        }
        return null ;
    }
    private Boolean colorOf(Node<K,V> node){
        if(node==null) return Black ; //空节点默认为黑色(叶子节点有一层虚拟的黑色节点)
        return ((Node<K,V>)node).color;
    }
    private void remove(Node<K,V> node){
        //删除度为2的节点 先找到他的前驱或者后继节点 然后把前驱或者后继 覆盖该节点 然后删除掉
        //前驱或者后继节点
        if(node.left!=null&&node.right!=null){
            Node<K,V> preNode = preNode(node);
            node.key = preNode.key ;
            node.value = preNode.value;
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
    private void afterRomove(Node<K,V> node,Node<K,V> replace ){
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
            Node<K,V> sibing =   (left==false)?node.parent.left:node.parent.right;
            //删除的节点在父节点的左边
            if(left==true){
                //兄弟节点是红色的 那么把他调整成兄弟为黑色的情况 并且 兄弟节点满足红黑树的性质
                //父节点变为红色 兄弟节点变为黑色 进行旋转
                if (isRed(sibing)) {
                    red(node.parent);
                    black(sibing);
                    ratateLeft(node.parent);
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
                        ratateRight(sibing);
                        sibing = node.parent.right;
                    }
                    //兄弟节点变为父亲节点的颜色 然后父节点染成黑色(兄弟节点的颜色) 要取代的节点也变成黑色
                    color(sibing,node.parent.color);
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
                    ratateRight(node.parent);
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
                        ratateLeft( sibing);
                        sibing = node.parent.left;
                    }
                    //兄弟节点变为父亲节点的颜色 然后父节点染成黑色(兄弟节点的颜色) 要取代的节点也变成黑色
                    color(sibing, (node.parent).color);
                    black(node.parent);
                    black(sibing.left);
                }
            }

        }
    }
    private Node<K,V> preNode(Node<K,V> node){
        Node<K,V> res = node.left;
        while(res.right!=null){
            res = res.right;
        }
        return res ;
    }
    private Node<K,V> lastNode(Node<K,V> node){
        Node<K,V> res = node.right;
        while(res.left!=null){
            res = res.left;
        }
        return res ;
    }
    private void print(Node<K,V> node){
        if(node==null) return ;
        print(node.left);
        System.out.println("key  "+node.key+"  value  "+node.value);
        print(node.right);
    }
    public void printTree(){
        print(root);
    }
    private  void afterPut(Node<K,V> node){
        if(node.parent==null){
            black(node);
            return ;
        }
        //父节点为黑色的时候不用处理
        if(isBlack(node.parent)) return ;
        if(node.parent.parent==null){ return ;}
        //看看叔父节点是不是红色的 如果不是 那么左旋右旋 是红色 那么就递归的调整
        Node<K,V> uncle = node.parent.sibling();
        if(isRed(uncle)){
            black(node.parent);
            black(uncle);
            //祖父节点变为红色然后 递归的调整红黑树
            red(node.parent.parent);
            afterPut(node.parent.parent);
        }//叔父节点时黑色的时候 进行旋转操作
        else {

            if (node.parent.isLeftChild()) {
                if (node.isLeftChild()) { //LL
                    black(node.parent);
                    red(node.parent.parent);
                    ratateRight( node.parent.parent);
                } else {
                    black(node);
                    red(node.parent.parent);
                    ratateLeft( node.parent);
                    ratateRight( node.parent.parent);

                }
            }
        }
    }

}
