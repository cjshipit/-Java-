package Tree;

public abstract  class  BinaryTree<E> {
    static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.parent = parent;
            this.element = element;
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
        public Node<E> sibling(){
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
    protected  Node<E> preNode(Node<E> node){
        Node<E> res = node.left;
        while(res.right!=null){
            res = res.right;
        }
        return res ;
    }
    protected   Node<E> lastNode(Node<E> node){
        Node<E> res = node.right;
        while(res.left!=null){
            res = res.left;
        }
        return res ;
    }
    //根据元素来找出节点

    //检验是不是空节点
    protected  void isNullElement(E element){
        if(element==null)
            throw new IllegalArgumentException("不能存储空节点");
    }
        protected int size ;
        protected Node<E> root ;
}
