package cn.xx.java.set;

/**
 * @author xiexu
 * @create 2021-08-13 1:59 下午
 */
public interface Set<E> {
    int size();

    boolean isEmpty();

    void clear();

    boolean contains(E elements);

    void add(E elements);

    void remove(E elements);

    void traversal(Visitor<E> visitor);

    public static abstract class Visitor<E> {
        boolean stop;

        public abstract boolean visit(E element);
    }
}
