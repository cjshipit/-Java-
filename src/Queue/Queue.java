package Queue;

import List.MyLinkedList;

public class Queue<E> {
    private MyLinkedList list = new MyLinkedList();
    public int size(){
        return list.size();
    }
    public boolean isEmpty(){
        if(size()==0){
            return true ;
        }
        return false ;
    }
    //入队
    public void enQueue(E element){
        list.add(0,element);
    }
    //获取队头元素
    public E front(){
        return (E) list.get(size()-1);
    }
    //出队
    public E deQueue(){
        if(size()==0)  return null;
        E temp = (E)list.get(size()-1);
        list.remove(size()-1);
        return temp ;
    }

}
