package List;

public interface List<E> {


    //元素不存在时 返回的常量
     static final int ELEMENT_NOT_FOUND = -1 ;
    //返回下标为index的元素
    public E get(int index);


    //往index位置覆盖值 返回一个旧的值
    public E set(int index, E element);


    //返回元素所在的小标 如果有那么返回 没有返回-1
    public int indexOf(E element);

    public void clear();

    //元素插入到特定的位置
    public void add(int index,E element);
    public void remove(int index) ;



}
