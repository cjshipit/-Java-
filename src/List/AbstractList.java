package List;

import java.awt.*;

public abstract class  AbstractList<E> implements List<E> {
    protected int  size ;

    //返回长度
    public int size() {
        return size;
    }
    //尾部添加
    public void add(E element){
        add(size,element);
    }
    //检查下标是否越界
    protected void rangeCheck(int index){
        if (index >= size || index < 0) {
            outOFBounds();
        }
    }
    //判断是否为null
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    protected void rangeCheckForAdd(int index){
        if (index > size || index < 0) {
            outOFBounds();
        }
    }
    //看看包含这个元素吗
    public boolean contains(E element) {
        return indexOf(element)!=ELEMENT_NOT_FOUND ;
    }
    //对数组下标越界的处理
     protected void outOFBounds(){
        throw new ArrayIndexOutOfBoundsException("数组下标越界");
    }
}
