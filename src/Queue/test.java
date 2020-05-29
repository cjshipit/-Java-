package Queue;

public class test {
    public static void main(String[] args) {
        Queue<Integer> q = new Queue<Integer>();
        q.enQueue(1);
        q.enQueue(2);
        q.enQueue(3);
        while(q.isEmpty()==false){
            System.out.println(q.deQueue());
        }
        q.deQueue();



    }
}
