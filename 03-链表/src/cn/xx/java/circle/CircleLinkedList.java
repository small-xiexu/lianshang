package cn.xx.java.circle;

import cn.xx.java.AbstractList;

/**
 * @author xiexu
 * @create 2021-07-13 9:52 上午
 */
public class CircleLinkedList<E> extends AbstractList<E> {
    private Node<E> first;
    private Node<E> last;
    private Node<E> current;

    /**
     * 让current指向头结点first
     */
    public void reset() {
        current = first;
    }

    /**
     * 让current往后走一步
     *
     * @return
     */
    public E next() {
        if (current == null) {
            return null;
        }
        current = current.next;
        return current.element;
    }

    /**
     * 删除current指向的结点，删除成功后让current指向下一个结点
     *
     * @return
     */
    public E remove() {
        if (current == null) {
            return null;
        }
        Node<E> next = current.next;
        E element = remove(current);
        if (size == 0) {
            current = null;
        } else {
            current = next;
        }
        return element;
    }

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
            last = new Node<>(element, oldLast, first);
            if (oldLast == null) { //这是链表添加的第一个元素
                first = last;
                first.next = last;
                first.prev = first;
            } else {
                oldLast.next = last;
                first.prev = last;
            }
        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(element, prev, next);
            next.prev = node;
            prev.next = node;
            if (index == 0) {
                first = node;
            }
        }

        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        return remove(node(index));
    }

    private E remove(Node<E> node) {
        if (size == 1) {
            first = null;
            last = null;
        } else {
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = next;
            next.prev = prev;

            if (node == first) { //等价于 index == 0
                first = next;
            }

            if (node == last) { //等价于 index == size - 1
                last = prev;
            }
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
