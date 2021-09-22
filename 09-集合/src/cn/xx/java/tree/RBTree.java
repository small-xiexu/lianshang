package cn.xx.java.tree;

import java.util.Comparator;

/**
 * @author xiexu
 * @create 2021-08-09 9:24 上午
 */
public class RBTree<E> extends BBST<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 添加节点之后的操作
     *
     * @param node 新添加的节点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        //添加的是根节点或者上溢到达了根节点
        if (parent == null) {
            black(node); //直接将根节点染成黑色
            return;
        }

        //如果父节点是黑色，则直接返回
        if (isBlack(parent)) {
            return;
        }

        //uncle节点
        Node<E> uncle = parent.sibling();
        //grand节点
        Node<E> grand = parent.parent;

        //uncle节点是红色[B树节点上溢]
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grand);
            //grand节点当做是新添加的节点
            afterAdd(grand);
            return;
        }

        //uncle节点不是红色
        if (parent.isLeftChild()) { //L
            if (node.isLeftChild()) { //LL
                black(parent);
                red(grand);
                rotateRight(grand); //右旋
            } else { //LR
                black(node);
                red(grand);
                rotateLeft(parent); //左旋
                rotateRight(grand); //右旋
            }
        } else { //R
            if (node.isLeftChild()) { //RL
                black(node);
                red(grand);
                rotateRight(parent); //右旋
                rotateLeft(grand); //左旋
            } else { //RR
                black(parent);
                red(grand);
                rotateLeft(grand); //左旋
            }
        }
    }

    /**
     * 给节点染色
     *
     * @param node
     * @param color
     * @return
     */
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) {
            return node;
        }
        ((RBNode<E>) node).color = color;
        return node;
    }

    /**
     * 把节点染成红色
     *
     * @param node
     * @return
     */
    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    /**
     * 把节点染成黑色
     *
     * @param node
     * @return
     */
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    /**
     * 判断当前节点是什么颜色的
     * 空节点默认是黑色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    /**
     * 当前节点是否为黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 当前节点是否为红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    /**
     * 重写父类中的createNode方法
     *
     * @param element
     * @param parent
     * @return
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    /**
     * 删除节点之后的操作
     *
     * @param node 被删除的节点 或者 用以取代被删除节点的子节点（当被删除节点的度为1）
     */
    @Override
    protected void afterRemove(Node<E> node) {

        //如果删除的节点是红色 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        //parent节点
        Node<E> parent = node.parent;

        //删除的是根节点
        if (parent == null) {
            return;
        }

        //删除的是黑色叶子节点[下溢]
        //判断被删除的node是左还是右，因为被删除后该父节点的一边就会为空，所以兄弟节点就在另一边
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) { //被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { //兄弟节点是红色
                black(sibling); //兄弟节点染成黑色
                red(parent); //parent节点染成红色
                rotateLeft(parent); //右旋
                //更换兄弟
                sibling = parent.right;
            }

            //兄弟节点必然是黑色
            //如果左右节点为null，null也是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent); //处理之前先检查下父节点是不是黑色
                black(parent);
                red(sibling);
                if (parentBlack) {
                    //只需把 parent 当做被删除的节点处理即可
                    afterRemove(parent);
                }
            } else { //兄弟节点至少有1个红色子节点，向兄弟节点借元素
                //兄弟节点的右边是黑色，要对兄弟节点进行左旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right; //兄弟节点要重新赋值
                }

                //sibling继承parent的颜色
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);

                rotateLeft(parent);
            }
        } else { //被删除的节点在右边，兄弟节点在左边

            if (isRed(sibling)) { //兄弟节点是红色
                black(sibling); //兄弟节点染成黑色
                red(parent); //parent节点染成红色
                rotateRight(parent); //右旋
                //更换兄弟
                sibling = parent.left;
            }

            //兄弟节点必然是黑色
            //如果左右节点为null，null也是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent); //处理之前先检查下父节点是不是黑色
                black(parent);
                red(sibling);
                if (parentBlack) {
                    //只需把 parent 当做被删除的节点处理即可
                    afterRemove(parent);
                }
            } else { //兄弟节点至少有1个红色子节点，向兄弟节点借元素
                //兄弟节点的左边是黑色，要对兄弟节点进行左旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left; //兄弟节点要重新赋值
                }

                //sibling继承parent的颜色
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);

                rotateRight(parent);
            }
        }
    }

    /**
     * 红黑树节点
     *
     * @param <E>
     */
    private static class RBNode<E> extends Node<E> {

        //建议新添加的节点默认为 RED，
        //这样能够让红黑树的性质尽快满足（性质 1、2、3、5 都满足，性质 4 不一定）
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }

}
