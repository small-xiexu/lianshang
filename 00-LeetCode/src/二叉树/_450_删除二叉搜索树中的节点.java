package 二叉树;

/**
 * https://leetcode-cn.com/problems/delete-node-in-a-bst/
 *
 * @author xiexu
 * @create 2021-08-02 12:40 下午
 */
public class _450_删除二叉搜索树中的节点 {

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key > root.val) { //去右子树删除节点
            root.right = deleteNode(root.right, key);
        } else if (key < root.val) { //去左子树删除节点
            root.left = deleteNode(root.left, key);
        } else { //当前节点就是要删除的节点
            if (root.left == null) { //欲删除的节点无左子树
                return root.right;
            }
            if (root.right == null) { //欲删除的节点无右子树
                return root.left;
            }
            //欲删除的节点左右子树都有
            TreeNode node = root.right;
            while (node.left != null) { //寻找欲删除节点右子树的最左节点
                node = node.left;
            }
            node.left = root.left; //将欲删除节点的左子树成为其右子树的最左节点的左子树
            root = root.right; //欲删除节点的右子树顶替其位置，节点被删除
        }
        return root;
    }

}
