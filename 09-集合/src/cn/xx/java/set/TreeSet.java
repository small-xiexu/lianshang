package cn.xx.java.set;

import cn.xx.java.tree.BinaryTree;
import cn.xx.java.tree.RBTree;

import java.util.Comparator;

/**
 * 红黑树 RBTree 实现 TreeSet
 *
 * @author xiexu
 * @create 2021-08-12 11:51 上午
 */
public class TreeSet<E> implements Set<E> {

    private RBTree<E> tree;

    public TreeSet() {
        this(null);
    }

    public TreeSet(Comparator<E> comparator) {
        tree = new RBTree<>(comparator);
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E elements) {
        return tree.contains(elements);
    }

    @Override
    public void add(E elements) {
        tree.add(elements); //红黑树默认就是去重的
    }

    @Override
    public void remove(E elements) {
        tree.remove(elements);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        tree.inorder(new BinaryTree.Visitor<E>() {
            @Override
            public boolean visit(E element) {
                return visitor.visit(element);
            }
        });
    }
}
