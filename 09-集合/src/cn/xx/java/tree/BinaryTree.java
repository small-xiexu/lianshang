package cn.xx.java.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树
 *
 * @author xiexu
 * @create 2021-08-02 12:14 下午
 */
public class BinaryTree<E> {
    protected int size; //节点数量
    protected Node<E> root; //根节点

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    protected static class Node<E> {
        E element;
        Node<E> left; //左子节点
        Node<E> right; //右子节点
        Node<E> parent; //父节点

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        //判断当前节点是不是叶子节点
        public boolean isLeaf() {
            return left == null && right == null;
        }

        //判断当前节点是不是拥有两个子节点
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        //判断当前节点是不是左子树
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        //判断当前节点是不是右子树
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        //返回兄弟结点
        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }

    /**
     * Visitor 接口
     *
     * @param <E>
     */
    public static abstract class Visitor<E> {
        boolean stop;

        /**
         * @param element
         * @return 如果返回true，就代表停止遍历
         */
        public abstract boolean visit(E element);
    }

    /**
     * 前序遍历
     *
     * @param visitor
     */
    public void preorder(Visitor<E> visitor) {
        preorder(root, visitor);
    }

    private void preorder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }
        visitor.visit(node.element);
        preorder(node.left, visitor);
        preorder(node.right, visitor);
    }

    /**
     * 中序遍历
     *
     * @param visitor
     */
    public void inorder(Visitor<E> visitor) {
        inorder(root, visitor);
    }

    private void inorder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }
        inorder(node.left, visitor);
        visitor.visit(node.element);
        inorder(node.right, visitor);
    }

    /**
     * 后序遍历
     *
     * @param visitor
     */
    public void postorder(Visitor<E> visitor) {
        postorder(root, visitor);
    }

    private void postorder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }
        postorder(node.left, visitor);
        postorder(node.right, visitor);
        visitor.visit(node.element);
    }

    /**
     * 层序遍历
     *
     * @param visitor
     */
    public void levelOrder(Visitor<E> visitor) {
        if (root == null || visitor == null) {
            return;
        }
        //创建一个队列
        Queue<Node<E>> queue = new LinkedList<>();
        //将头节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //将头结点出队
            Node<E> node = queue.poll();

            visitor.visit(node.element);

            //如果左子节点不为空，就将左子节点入队
            if (node.left != null) {
                queue.offer(node.left);
            }
            //如果右子节点不为空，就将右子节点入队
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    /**
     * 前驱节点: 中序遍历时的前一个节点
     * 求前驱节点
     */
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) {
            return null;
        }
        //前驱节点在左子树中(left.right.right.right....)
        Node<E> p = node.left;
        if (node.left != null) {
            //左子树不为空，则找到它的最右节点
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        // 能来到这里说明左子树为空, 则从父节点、祖父节点中寻找前驱节点
        // 当父节点不为空, 且某节点为父节点的左子节点
        // 则顺着父节点找, 直到找到【某结点为父节点或祖父节点的右子树中】时
        while (node.parent != null && node.parent.left == node) {
            node = node.parent;
        }

        // 来到这里有以下两种情况:
        // node.parent == null	无前驱, 说明是根结点
        // node.parent...right == node 找到【某结点为父节点或祖父节点的右子树中】
        // 那么父节点就是某节点的前驱节点
        return node.parent;
    }

    /**
     * 后继节点: 中序遍历时的后一个节点
     * 求后继节点
     */
    protected Node<E> successor(Node<E> node) {
        if (node == null) {
            return null;
        }
        //后继节点在右子树中(right.left.left.left....)
        Node<E> p = node.right;
        if (node.right != null) {
            //左子树不为空，则找到它的最右节点
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 能来到这里说明右子树为空, 则从父节点、祖父节点中寻找后继节点
        // 当父节点不为空, 且某节点为父节点的右子节点
        // 则顺着父节点找, 直到找到【某结点为父节点或祖父节点的左子树中】时
        while (node.parent != null && node.parent.right == node) {
            node = node.parent;
        }

        // 来到这里有以下两种情况:
        // node.parent == null	无后继, 说明是根结点
        // node.parent...left == node 找到【某结点为父节点或祖父节点的左子树中】
        // 那么父节点就是某节点的后继节点
        return node.parent;
    }

    /**
     * 计算二叉树的高度
     * 非递归解法
     */
    public int height() {
        if (root == null) {
            return 0;
        }
        //树的高度
        int height = 0;
        //存储着每一层的元素数量
        int levelSize = 1;
        //创建一个队列
        Queue<Node<E>> queue = new LinkedList<>();
        //将头节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //将头结点出队
            Node<E> node = queue.poll();
            levelSize--;
            //如果左子节点不为空，就将左子节点入队
            if (node.left != null) {
                queue.offer(node.left);
            }
            //如果右子节点不为空，就将右子节点入队
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) { //意味着即将要访问下一层
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }

    /**
     * 计算二叉树的高度
     * 递归解法
     *
     * @return
     */
    public int height2() {
        return height(root);
    }

    /**
     * 获取某一个节点的高度
     * 递归解法
     *
     * @param node
     * @return
     */
    private int height(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    /**
     * 判断这棵二叉树是不是完全二叉树
     */
    public boolean isComplete() {
        if (root == null) {
            return false;
        }
        //创建一个队列
        Queue<Node<E>> queue = new LinkedList<>();
        //将头节点入队
        queue.offer(root);
        //是否要求该节点是叶子节点
        boolean leaf = false;
        while (!queue.isEmpty()) {
            //将头结点出队
            Node<E> node = queue.poll();
            //要求是叶子节点，但这个节点却不是叶子节点
            if (leaf && !node.isLeaf()) {
                return false;
            }

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                //node.left == null && node.right != null
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                //包含下面两种情况
                //node.left == null && node.right == null
                //node.left != null && node.right == null
                leaf = true;
            }
        }
        return true;
    }

}
