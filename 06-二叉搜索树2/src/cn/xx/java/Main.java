package cn.xx.java;

import cn.xx.java.printer.BinaryTrees;
import cn.xx.java.tree.BST;
import cn.xx.java.tree.BinaryTree;

/**
 * @author xiexu
 * @create 2021-07-26 12:13 下午
 */
public class Main {

    static void test1() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);

        bst.remove(7);
        BinaryTrees.println(bst);
    }

    static void test2() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 1
        };
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);

        bst.preorder(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println();

        bst.inorder(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println();

        bst.postorder(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println();

        bst.levelOrder(new BinaryTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print(element + " ");
            }
        });
        System.out.println();
    }

    public static void main(String[] args) {
        test2();
    }
}
