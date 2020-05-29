package List.cicle;

import List.AbstractList;
import List.MyLinkedList;

public class SingleCicleLinkList<E> extends AbstractList<E> {
   Node<E> first = null ;
    private static class Node<E> {
        public E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;

        }
    }

    @Override
    public E get(int index) {
        return (E) getNode(index).element;
    }

    @Override
    public E set(int index, E element) {
       Node<E> oldNode = getNode(index);
        oldNode.element = element;
        return (E) oldNode.element;
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
           Node<E> node = first ;
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
            }
        } else {
            Node<E> node = first ;
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) {
                    return i;
                }
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        //自动释放内存 舒服
        size = 0;
        first = null;
    }

    @Override
    public void add(int index, E element) {
       //插入在链表的头部
       if(index==0){
           Node<E> node = new Node<>(element,first);
           first = node ;
           Node<E> last = (size==0) ? node : getNode(size-1); //插入第一个节点的时候 最后一个节点是自己
           last.next = node ;
       }
       else{
           Node<E> pre = getNode(index-1);
           Node<E> node = new Node<>(element,pre.next);
           pre.next = node ;
       }
       size++;


    }

    @Override
    public void remove(int index) {
          if(index==0){
              if(size==0){
                  first=null;
              }
              Node<E> last  = getNode(size-1);//拿到最后一个节点
              last.next = first.next;
              first = first.next ;
          }
          else{
              Node<E> pre  = getNode(index-1);
              pre.next = pre.next.next;
          }
          size--;
    }

    //获得下标为index的node节点
    private Node<E> getNode(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i = 0; i <= index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("size==").append(size).append("[ ");
        Node node = first;
        for(int i=0;i<size;i++) {

            s.append(node.element+" ");
            node = node.next;
        }
        s.append(" ]");
        return s.toString();
    }
    public boolean isCicleLinkedList(){
         Node<E> first = this.first ;
         Node<E> last = this.first;
        while(first.next==null ||first.next.next==null||last.next==null||first==last){
            first = first.next.next;
            last = last.next;
        }
        if(first.next==null||first.next.next==null||last.next==null){
            return false ;
        }
        return true ;

    }
}
