package 链表;

/**
 * https://leetcode-cn.com/problems/delete-middle-node-lcci/
 *
 * @author xiexu
 * @create 2021-07-24 9:21 上午
 */
public class 面试题02_03_删除中间节点 {
    public void deleteNode(ListNode node) {
        if (node == null || node.next == null) {
            return;
        }
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
