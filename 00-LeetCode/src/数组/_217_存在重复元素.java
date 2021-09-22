package 数组;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/contains-duplicate/
 *
 * @author xiexu
 * @create 2021-08-08 3:58 下午
 */
public class _217_存在重复元素 {
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }
}
