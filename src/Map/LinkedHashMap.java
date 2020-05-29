package Map;



public class LinkedHashMap<K,V> extends HashMap<K,V> {
    private LinkedNode<K,V> first ;
    private LinkedNode<K,V> last;
    public void print(){
        LinkedNode node = first;
        while(node!=null) {
            System.out.println("key=" + node.key + "  value=" +node.value);
            node = node.next;
        }
    }
    @Override
    protected Node<K, V> creatNode(K key, V value, Node<K, V> parent) {
        return new LinkedNode<K,V>(key,value,parent);
    }

    @Override
    protected void linkedRemove(Node<K,V> willNode,Node<K, V> node) {
        //删的是度为2的节点 需要交换节点 然后再删除
        LinkedNode node1 = (LinkedNode<K,V>) willNode;
        LinkedNode node2 = (LinkedNode<K,V>) node;
        if(willNode!=node){
            LinkedNode<K,V> temp = node2.prev;
            node2.prev = node1.prev;
            node1.prev = temp ;
            temp= node2.next;
            node2.next= node1.next;
            node1.next= temp ;
            if(node1.prev==null){
                first = node1;
            }
            else{
                node1.prev.next = node1 ;
            }
            if(node2.prev==null){
                first = node2;
            }
            else{
                node2.prev.next = node2 ;
            }
            if(node1.next==null){
                last = node1;
            }
            else{
                node1.next.prev = node1 ;
            }
            if(node2.next==null){
                last = node2;
            }
            else{
                node1.next.prev = node2 ;
            }
        }
        //移除要删除的节点
        LinkedNode<K,V> prev = node2.prev;
        LinkedNode<K,V> next = node2.next;
        if(prev==null){
            first = next ;
        }
        else{
            prev.next = next ;
        }
        if(next==null){
            last = prev;
        }
        else{
            next.prev = node2 ;
        }
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        super.clear();
    }

    @Override
    protected void afterPut(Node<K, V> node) {
        //节点串起来
        if(first==null){
            first = (LinkedNode<K, V>) node ;
            last = (LinkedNode<K, V>) node ;
        }
        else{
            last.next =(LinkedNode<K, V>) node ;
            ((LinkedNode<K, V>) node).prev = last ;
            ((LinkedNode<K, V>) node).next = null;
            last = (LinkedNode<K, V>) node ;

        }
        super.afterPut(node);
    }

    @Override
    protected void afterRomove(Node<K, V> node, Node<K, V> replace) {

        super.afterRomove(node, replace);
    }

    public static class LinkedNode<K,V> extends Node<K,V>{
        private LinkedNode prev ;
        private LinkedNode next ;
        LinkedNode(K key, V value, HashMap.Node parent) {
            super(key, value, parent);
        }
    }

}
