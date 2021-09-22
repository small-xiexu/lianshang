package cn.xx.java.queue;

import cn.xx.java.heap.BinaryHeap;

import java.util.Comparator;

/**
 * @author xiexu
 * @create 2021-09-14 4:34 下午
 */
public class PriorityQueue<E> {
    private BinaryHeap<E> heap;

    public PriorityQueue(Comparator<E> comparator) {
        heap = new BinaryHeap<>(comparator);
    }

    public PriorityQueue() {
        this(null);
    }

    /**
     * 入队
     *
     * @param element
     */
    public void enQueue(E element) {
        heap.add(element);
    }

    /**
     * 出队
     *
     * @return
     */
    public E deQueue() {
        return heap.remove();
    }

    /**
     * 清空队列
     */
    public void clear() {
        heap.clear();
    }

    /**
     * 元素的数量
     *
     * @return
     */
    public int size() {
        return heap.size();
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * 获取堆顶元素
     *
     * @return
     */
    public E front() {
        return heap.get();
    }
}
