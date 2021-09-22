package cn.xx.java.circle;

/**
 * @author xiexu
 * @create 2021-07-25 11:47 上午
 */
public class CircleDeque<E> {
    private int front;
    private int size;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10; //默认容量

    public CircleDeque() {
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
        for (int i = 0; i < elements.length; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;
    }

    /**
     * 从队尾入队
     *
     * @param element
     */
    public void enQueueRear(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    /**
     * 从队头出队
     *
     * @return
     */
    public E deQueueFront() {
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return frontElement;
    }

    /**
     * 从队头入队
     *
     * @param element
     */
    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        front = index(-1);
        elements[front] = element;
        size++;
    }

    /**
     * 从队尾出队
     *
     * @return
     */
    public E deQueueRear() {
        int rearIndex = index(size - 1); //尾部元素的索引
        E rear = elements[rearIndex];
        elements[rearIndex] = null;
        size--;
        return rear;
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
     * 获取队尾元素
     *
     * @return
     */
    public E rear() {
        return elements[index(size - 1)];
    }

    private int index(int index) {
        index += front;
        if (index < 0) {
            return index + elements.length;
        } else {
            return index - (index >= elements.length ? elements.length : 0);
        }
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

}
