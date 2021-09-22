package cn.xx.java.set;

import cn.xx.java.map.HashMap;
import cn.xx.java.map.Map;

/**
 * @author xiexu
 * @create 2021-08-20 9:48 上午
 */
public class HashSet<E> implements Set<E> {

    private HashMap<E, Object> map = new HashMap<>();

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
