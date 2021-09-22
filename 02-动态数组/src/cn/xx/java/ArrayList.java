package cn.xx.java;

/**
 * @author xiexu
 * @create 2021-07-12 5:11 下午
 */
public class ArrayList<E> {

    private int size; //元素的数量
    private E[] elements; //所有的元素

    private static final int DEFAULT_CAPACITY = 10; //默认容量
    private static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList(int capaticy) {
        //小于默认容量的一律按默认容量处理
        capaticy = (capaticy < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capaticy;
        elements = (E[]) new Object[capaticy];
    }

    public ArrayList() {
        //默认是10个容量
        this(DEFAULT_CAPACITY);
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * 元素的数量
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含某个元素
     *
     * @return
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 获取index位置的元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 设置index位置的元素
     *
     * @param index
     * @param element
     * @return 原来的元素
     */
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * 添加元素到尾部
     *
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    /**
     * 在index位置插入一个元素
     *
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //扩容
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        size++;
    }

    /**
     * 删除index位置的元素
     *
     * @param index
     * @return 返回删掉的元素
     */
    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }
        elements[--size] = null;
        return old;
    }

    /**
     * 查看元素的索引
     *
     * @param element
     * @return
     */
    public int indexOf(E element) {
        //null值的判断
        if (element == null) { //如果当前元素是null
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else { //如果当前元素不是null
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND; //找不到就返回-1
    }

    //抛出异常
    private void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index：" + index + ", Size：" + size);
    }

    /**
     * 检查范围
     *
     * @param index
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    /**
     * 检查添加到指定位置元素的范围
     *
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
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
            newElements[i] = elements[i];
        }
        elements = newElements;
        System.out.println(oldCapacity + "扩容为：" + newCapacity);
    }

    /**
     * 打印数组
     *
     * @return
     */
    @Override
    public String toString() {
        //size = 3, [99,88,77]
        StringBuilder string = new StringBuilder();
        string.append("size = ").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }
}
