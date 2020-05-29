package Stack;

import List.MyLinkedList;

public class Stack<E> {
    private MyLinkedList<E>  list  = new MyLinkedList();
    public int size(){
       return  list.size();
    }
    public boolean isEmpty(){
        if(size()==0){
            return true ;
        }
        return false;
    }
    public E pop(){
        if(isEmpty())  return null ;
        E temp  = list.get(size()-1);
        list.remove(list.size()-1);
        return temp ;
    }
    public void push(E element){
        list.add(element);
    }
    public E peek(){
        return list.get(list.size()-1);
    }


}
