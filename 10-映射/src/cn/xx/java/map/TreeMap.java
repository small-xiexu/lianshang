package cn.xx.java.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author xiexu
 * @create 2021-08-13 10:39 上午
 */
public class TreeMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size; //节点数量
    private Node<K, V> root; //根节点

    private Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

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

    @Override
    public V put(K key, V value) {
        //判空
        keyNotNullCheck(key);

        //根节点为空，相当于添加第一个节点
        if (root == null) {
            root = new Node<>(key, value, null);
            size++;
            //新添加节点之后的处理
            afterPut(root);
            return null;
        }
        //添加的不是第一个节点
        //找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = commpare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { //相等就覆盖
                node.key = key;
                V oldVal = node.value;
                node.value = value;
                return oldVal;
            }
        }
        //找到父节点，看看插入到父节点的哪个位置
        Node<K, V> newNode = new Node<>(key, value, parent); //创建新节点
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        //新添加节点之后的处理
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valEquals(value, node.value)) {
                return true;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) {
            return;
        }
        traversal(root, visitor);
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

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
        Node<K, V> uncle = parent.sibling();
        //grand节点
        Node<K, V> grand = parent.parent;

        //uncle节点是红色[B树节点上溢]
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grand);
            //grand节点当做是新添加的节点
            afterPut(grand);
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

    private int commpare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        } else {
            return ((Comparable<K>) k1).compareTo(k2);
        }
    }

    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private V remove(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        size--;
        V oldValue = node.value;
        if (node.hasTwoChildren()) { //度为2的节点
            //找到后继节点
            Node<K, V> s = successor(node);
            //用后继节点的值，覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            //删除后继节点
            node = s;
        }

        //删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { //node是度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的left，right的指向
            if (node.parent == null) { //node是度为1的节点并且是根节点
                root = replacement;
            } else {
                if (node == node.parent.left) {
                    node.parent.left = replacement;
                } else { //node == node.parent.right
                    node.parent.right = replacement;
                }
            }

            //删除节点之后的处理
            afterRemove(replacement);
        } else if (node.parent == null) { //node是叶子节点并且是根节点
            root = null;
        } else { //node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { //node == node.parent.right
                node.parent.right = null;
            }

            //删除节点之后的处理
            afterRemove(node);
        }
        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {

        //如果删除的节点是红色 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        //parent节点
        Node<K, V> parent = node.parent;

        //删除的是根节点
        if (parent == null) {
            return;
        }

        //删除的是黑色叶子节点[下溢]
        //判断被删除的node是左还是右，因为被删除后该父节点的一边就会为空，所以兄弟节点就在另一边
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
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
     * 前驱节点: 中序遍历时的前一个节点
     * 求前驱节点
     */
    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        //前驱节点在左子树中(left.right.right.right....)
        Node<K, V> p = node.left;
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
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        //后继节点在右子树中(right.left.left.left....)
        Node<K, V> p = node.right;
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

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) {
            return;
        }
        traversal(node.left, visitor);
        if (visitor.stop) {
            return;
        }
        visitor.visit(node.key, node.value);
        traversal(node.right, visitor);
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    /**
     * 给节点染色
     *
     * @param node
     * @param color
     * @return
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) {
            return node;
        }
        node.color = color;
        return node;
    }

    /**
     * 把节点染成红色
     *
     * @param node
     * @return
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 把节点染成黑色
     *
     * @param node
     * @return
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 判断当前节点是什么颜色的
     * 空节点默认是黑色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * 当前节点是否为黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 当前节点是否为红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 左旋
     *
     * @param grand
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 右旋
     *
     * @param grand
     */
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 公共代码：不管是左旋转、右旋转，都要执行
     *
     * @param grand  失衡节点
     * @param parent
     * @param child
     */
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        //让parent成为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { //grand是根节点
            root = parent;
        }

        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        //更新grand的parent
        grand.parent = parent;
    }

    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = commpare(key, node.key);
            if (cmp == 0) {
                return node;
            } else if (cmp > 0) {
                node = node.right;
            } else { //cmp < 0
                node = node.left;
            }
        }
        return null;
    }

    private static class Node<K, V> {
        K key;
        V value;
        boolean color;
        Node<K, V> left; //左子节点
        Node<K, V> right; //右子节点
        Node<K, V> parent; //父节点

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
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
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }
}
