package 二叉树;

/**
 * https://leetcode-cn.com/problems/minimum-distance-between-bst-nodes/
 *
 * @author xiexu
 * @create 2021-08-02 10:37 下午
 */
public class _783_二叉搜索树节点最小距离 {

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

    public int minDiffInBST(TreeNode root) {
        if (root == null) {
            return 0;
        }
        inorderTraversal(root);
        return result;
    }
}
