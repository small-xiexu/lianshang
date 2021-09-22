package cn.xx.java.set;

import cn.xx.java.list.LinkedList;
import cn.xx.java.list.List;

/**
 * 双向链表 LinkedList 实现 ListSet
 *
 * @author xiexu
 * @create 2021-08-12 11:16 上午
 */
public class ListSet<E> implements Set<E> {

    private List<E> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E elements) {
        return list.contains(elements);
    }

    @Override
    public void add(E elements) {
        int index = list.indexOf(elements);
        if (index != List.ELEMENT_NOT_FOUND) { //存在就覆盖
            list.set(index, elements);
        } else { //不存在就添加
            list.add(elements);
        }
    }

    @Override
    public void remove(E elements) {
        int index = list.indexOf(elements);
        if (index != List.ELEMENT_NOT_FOUND) { //存在就删除
            list.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (visitor.visit(list.get(i))) {
                return;
            }
        }
    }
}
