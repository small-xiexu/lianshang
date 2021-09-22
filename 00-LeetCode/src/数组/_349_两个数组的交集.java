package 数组;

import java.util.HashSet;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-arrays/
 *
 * @author xiexu
 * @create 2021-08-13 8:23 上午
 */
public class _349_两个数组的交集 {

    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set1.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            if (set1.contains(nums2[i])) {
                set2.add(nums2[i]);
            }
        }
        int index = 0;
        int[] arr = new int[set2.size()];
        for (int i : set2) {
            arr[index++] = i;
        }
        return arr;
    }

}
