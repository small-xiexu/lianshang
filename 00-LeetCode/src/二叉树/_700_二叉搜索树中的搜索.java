package 二叉树;

/**
 * https://leetcode-cn.com/problems/search-in-a-binary-search-tree/
 *
 * @author xiexu
 * @create 2021-08-02 9:18 下午
 */
public class _700_二叉搜索树中的搜索 {
    public TreeNode searchBST(TreeNode root, int val) {
        TreeNode node = root;
        while (node != null) {
            int cmp = node.val - val;
            if (cmp == 0) {
                return node;
            }
            if (cmp > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }
}
