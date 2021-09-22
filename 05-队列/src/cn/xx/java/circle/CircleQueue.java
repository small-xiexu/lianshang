package cn.xx.java.circle;

/**
 * @author xiexu
 * @create 2021-07-25 10:32 上午
 */
public class CircleQueue<E> {
    private int front;
    private int size;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10; //默认容量

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * 获取队列元素的数量
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 判断队列是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 清空队列
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;
    }

    /**
     * 入队操作
     *
     * @param element
     */
    public void enQueue(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    /**
     * 出队操作
     *
     * @return
     */
    public E deQueue() {
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return frontElement;
    }

    /**
     * 获取队头元素
     *
     * @return
     */
    public E front() {
        return elements[front];
    }

    /**
     * 扩容
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) {
            return;
        }
        //扩容到原来容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;

        //重置front
        front = 0;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("capcacity=").append(elements.length)
                .append(" size=").append(size)
                .append(" front=").append(front)
                .append(", [");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }

    private int index(int index) {
        index += front;
        // 10%8 = 2    10-8 = 2
        // 11%10 = 1   11-10 = 1
        return index - (index >= elements.length ? elements.length : 0);
    }
}
