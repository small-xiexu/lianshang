package cn.xx.java;

/**
 * @author xiexu
 * @create 2021-07-12 3:44 下午
 */
public class Main {

    /**
     * 斐波那契数列
     * 0 1 1 2 3 5 8 13 21 34 55 89
     */
    //时间复杂度：O(2^n)
    public static int fib1(int n) { //方法一：递归
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    /**
     * 斐波那契数列
     * 0 1 1 2 3 5 8 13 21 34 55 89
     */
    public static int fib2(int n) { //方法二：非递归方法
        if (n <= 1) {
            return n;
        }
        int first = 0; //第一个数
        int second = 1; //第二个数
        for (int i = 0; i < n - 1; i++) {
            int sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }

    /**
     * 斐波那契数列
     * 0 1 1 2 3 5 8 13 21 34 55 89
     */
    public static int fib3(int n) { //方法三：非递归方法精简版
        if (n <= 1) {
            return n;
        }
        int first = 0;
        int second = 1;
        for (int i = 0; i < n - 1; i++) {
            second += first;
            first = second - first;
        }
        return second;
    }

    //时间复杂度：O(n)
    public static void main(String[] args) {
        //测试两种方法的性能
        int n = 45;
        Times.test("fib1", new Times.Task() {
            @Override
            public void execute() {
                System.out.println(fib1(n));
            }
        });

        Times.test("fib2", new Times.Task() {
            @Override
            public void execute() {
                System.out.println(fib2(n));
            }
        });
    }
}
