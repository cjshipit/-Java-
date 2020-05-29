package List.cicle;


import List.AbstractList;
import List.single.SingleLinkList;

//双向循环链表链表实现一个线性结构
public class CicleLinkedList<E> extends AbstractList<E> {
    //虚拟一个不放任何东西的头节点
    Node<E> first =  null ; //指向链表的开头
    Node<E> last = null;   //指向链表的结尾
    private static class Node<E> {
        public E element;
        Node<E> next;
        Node<E> pre;

        public Node(Node<E> pre,E element, Node<E> next) {
            this.element = element;
            this.next = next;
            this.pre = pre;
        }
    }

    @Override
    public E get(int index) {
        return (E) getNode(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node oldNode = getNode(index);
        oldNode.element = element;
        return (E) oldNode.element;
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            Node<E> node = first.next;
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
            }
        } else {
            Node<E> node = first;
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
    //因为没有设计迭代器 。。。所以 不用切断每一个指针
    public void clear() {
        //自动释放内存 舒服
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public void add(int index, E element) {
        //链表中没有任何一个元素
        if(last==null&&first==null){
             Node<E> node =  new Node<>(null,element,null);
             last = node;
             first = node;
             node.next = last;
             node.pre = first;
         }
         //往末尾插入元素
         else if(index==size){
             Node<E> pre = getNode(size-1);
             Node<E> node = new Node<E>(pre,element,first);
             first.pre = node ;
             pre.next = node ;
             last = node ;
         }
         //头部插入元素
         else if(index==0){
            Node<E> next = getNode(0);
            Node<E> node = new Node<E>(last,element,next);
            first = node ;
            last.next = node ;
        }
         else {
             Node<E> next = getNode(index);
             Node<E> pre = next.pre ;
             Node<E> node = new Node<E>(pre,element,next);
             pre.next = node ;
             next.pre = node ;
         }
        size++;

    }

    @Override
    public void remove(int index) {
      Node<E> node = getNode(index);
      Node<E> pre = node.pre;
      Node<E> next = node.next;
      //头节点特殊处理
      if(index==0){
          first = next ;
          last.next = first;
      }
      else{
          pre.next = next ;
      }
      //最后一个节点特殊处理
      if(index==size-1){
          last = pre ;
          first = last;
      }else{
          next.pre = pre ;
      }
      size--;
    }

    //获得下标为index的node节点
    private Node<E> getNode(int index) {
        rangeCheck(index);
        //左边找
        if(index<(size>>2)){
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
        //右边找
        else{
            Node<E> node = last;
            for (int i = size-1 ; i>index; i--) {
                node = node.pre;
            }
            return node;
        }

        }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("size==").append(size).append("[ ");
        Node node = first;

        for(int i=0;i<size;i++){
            if (node.next != null) {
                s.append(node.element).append(", ");
            } else {
                s.append(node.element);
            }
            node = node.next;
        }
        s.append(" ]");
        return s.toString();
    }
    public void isCicle(){
        Node<E> first = this.first;
        Node<E> last = this.last ;
        for(int i=0;i<20;i++){
            System.out.print(first.element+" ");
            first = first.next;
        }
        System.out.println();
        for(int i=0;i<20;i++){
            System.out.print(last.element+" ");
            last = last.pre;
        }


    }

}