package 二叉树;

/**
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 *
 * @author xiexu
 * @create 2021-08-04 2:18 下午
 */
public class _104_二叉树的最大深度 {
    public int maxDepth(TreeNode root) {
        //终止条件：当树为空时结束递归，并返回当前深度0
        if (root == null) {
            return 0;
        }
        //root的左、右子树的最大深度
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        //返回的是左、右子树的最大深度+1
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
