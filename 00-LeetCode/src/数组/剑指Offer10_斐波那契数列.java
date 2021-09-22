package 数组;

/**
 * https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/
 *
 * @author xiexu
 * @create 2021-07-20 10:38 下午
 */
public class 剑指Offer10_斐波那契数列 {

    public int fib(int n) {
        if (n <= 1) {
            return n;
        }
        int first = 0;
        int second = 1;
        for (int i = 0; i < n - 1; i++) {
            second += first;
            first = second - first;
            second = second % 1000000007;
        }
        return second;
    }

}
