package cn.xx.java.map;

/**
 * @author xiexu
 * @create 2021-08-13 10:32 上午
 */
public interface Map<K, V> {

    int size();

    boolean isEmpty();

    void clear();

    V put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);

    void traversal(Visitor<K, V> visitor);

    public static abstract class Visitor<K, V> {
        boolean stop;

        public abstract boolean visit(K key, V value);
    }
}
