package List;

//实现一个可变数组
public class MyArrayList<E> extends AbstractList<E>{
    //存放的元素
    private E[] elements;
    //当外界不传入值的时候 默认用这个长度
    private static final int DEFULT_CAPACITY = 10;

    public MyArrayList(int capacity) {
        //当传入的容量过小的时候 使用默认的容量
        capacity = (capacity < DEFULT_CAPACITY) ? DEFULT_CAPACITY : capacity;
        elements = (E[])new Object[capacity];
    }

    //没有传入参数的时候 就用默认的容量
    public MyArrayList() {
        this(DEFULT_CAPACITY);
    }

    //返回下标为index的元素
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    //往index位置覆盖值 返回一个旧的值
    public E set(int index, E element) {
       rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }
    //返回元素所在的小标 如果有那么返回 没有返回-1
    public int indexOf(E element) {
        if(element==null){
            for(int i=0;i<size;i++){
                if(elements[i]==null){
                    return i ;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])){
                return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    public void clear() {
        //清空一下
        for(int i=0;i<size;i++){
            elements[i] = null ;
        }
        size = 0 ;
    }
    //尾部添加
        public void add(E element){
         add(size,element);
    }
    //元素插入到特定的位置
    public void add(int index,E element){
        rangeCheckForAdd(index);
        //容量不够得时候 扩容
        ensureCapacity(size+1);
        for(int i=size-1;i>=index;i--){
            elements[i+1] = elements[i];
        }
        elements[index] = element;
        size++;
    }
    public void remove(int index) {
        //判断是否要缩小容量
        trim(size);
        for(int i=index;i<size-2;i++){
            elements[i] = elements[i+1];
        }
        size--;
        elements[size] = null;
    }
    @Override
    public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("size==").append(size).append("[ ");
            for(int i=0;i<size;i++) {
                if(i!=size-1){
                    s.append(elements[i]).append(" ,");
                }
                else{
                    s.append(elements[i]);
                }
            }
            s.append(" ]");
            return s.toString();
    }

    //确保容量 当容量不够得时候用1.5倍的因子进行扩容
    private void ensureCapacity(int capacity){
         int oldCapacity = elements.length;
         if(oldCapacity>capacity) return ;
         int newCapacity = oldCapacity + (oldCapacity >> 1);
         E[] newElements = (E[])new Object[newCapacity] ;
         for(int i=0;i<size;i++){
             newElements[i] = elements[i];
         }
         elements = newElements;
        System.out.println("扩容后的length"+newCapacity);
    }
    //缩小容量 当只用了一半的时候就进行缩小容量 容量小于10(默认)的时候不用缩小容量
    private void trim(int size){
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity>>1;
        if(size>=newCapacity||oldCapacity<DEFULT_CAPACITY){
            return ;
        }
            E[] newElements = (E[]) new Object[newCapacity];
            for(int i=0;i<size;i++){
              newElements[i] = elements[i] ;
            }
            elements = newElements ;
        System.out.println("缩小容量"+newCapacity);


    }

}
