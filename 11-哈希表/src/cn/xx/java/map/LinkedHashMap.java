package cn.xx.java.map;

import java.util.Objects;

/**
 * @author xiexu
 * @create 2021-08-19 4:30 下午
 */
public class LinkedHashMap<K, V> extends HashMap<K, V> {
    private LinkedNode<K, V> first;
    private LinkedNode<K, V> last;

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (Objects.equals(value, node.value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) {
            return;
        }
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (visitor.visit(node.key, node.value)) {
                return;
            }
            node = node.next;
        }
    }

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removeNode) {
        LinkedNode<K, V> linkedWillNode = (LinkedNode<K, V>) willNode;
        LinkedNode<K, V> linkedRemovedNode = (LinkedNode<K, V>) removeNode;

        if (linkedWillNode != linkedRemovedNode) {
            //交换linkedWillNode和linkedRemovedNode在链表中的位置
            //交换prev
            LinkedNode<K, V> tmp = linkedWillNode.prev;
            linkedWillNode.prev = linkedRemovedNode.prev;
            linkedRemovedNode.prev = tmp;
            if (linkedWillNode.prev == null) {
                first = linkedWillNode;
            } else {
                linkedWillNode.prev.next = linkedWillNode;
            }
            if (linkedRemovedNode.prev == null) {
                first = linkedRemovedNode;
            } else {
                linkedRemovedNode.prev.next = linkedRemovedNode;
            }

            //交换next
            tmp = linkedWillNode.next;
            linkedWillNode.next = linkedRemovedNode.next;
            linkedRemovedNode.next = tmp;
            if (linkedWillNode.next == null) {
                last = linkedWillNode;
            } else {
                linkedWillNode.next.prev = linkedWillNode;
            }
            if (linkedRemovedNode.next == null) {
                last = linkedRemovedNode;
            } else {
                linkedRemovedNode.next.prev = linkedRemovedNode;
            }

        }

        LinkedNode<K, V> prev = linkedRemovedNode.prev;
        LinkedNode<K, V> next = linkedRemovedNode.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode node = new LinkedNode(key, value, parent);

        if (first == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    private static class LinkedNode<K, V> extends Node<K, V> {
        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }

}
