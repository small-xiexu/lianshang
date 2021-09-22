package cn.xx.java;

import cn.xx.java.printer.BinaryTrees;
import cn.xx.java.tree.AVLTree;
import cn.xx.java.tree.BST;
import cn.xx.java.tree.BinaryTree;

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

    public static void main(String[] args) {
        test1();
    }
}
