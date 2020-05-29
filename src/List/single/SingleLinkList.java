package List.single;

import List.AbstractList;
import List.MyLinkedList;

public class SingleLinkList<E> extends AbstractList<E> {
    //虚拟一个不放任何东西的头节点
   Node<E> first =  new Node<E>(null, null); ;
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
           Node<E> node = first.next;
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
            }
            node = node.next;
        } else {
            Node<E> node = first.next;
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
        first.next = null;
    }

    @Override
    public void add(int index, E element) {

      Node<E> pre = (index == 0) ? first: getNode(index - 1);
        Node<E> temp = new Node<E>(element, pre.next);
        pre.next = temp;
        size++;

    }

    @Override
    public void remove(int index) {
        Node<E> pre = (index == 0) ? first: getNode(index - 1);
        pre.next = pre.next.next;
        size--;
    }

    //获得下标为index的node节点
    private Node<E> getNode(int index) {
        rangeCheck(index);
        Node<E> node = first.next;
        for (int i = 0; i <= index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("size==").append(size).append("[ ");
        Node node = first.next;
        while (node != null) {
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


}
