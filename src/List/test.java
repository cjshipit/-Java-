package List;

import List.*;
import List.cicle.CicleLinkedList;
import List.cicle.SingleCicleLinkList;

import java.util.ArrayList;

public class test {
    public static void testCicleLinkedList(CicleLinkedList list){
        list.add(10);
        list.add(20);
        list.add(40);
        System.out.println(list);//[10 20 40]
        list.add(0,0 );
        System.out.println(list);
        list.add(3,30 );
        System.out.println(list);//[0 10 20 30 40]
        list.remove(0);
        list.remove(2);
        list.add(100);
        list.remove(list.size()-1);
        System.out.println(list);
        System.out.println(list);//[10 20 40]
        System.out.println(list.get(1));//20
        System.out.println(list.indexOf(20));//1
        list.isCicle();
    }
    public static void testSingleCicleLinkedList(SingleCicleLinkList list){
        list.add(10);
        list.add(20);
        list.add(40);
        System.out.println(list);//[10 20 40]
        list.add(0,0 );
        System.out.println(list);
        list.add(3,30 );
        System.out.println(list);//[0 10 20 30 40]
        list.remove(0);
        list.remove(2);
        System.out.println(list);//[10 20 40]
        System.out.println(list.get(1));//20
        System.out.println(list.indexOf(20));//1
        /*for(int i=0;i<100;i++){
            list.add(i);
        }
        System.out.println(list);
        System.out.println(list.isCicleLinkedList());*/
    }
    public static void testLinkedList(MyLinkedList list){
        list.add(10);
        list.add(20);
        list.add(40);
        System.out.println(list);//[10 20 40]
        list.add(0,0 );
        list.add(3,30 );
        System.out.println(list);//[0 10 20 30 40]
        list.remove(0);
        list.remove(2);
        System.out.println(list);//[10 20 40]
        System.out.println(list.get(1));//20
        System.out.println(list.indexOf(20));//1


    }
    public static void main(String[] args){
           //testLinkedList(new MyLinkedList());
          // testSingleCicleLinkedList(new SingleCicleLinkList());
          testCicleLinkedList(new CicleLinkedList());
    }
}

