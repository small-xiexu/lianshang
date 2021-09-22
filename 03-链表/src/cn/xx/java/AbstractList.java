package cn.xx.java;

/**
 * @author xiexu
 * @create 2021-07-13 10:08 上午
 */
public abstract class AbstractList<E> implements List<E> {

    protected int size; //元素的数量

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
     * 添加元素到尾部
     *
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    //抛出异常
    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index：" + index + ", Size：" + size);
    }

    /**
     * 检查范围
     *
     * @param index
     */
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    /**
     * 检查添加到指定位置元素的范围
     *
     * @param index
     */
    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

}
