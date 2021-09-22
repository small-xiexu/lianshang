package 二叉树;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/check-completeness-of-a-binary-tree/
 *
 * @author xiexu
 * @create 2021-08-06 4:05 下午
 */
public class _958_二叉树的完全性检验 {
    public boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return false;
        }
        //创建一个队列
        Queue<TreeNode> queue = new LinkedList<>();
        //将头结点入队
        queue.offer(root);
        //是否要求该节点是叶子节点
        boolean leaf = false;
        while (!queue.isEmpty()) {
            //将头结点出队
            TreeNode node = queue.poll();
            //要求是叶子节点，但这个节点却不是叶子节点
            if (leaf && !isLeaf(node)) {
                return false;
            }

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                //node.left == null && node.right != null
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                //包含下面两种情况
                //node.left == null && node.right == null
                //node.left != null && node.right == null
                leaf = true;
            }
        }
        return true;
    }

    //判断当前节点是不是叶子节点
    public boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

}


