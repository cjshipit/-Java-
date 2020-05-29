package Set;

public interface Set<E> {
    public int size();
    public boolean isEmpty();
    public boolean contions(E element);
    public void add(E element);
    public void remove(E element);
    public void printSet();
}
