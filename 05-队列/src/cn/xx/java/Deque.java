package cn.xx.java;

import cn.xx.java.list.LinkedList;
import cn.xx.java.list.List;

/**
 * @author xiexu
 * @create 2021-07-25 9:57 上午
 */
public class Deque<E> {

    private List<E> list = new LinkedList<>();

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
     * 清空队列
     */
    public void clear() {
        list.clear();
    }

    /**
     * 从队尾入队
     *
     * @param element
     */
    public void enQueueRear(E element) {
        list.add(element);
    }

    /**
     * 从队头出队
     *
     * @return
     */
    public E deQueueFront() {
        return list.remove(0);
    }

    /**
     * 从队头入队
     *
     * @param element
     */
    public void enQueueFront(E element) {
        list.add(0, element);
    }

    /**
     * 从队尾出队
     *
     * @return
     */
    public E deQueueRear() {
        return list.remove(size() - 1);
    }

    /**
     * 获取队头元素
     *
     * @return
     */
    public E front() {
        return list.get(0);
    }

    /**
     * 获取队尾元素
     *
     * @return
     */
    public E rear() {
        return list.get(size() - 1);
    }
}
