package cn.xx.java.single;

import cn.xx.java.AbstractList;

/**
 * @author xiexu
 * @create 2021-07-13 9:52 上午
 */
public class SingleLinkedList<E> extends AbstractList<E> {
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
        if (index == 0) { //在链表头部进行插入
            Node<E> newNode = new Node<>(element, first);
            first = newNode;
        } else { //在链表中间或者尾部进行插入
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
            first = first.next;
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
        E element; //节点元素
        Node<E> next; //节点指向下一个节点

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
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
            string.append(node.element);
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
