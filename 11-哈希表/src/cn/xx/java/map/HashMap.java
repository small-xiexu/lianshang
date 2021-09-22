package cn.xx.java.map;

import cn.xx.java.printer.BinaryTreeInfo;
import cn.xx.java.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author xiexu
 * @create 2021-08-15 5:18 下午
 */
public class HashMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY]; //数组默认容量是16
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) { //size等于0，就没必要去遍历数组
            return;
        }
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    @Override
    public V put(K key, V value) {
        resize();

        int index = index(key);
        //取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = createNode(key, value, null);
            table[index] = root;
            size++;
            fixAfterPut(root);
            return null;
        }
        //添加的不是第一个节点
        //添加新的节点到红黑树上面
        //找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = key;
        int h1 = hash(k1);
        Node<K, V> result = null;
        boolean searched = false; //是否已经搜索过这个key
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {

            } else if (searched) { //已经扫描了
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else { //searched == false 还没有扫描，再根据内存地址大小决定左右
                if (node.left != null && (result = node(node.left, k1)) != null
                        || node.right != null && (result = node(node.right, k1)) != null) {
                    //已经存在这个key
                    node = result;
                    cmp = 0;
                } else { //不存在这个key
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { //相等就覆盖
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;
            }
        } while (node != null);

        //找到父节点，看看插入到父节点的哪个位置
        Node<K, V> newNode = createNode(key, value, parent); //创建新节点
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        //新添加节点之后的处理
        fixAfterPut(newNode);
        return null;
    }

    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    protected void afterRemove(Node<K, V> willNode, Node<K, V> removeNode) {

    }

    private void resize() {
        //负载因子 <= 0.75
        if (size / table.length <= DEFAULT_LOAD_FACTOR) {
            return;
        }
        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1]; //扩容为原来的2倍

        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null) {
                continue;
            }
            queue.offer(oldTable[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                //挪动代码得放在最后面
                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K, V> newNode) {
        //重置node的关系
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        int index = index(newNode);
        //取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }
        //添加的不是第一个节点
        //添加新的节点到红黑树上面
        //找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);
        //找到父节点，看看插入到父节点的哪个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        //新添加节点之后的处理
        fixAfterPut(newNode);
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
        if (size == 0) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) {
                    return true;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    /**
     * 遍历HashMap
     *
     * @param visitor
     */
    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) {
            return;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) {
                    return;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }

    public void print() {
        if (size == 0) {
            return;
        }
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("[index = " + i + "]");
            BinaryTrees.println(new BinaryTreeInfo() {

                @Override
                public Object string(Object node) {
                    return node;
                }

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }


                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }

            });
            System.out.println("-----------------------------------");
        }
    }

    protected V remove(Node<K, V> node) {
        if (node == null) {
            return null;
        }

        //一开始想删除的节点
        Node<K, V> willNode = node;

        size--;
        V oldValue = node.value;
        if (node.hasTwoChildren()) { //度为2的节点
            //找到后继节点
            Node<K, V> s = successor(node);
            //用后继节点的值，覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            //删除后继节点
            node = s;
        }

        //删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);

        if (replacement != null) { //node是度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的left，right的指向
            if (node.parent == null) { //node是度为1的节点并且是根节点
                table[index] = replacement;
            } else {
                if (node == node.parent.left) {
                    node.parent.left = replacement;
                } else { //node == node.parent.right
                    node.parent.right = replacement;
                }
            }

            //删除节点之后的处理
            fixAfterRemove(replacement);
        } else if (node.parent == null) { //node是叶子节点并且是根节点
            table[index] = null;
        } else { //node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { //node == node.parent.right
                node.parent.right = null;
            }

            //删除节点之后的处理
            fixAfterRemove(node);
        }

        //交给子类处理的方法
        //willNode是一开始想删除的节点
        //node是最后真正删除的节点
        afterRemove(willNode, node);

        return oldValue;
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

    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 = hash(k1);
        //存储查找结果
        Node<K, V> result = null;
        int cmp = 0;
        while (node != null) {
            int h2 = node.hash;
            K k2 = node.key;
            //先比较哈希值
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                return node;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            } else if (node.right != null && (result = node(node.right, k1)) != null) {
                return result;
            } else { //只能往左边找
                node = node.left;
            }
        }
        return null;
    }

    /**
     * 根据key生成对应的索引（在桶数组中的位置）
     *
     * @param key
     * @return
     */
    private int index(K key) {
        return hash(key) & (table.length - 1);
    }

    private int hash(K key) {
        //如果key等于null，直接放在数组索引为0的位置
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    private int index(Node<K, V> node) {
        return node.hash & (table.length - 1);
    }

    private void fixAfterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        //添加的是根节点 或者 上溢到达了根节点
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
            fixAfterPut(grand);
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

    private void fixAfterRemove(Node<K, V> node) {

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
                    fixAfterRemove(parent);
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
                    fixAfterRemove(parent);
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
            table[index(grand)] = parent;
        }

        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        //更新grand的parent
        grand.parent = parent;
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

    protected static class Node<K, V> {
        int hash;
        K key;
        V value;
        boolean color;
        Node<K, V> left; //左子节点
        Node<K, V> right; //右子节点
        Node<K, V> parent; //父节点

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
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

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }

}
