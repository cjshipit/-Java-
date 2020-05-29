package Stack;

public class test {
    public static void testStack(){
        Stack<Integer> s = new Stack<Integer>();
        s.push(1);
        s.push(2);
        s.push(3);
        System.out.println(s.peek());  //3
        while(s.isEmpty()==false){
            System.out.println(s.pop());
        }  // 3 2 1
    }
    public static void main(String[] args) {
       testStack();
    }
}
