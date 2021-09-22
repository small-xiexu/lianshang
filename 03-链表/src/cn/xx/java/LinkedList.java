package cn.xx.java;

/**
 * @author xiexu
 * @create 2021-07-13 9:52 上午
 */
public class LinkedList<E> extends AbstractList<E> {
    private Node<E> first;
    private Node<E> last;

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public E get(int index) {
        /**
         * 最好：O(1)
         * 最坏：O(n)
         * 平均：O(n)
         */
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        /**
         * 最好：O(1)
         * 最坏：O(n)
         * 平均：O(n)
         */
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == size) { //往最后面添加元素
            Node<E> oldLast = last; //之前的last
            last = new Node<>(element, oldLast, null);
            if (oldLast == null) { //这是链表添加的第一个元素
                first = last;
            } else {
                oldLast.next = last;
            }
        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(element, prev, next);
            next.prev = node;
            if (prev == null) { //等价于index == 0
                first = node;
            } else {
                prev.next = node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (prev == null) { //等价于index == 0
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) { //等价于 index == size - 1
            last = prev;
        } else {
            next.prev = prev;
        }

        size--;
        return node.element;
    }

    /**
     * 查看元素的索引
     *
     * @param element
     * @return
     */
    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        //null值的判断
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) {
                    return i;
                }
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND; //找不到就返回-1
    }

    /**
     * 获取index位置对应的结点
     *
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        rangeCheck(index);

        if (index < (size >> 1)) { //索引小于一半从前往后找
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else { //索引大于一半从后往前找
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }

    }

    //Node内部类
    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }

            sb.append("_").append(element).append("_");

            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size = ").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(node);
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
