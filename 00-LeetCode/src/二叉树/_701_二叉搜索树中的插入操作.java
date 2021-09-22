package 二叉树;

/**
 * https://leetcode-cn.com/problems/insert-into-a-binary-search-tree/
 *
 * @author xiexu
 * @create 2021-08-02 9:25 下午
 */
public class _701_二叉搜索树中的插入操作 {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode newNode = new TreeNode(val);
        if (root == null) {
            root = newNode;
            return root;
        }

        TreeNode node = root;

        while (node != null) {
            int cmp = val - node.val;
            if (cmp > 0) {
                if (node.right == null) {
                    node.right = newNode;
                    break;
                } else {
                    node = node.right;
                }
            } else {
                if (node.left == null) {
                    node.left = newNode;
                    break;
                } else {
                    node = node.left;
                }
            }
        }
        return root;
    }
}
