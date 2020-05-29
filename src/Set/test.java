package Set;

public class test {
    public static  void test(){
        TreeSet set = new TreeSet();
        for(int i=0;i<10;i++){
            set.add(i);
        }
        System.out.println(set.size());
        System.out.println(set.contions(-1));
        System.out.println(set.contions(2));

    }
    public static void main(String[] args) {
            test();
    }
}
