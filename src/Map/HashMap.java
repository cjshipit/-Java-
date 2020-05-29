package Map;

import Queue.Queue;

import java.util.Comparator;
import java.util.Objects;

public class HashMap<K,V> implements Map<K,V>{
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size ;
    private Node<K,V>[] table ;
    private static final float  DEFAULT_LOAD_FACTOR = 0.75F ; //默认点的装填因子
    private static final int DEFAULT_CAPACITY = 1<<4 ;
   public HashMap(){
       table = new Node[DEFAULT_CAPACITY];
   }
    //删除key所对应的
   public V remove(K key){
       Node<K,V> node = node(key);
       if(node==null) return null;
       remove(node);
       return node.value;
   }
    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        if(node==null) return null;
        else return node.value;
    }
    public boolean contions(K key){
        Node<K,V> node = node(key);
        if(node==null) return false ;
        return true ;
    }
    public boolean contionsValue(V value) {
       //遍历红黑树的节点
       for(int i=0;i<table.length;i++){
           Node<K,V> root = table[i];
           if(root==null) continue ;
           Queue<Node<K,V>> q = new Queue<Node<K, V>>();
           q.enQueue(root);
           while(!q.isEmpty()){
               Node<K,V> temp = q.deQueue();
               if(temp.value==value) return true;
               if(temp.left!=null) q.enQueue(temp.left);
               if(temp.right!=null) q.enQueue(temp.right);
           }
       }
       return false ;

    }

    @Override
    public int size() {
        return 0;
    }
    protected Node<K,V> creatNode(K key,V value,Node<K,V> parent){
       return new Node<K, V>(key,value,parent);

    }
    public void clear(){
        if(size==0) return;
        size=0;
        for(int i=0;i<table.length;i++){
            table[i] = null;
        }
    }
    private void moveNode(Node<K,V> moveNode){
       moveNode.right = null;
       moveNode.left = null;
       moveNode.parent = null ;
       moveNode.color = RED;
       K key = moveNode.key ;
        int index = index(key);
        //取出位置的红黑树根节点
        Node<K,V> root = table[index];
        Node<K,V> result ;
        boolean reserched = false ;
        //该位置的第一个元素
        if(root==null){
            table[index]= moveNode;
            size++;
            afterPut(table[index]);
            return;
        }
        Node<K,V> node = root;//从根节点开始寻找
        Node<K,V> parent = null;  //要插入元素的父节点
        int cmp = 0;   //记录要插入的位置
        //找到要插入的位置 并且找到要插入的位置
        while (node != null) {
            int h1 = node.key.hashCode();
            int h2 = moveNode.key.hashCode();
            K k1 = node.key;
            if(h1<h2){
                cmp = 1 ;

            }
            else if(h1>h2){
                cmp = -1 ;
            }
            if(h1==h2){
                if(k1!=null&&key!=null&&k1.getClass()==key.getClass()&&k1 instanceof Comparator){
                    if(((Comparator) k1).compare(k1,key)<0){
                        cmp = 1 ;
                    }
                    else if (((Comparator) k1).compare(k1,key)>0){
                        cmp = -1 ;
                    }
                    else{
                            cmp = System.identityHashCode(key) - System.identityHashCode(k1);
                    }
                }
                else {
                    cmp = System.identityHashCode(key) - System.identityHashCode(k1);
                }
            }
            parent = node;
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                V old = node.value;
                node.key = key; //相等更新一下
                node.value = moveNode.value ;
                return ;
            }
        }
        //插入左边
        if(cmp<0){
            parent.left = moveNode;
            moveNode.parent = parent ;
            afterPut(parent.left);
        }
        else{
            parent.right = moveNode;
            moveNode.parent = parent ;
            afterPut(parent.right);
        }
        size++;
        return ;

    }
    private void  resize(){
       //需要扩容的情况
       if(size/table.length<=DEFAULT_LOAD_FACTOR) return ;
       Node<K,V>[] oldTable = table ;
       //两倍的扩容
       table = new Node[table.length<<2] ;
       //把节点 一个一个的挪动过去
       for(int i=0;i<oldTable.length;i++){
           //遍历红黑树的节点
               Node<K,V> root = table[i];
               if(root==null) continue ;
               Queue<Node<K,V>> q = new Queue<Node<K, V>>();
               q.enQueue(root);
               while(!q.isEmpty()){
                   Node<K,V> temp = q.deQueue();
                   if(temp.left!=null){
                       q.enQueue(temp.left);
                   }
                   if(temp.right!=null){
                       q.enQueue(temp.right);
                   }
                   moveNode(temp);

               }
           }
       }

    @Override
    public V put(K key, V value) {
         resize();
        //计算出索引 看看应该放到哪里
        int index = index(key);
        //取出位置的红黑树根节点
        Node<K,V> root = table[index];
        Node<K,V> result ;
        boolean reserched = false ;
        //该位置的第一个元素
        if(root==null){
            table[index]= creatNode(key,value,null);
            size++;
            afterPut(table[index]);
            return null ;
        }
        Node<K,V> node = root;//从根节点开始寻找
        Node<K,V> parent = null;  //要插入元素的父节点
        int cmp = 0;   //记录要插入的位置
        //找到要插入的位置 并且找到要插入的位置
        while (node != null) {
            int h1 = node.key.hashCode();
            int h2 = key.hashCode();
            K k1 = node.key;
            if(h1<h2){
                cmp = 1 ;

            }
            else if(h1>h2){
                cmp = -1 ;
            }
            if(h1==h2){
                //hashCode 相等 equals相等 说明两个key一模一样 单独的hashCode 不能说明问题
                if(Objects.equals(k1,key)){
                    cmp = 0 ;
                }
                if(k1!=null&&key!=null&&k1.getClass()==key.getClass()&&k1 instanceof Comparator){
                    if(((Comparator) k1).compare(k1,key)<0){
                            cmp = 1 ;
                    }
                    else if (((Comparator) k1).compare(k1,key)>0){
                         cmp = -1 ;
                    }
                    else{
                        //分开找一下 左边和右边是不是有一样的key
                        if(reserched==false&&
                                ((result = node(node.left,key))!=null||(result = node(node.right,key))!=null)){
                            cmp = 0 ;
                            node = result ;

                        }
                        else {
                            cmp = System.identityHashCode(key) - System.identityHashCode(k1);
                        }
                    }
                }
                else {
                    cmp = System.identityHashCode(key) - System.identityHashCode(k1);
                }
            }
            parent = node;
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                V old = node.value;
                node.key = key; //相等更新一下
                node.value = value ;
                return null;
            }
        }
        //插入左边
        if(cmp<0){
            parent.left = creatNode(key,value,parent);
            afterPut(parent.left);
        }
        else{
            parent.right = creatNode(key,value,parent);
            afterPut(parent.right);
        }
        size++;
        return value;
    }

    private int  compare(K k1,K k2){
        int hashCode1 = k1==null?0:k1.hashCode();
        int hashCode2 = k2==null?0:k1.hashCode();
        //hashCode不相同的时候 处理
        if(hashCode1!=hashCode2){
            return hashCode1 -hashCode2;
        }
        //equals相同
        if(Objects.equals(k1,k2)){
            return 0 ;
        }
        //equals不相同 比较类名
        if(k1!=null&&k2!=null){
            String s1 = k1.getClass().getName();
            String s2 = k2.getClass().getName();
           int result =  s1.compareTo(s2);
           if(result!=0){
               return result ;
           }
        }
        //最后 我们用地址来比 这个肯定不一样
        return System.identityHashCode(k1)-System.identityHashCode(k2);


    }
    //看看这个子树上 有没有这个key
    private Node<K,V> node(Node<K,V> node,K key){
        //遍历红黑树的节点
            if(node==null)  return null ;
            Queue<Node<K,V>> q = new Queue<Node<K, V>>();
            q.enQueue(node);
            while(!q.isEmpty()){
                Node<K,V> temp = q.deQueue();
                 if(Objects.equals(temp.key,key)){
                     return temp ;
                 }
            }
           return null ;


    }
    //计算key的索引
    private int index(K key){
        //key为空 放在0号位置
        if(key==null) return 0;
        int hashCode = key.hashCode()^(key.hashCode()>>>16);
        return hashCode & (table.length-1); //当table.length为2^n 的时候 算出来的数字[0,2^n-1]
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
        if(grand.parent==null) table[index(grand.key)] = parent;
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

        if(grand.parent==null) table[index(grand.key)] = parent;
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
        return color( node,BLACK);
    }
    private Node<K,V> node(K key){
        int index = index(key);
        Node<K,V> node = table[index(key)];
        if(node==null) return null;
        while(node!=null){
           K k1 = node.key;
           int h1 = k1.hashCode();
           int h2 = key.hashCode();
           if(h1<h2){
               node = node.right ;
           }
           else if(h1>h2){
               node = node.left;
           }
           else {
               if(Objects.equals(k1,key)){
                   return node ;
               }
               if(k1!=null&&key!=null&&k1.getClass()==key.getClass()&&k1 instanceof Comparator){

                   if(((Comparator) k1).compare(k1,key)<0){
                             node = node.right;
                   }
                   else if(((Comparator) k1).compare(k1,key)>0){
                                node = node.left;
                   }

               }
               else {
                   Node<K, V> result = node(node.left, key);
                   if (result != null) return result;
                   result = node(node.right, key);
                   return result;
               }


           }


        }
        return null ;
    }
    private Boolean colorOf(Node<K,V> node){
        if(node==null) return BLACK; //空节点默认为黑色(叶子节点有一层虚拟的黑色节点)
        return ((Node<K,V>)node).color;
    }
    protected  void linkedRemove(Node<K,V> willNode,Node<K,V> node){} //linkedMap要用
    private void remove(Node<K,V> node){
        //删除度为2的节点 先找到他的前驱或者后继节点 然后把前驱或者后继 覆盖该节点 然后删除掉
        //前驱或者后继节点
        Node<K,V> willNode = node ;
        if(node.left!=null&&node.right!=null){
            Node<K,V> preNode = preNode(node);
            node.key = preNode.key ;
            node.value = preNode.value;
            node = preNode;
        }
        //度为1的节点
         if(node.left!=null||node.right!=null){
            //要代替父节点的节点
            Node temp = (node.left!=null)?node.left:node.right;
            temp.parent = node.parent;
            if(node.parent==null){
                table[index(node.key)] = temp ;
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
                table[index(node.key)] = null;
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
             linkedRemove(willNode,node);
        }
    }
    protected void afterRomove(Node<K,V> node, Node<K,V> replace ){
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
    //前驱节点
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
    protected   void afterPut(Node<K,V> node){
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
