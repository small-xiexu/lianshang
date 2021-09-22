package cn.xx.java.tree;

import java.util.Comparator;

/**
 * AVL树
 *
 * @author xiexu
 * @create 2021-08-03 10:49 上午
 */
public class AVLTree<E> extends BBST<E> {
    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 添加节点之后的操作
     *
     * @param node 新添加的节点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) { //当前节点是平衡的
                //更新高度
                updateHeight(node);
            } else {
                /**
                 * 恢复平衡
                 * 进去里面的node就是高度最低的那个不平衡节点
                 */
                rebalance(node);
                //整棵树恢复平衡
                break;
            }
        }
    }

    /**
     * 删除节点之后的操作
     *
     * @param node 被删除的节点
     */
    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                //更新高度
                updateHeight(node);
            } else {
                //恢复平衡
                rebalance(node);
            }
        }
    }

    /**
     * 重写父类中的 createNode
     *
     * @param element
     * @param parent
     * @return
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    /**
     * 恢复平衡
     *
     * @param grand 高度最低的那个不平衡节点
     */
    private void rebalance2(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();
        if (parent.isLeftChild()) { //L
            if (node.isLeftChild()) { //LL
                rotateRight(grand); //右旋
            } else { //LR
                rotateLeft(parent); //左旋
                rotateRight(grand); //右旋
            }
        } else { //R
            if (node.isLeftChild()) { //RL
                rotateRight(parent); //右旋
                rotateLeft(grand); //左旋
            } else { //RR
                rotateLeft(grand); //左旋
            }
        }
    }

    /**
     * 公共代码：不管是左旋转、右旋转，都要执行
     *
     * @param grand  失衡节点
     * @param parent
     * @param child
     */
    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);
        //更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * 统一旋转代码
     *
     * @param r
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param f
     * @param g
     */
    @Override
    protected void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
        super.rotate(r, a, b, c, d, e, f, g);
        updateHeight(b); //更新b的高度
        updateHeight(f); //更新f的高度
        updateHeight(d); //更新d的高度
    }

    /**
     * 统一旋转操作
     * 恢复平衡
     *
     * @param grand 高度最低的那个不平衡节点
     */
    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();
        if (parent.isLeftChild()) { //L
            if (node.isLeftChild()) { //LL
                rotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            } else { //LR
                rotate(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
            }
        } else { //R
            if (node.isLeftChild()) { //RL
                rotate(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);
            } else { //RR
                rotate(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
            }
        }
    }

    /**
     * 判断传入节点是否平衡
     *
     * @param node
     * @return
     */
    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }

    /**
     * 更新高度
     *
     * @param node
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    /**
     * AVL树节点
     *
     * @param <E>
     */
    private static class AVLNode<E> extends Node<E> {

        int height = 1; //叶子节点的默认高度就是1

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 返回当前节点的平衡因子
         *
         * @return
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        /**
         * 更新节点的高度
         */
        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        /**
         * 返回高度较高的那个子节点
         * 因为p总是左右子树中高度最高的那个节点
         * n也总是左右子树中高度最高的那个节点
         *
         * @return
         */
        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight > rightHeight) {
                return left;
            }
            if (leftHeight < rightHeight) {
                return right;
            }
            /**
             * 左右高度相同，就返回与父节点的子节点同方向的那个节点
             */
            return isLeftChild() ? left : right;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }
}
