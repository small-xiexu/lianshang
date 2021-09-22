package 二叉树;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/er-cha-shu-de-shen-du-lcof/
 *
 * @author xiexu
 * @create 2021-08-06 3:44 下午
 */
public class 剑指Offer55_I_二叉树的深度 {

    /**
     * 递归算法
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 非递归算法
     *
     * @param root
     * @return
     */
    public int maxDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //树的高度
        int height = 0;
        //每一层的元素数量
        int levelSize = 1;
        //创建一个队列
        Queue<TreeNode> queue = new LinkedList<>();
        //将头结点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //将头结点出队
            TreeNode node = queue.poll();
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }
}
