package 二叉树;

/**
 * https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/
 *
 * @author xiexu
 * @create 2021-08-02 10:55 下午
 */
public class _108_将有序数组转换为二叉搜索树 {
    public TreeNode sortedArrayToBST(int[] nums) {
        int mid = nums[nums.length / 2];
        TreeNode root = new TreeNode(nums[mid]);
        for (int i = 0; i < nums.length; i++) {
            add(root, nums[i]);
        }
        return root;
    }

    public void add(TreeNode root, int key) {
        TreeNode node = root;
        int cmp = 0;
        while (node != null) {
            cmp = key - root.val;
            if (cmp > 0) {
                if (node.right == null) {
                    node.right = new TreeNode(key);
                } else {
                    node = node.right;
                }
            } else if (cmp < 0) {
                if (node.left == null) {
                    node.left = new TreeNode(key);
                } else {
                    node = node.left;
                }
            } else {
                node.val = key;
                return;
            }
        }
    }
}
