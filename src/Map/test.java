package Map;


public class test {
    public static void testLinkedMap(){
        LinkedHashMap<Integer,Integer> map = new LinkedHashMap<Integer, Integer>();
        for(int i=0;i<10;i++){
            map.put(i,i);
        }
       // map.print();
        map.remove(3);
        map.remove(5);
        map.print();
    }
    public static void test(){
        TreeMap<Integer,String> map = new TreeMap<Integer, String>();
        for(int i=0;i<10;i++){
            map.put(i,"a"+i);
        }
        //map.printTree();
        System.out.println(map.get(1));
        System.out.println(map.contions(2));
        System.out.println(map.contions(100));
        for(int i=0;i<5;i++){
            map.remove(i);

        }
        map.printTree();

    }
    public static void testHashMap(){
        HashMap<Object,Integer> map = new HashMap<Object, Integer>();
        for(int i=0;i<10;i++){
            map.put(1,1);
        }
        for(int i=0;i<10;i++){
            map.remove(i);
        }
        System.out.println(map.get(1));
        System.out.println(map.size());
    }
    public static void main(String[] args) {
                       // test();
                //testHashMap();
           testLinkedMap();
    }

}
class key{
    int value ;
    public key(int value){
        this.value = value ;
    }

    @Override
    public boolean equals(Object o) {
       if(this.value==((key)o).value){
           return true ;
       }
       return false ;
    }

    @Override
    public int hashCode() {
        return  value/10 ;
    }
}