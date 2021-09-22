package 栈;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/build-an-array-with-stack-operations/
 *
 * @author xiexu
 * @create 2021-07-25 4:16 下午
 */
public class _1441_用栈操作构建数组 {
    public List<String> buildArray(int[] target, int n) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1, j = 0; i <= n && j < target.length; i++) {
            if (target[j] > i) {
                list.add("Push");
                list.add("Pop");
            } else if (target[j] == i) {
                list.add("Push");
                j++;
            }
        }
        return list;
    }
}
