package cn.xx.java;

import cn.xx.java.list.LinkedList;
import cn.xx.java.list.List;

/**
 * @author xiexu
 * @create 2021-07-24 10:56 上午
 */
public class Queue<E> {

    private List<E> list = new LinkedList<>();

    /**
     * 入队
     *
     * @param element
     */
    public void enQueue(E element) {
        list.add(element);
    }

    /**
     * 出队
     *
     * @return
     */
    public E deQueue() {
        return list.remove(0);
    }

    /**
     * 清空队列
     */
    public void clear() {
        list.clear();
    }

    /**
     * 元素的数量
     *
     * @return
     */
    public int size() {
        return list.size();
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 队头元素
     *
     * @return
     */
    public E top() {
        return list.get(0);
    }
}
