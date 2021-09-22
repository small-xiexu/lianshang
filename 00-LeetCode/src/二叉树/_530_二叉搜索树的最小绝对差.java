package 二叉树;

/**
 * https://leetcode-cn.com/problems/minimum-absolute-difference-in-bst/
 *
 * @author xiexu
 * @create 2021-08-02 9:46 下午
 */
public class _530_二叉搜索树的最小绝对差 {

    TreeNode pre; //记录上一个遍历的节点
    int result = Integer.MAX_VALUE;

    /**
     * 中序遍历
     *
     * @param root
     * @return
     */
    public void inorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        //左
        inorderTraversal(root.left);

        //中
        if (pre != null) {
            result = Math.min(result, root.val - pre.val);
        }
        pre = root;

        //右
        inorderTraversal(root.right);
    }

    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }
        inorderTraversal(root);
        return result;
    }

}
