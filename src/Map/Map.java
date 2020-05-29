package Map;

public interface Map<K,V> {
    public V put(K key,V value);
    public V get(K key);
    public boolean contions(K key);
    public int size();
}
