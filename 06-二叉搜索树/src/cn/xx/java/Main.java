package cn.xx.java;

import cn.xx.java.file.Files;
import cn.xx.java.printer.BinaryTrees;

import java.util.Comparator;

/**
 * @author xiexu
 * @create 2021-07-26 12:13 下午
 */
public class Main {

    private static class PersonComparator implements Comparator<Person> {

        @Override
        public int compare(Person e1, Person e2) {
            return e1.getAge() - e2.getAge();
        }
    }

    private static class PersonComparator2 implements Comparator<Person> {

        @Override
        public int compare(Person e1, Person e2) {
            return e2.getAge() - e1.getAge();
        }
    }

    static void test1() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.print(bst);
    }

    static void test2() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BinarySearchTree<Person> bst1 = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst1.add(new Person(data[i]));
        }
        BinaryTrees.print(bst1);
    }

    static void test3() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.getAge() - o1.getAge();
            }
        });
        for (int i = 0; i < data.length; i++) {
            bst2.add(new Person(data[i]));
        }
        BinaryTrees.println(bst2);
    }

    static void test4() {
        BinarySearchTree<Integer> bst3 = new BinarySearchTree<>();
        for (int i = 0; i < 400; i++) {
            bst3.add((int) (Math.random() * 100));
        }
        String str = BinaryTrees.printString(bst3);
        Files.writeToFile("/Users/xiexu/Desktop/代码/代码/1.txt", str);
        //BinaryTrees.println(bst3);
    }

    static void test5() {
        BinarySearchTree<Person> bst4 = new BinarySearchTree<>();
        bst4.add(new Person(10, "jack"));
        bst4.add(new Person(12, "rose"));
        bst4.add(new Person(6, "jim"));
        bst4.add(new Person(10, "michael"));
        BinaryTrees.println(bst4);
    }

    static void test6() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        //bst.levelOrderTranversal();

//        bst.levelOrder(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public void visit(Integer element) {
//                System.out.print("_" + element + "_ ");
//            }
//        });

//        bst.inorder(
//                new BinarySearchTree.Visitor<Integer>() {
//                    @Override
//                    public void visit(Integer element) {
//                        System.out.print("_" + element + "_ ");
//                    }
//                }
//        );
        BinaryTrees.println(bst);
        System.out.println(bst.height());
        System.out.println(bst.isComplete());
    }

    static void test7() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);

        bst.remove(7);
        BinaryTrees.println(bst);
    }

    static void test8() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);

        bst.preorder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 2 ? true : false;
            }
        });
        System.out.println();

        bst.inorder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 4 ? true : false;
            }
        });
        System.out.println();

        bst.postorder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 4 ? true : false;
            }
        });
        System.out.println();

        bst.levelOrder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 9 ? true : false;
            }
        });
        System.out.println();
    }

    public static void main(String[] args) {
        test8();
    }
}
