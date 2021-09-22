package 链表;

/**
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 *
 * @author xiexu
 * @create 2021-07-14 8:59 下午
 */
public class _237_删除链表中的节点 {

    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}


