package cn.xx.java;

import cn.xx.java.printer.BinaryTrees;
import cn.xx.java.tree.AVLTree;
import cn.xx.java.tree.RBTree;

/**
 * @author xiexu
 * @create 2021-07-26 12:13 下午
 */
public class Main {

    static void test1() {
        Integer data[] = new Integer[]{
                51, 34, 56, 68, 61, 62, 70, 26, 96, 45, 8, 20, 14, 25, 64, 39, 7, 21
        };
        AVLTree<Integer> bst = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);

        for (int i = 0; i < data.length; i++) {
            bst.remove(data[i]);
        }

        BinaryTrees.println(bst);
    }

    static void test2() {
        Integer data[] = new Integer[]{
                18, 25, 89, 70, 72, 10, 34, 39, 24, 6, 29, 47, 14
        };
        RBTree<Integer> rbTree = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rbTree.add(data[i]);
        }
        BinaryTrees.println(rbTree);
    }

    static void test3() {
        Integer data[] = new Integer[]{
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };

        RBTree<Integer> rb = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
        }

        BinaryTrees.println(rb);

        for (int i = 0; i < data.length; i++) {
            rb.remove(data[i]);
            System.out.println("---------------------------------------");
            System.out.println("[" + data[i] + "]");
            BinaryTrees.println(rb);
        }
    }

    public static void main(String[] args) {
        test3();
    }
}
