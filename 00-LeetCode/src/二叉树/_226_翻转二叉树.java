package 二叉树;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree/
 *
 * @author xiexu
 * @create 2021-07-30 6:56 下午
 */
public class _226_翻转二叉树 {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        //创建一个队列
        Queue<TreeNode> queue = new LinkedList<>();
        //将头结点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //将头结点出队
            TreeNode node = queue.poll();
            //交换左右子树
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }
}
