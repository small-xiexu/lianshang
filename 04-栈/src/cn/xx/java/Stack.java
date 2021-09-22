package cn.xx.java;

import cn.xx.java.list.ArrayList;
import cn.xx.java.list.List;

/**
 * @author xiexu
 * @create 2021-07-18 2:21 下午
 */
public class Stack<E> {

    private List<E> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E element) {
        list.add(element);
    }

    public E pop() {
        return list.remove(list.size() - 1);
    }

    public E top() {
        return list.get(list.size() - 1);
    }
}
