package cn.xx.java.circle;

import cn.xx.java.AbstractList;

/**
 * 单向循环链表
 *
 * @author xiexu
 * @create 2021-07-13 9:52 上午
 */
public class SingleCircleLinkedList<E> extends AbstractList<E> {
    private Node<E> first;

    @Override
    public void clear() {
        size = 0;
        first = null;
    }

    @Override
    public E get(int index) {
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

        if (index == 0) {
            Node<E> newFirst = new Node<>(element, first);
            //拿到最后一个结点
            Node<E> last = (size == 0) ? newFirst : node(size - 1);
            last.next = newFirst;
            first = newFirst;
        } else {
            Node<E> prev = node(index - 1); //获取上一个节点
            Node<E> newNode = new Node<>(element, prev.next);
            prev.next = newNode;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        Node<E> node = first;
        if (index == 0) { //删除0位置的元素
            if (size == 1) { //链表只有一个结点
                first = null;
            } else {
                Node<E> last = node(size - 1); //拿到最后一个结点
                first = first.next;
                last.next = first;
            }
        } else {
            Node<E> prev = node(index - 1); //获取上一个节点
            node = prev.next;
            prev.next = node.next;
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
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    //Node内部类
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element).append("_").append(next.element);
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
