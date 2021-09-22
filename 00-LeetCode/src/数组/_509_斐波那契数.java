package 数组;

/**
 * https://leetcode-cn.com/problems/fibonacci-number/
 *
 * @author xiexu
 * @create 2021-07-20 10:35 下午
 */
public class _509_斐波那契数 {
    public int fib(int n) {
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
}
