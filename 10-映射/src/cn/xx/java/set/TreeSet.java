package cn.xx.java.set;

import cn.xx.java.map.Map;
import cn.xx.java.map.TreeMap;

/**
 * @author xiexu
 * @create 2021-08-13 2:00 下午
 */
public class TreeSet<E> implements Set<E> {
    Map<E, Object> map = new TreeMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(E elements) {
        return map.containsKey(elements);
    }

    @Override
    public void add(E elements) {
        map.put(elements, null);
    }

    @Override
    public void remove(E elements) {
        map.remove(elements);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        map.traversal(new Map.Visitor<E, Object>() {
            @Override
            public boolean visit(E key, Object value) {
                return visitor.visit(key);
            }
        });
    }
}
