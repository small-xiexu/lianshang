package 链表;

/**
 * https://leetcode-cn.com/problems/middle-of-the-linked-list/
 *
 * @author xiexu
 * @create 2021-07-15 8:12 上午
 */
public class _876_链表的中间结点 {
    public ListNode middleNode(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode tmp = head;
        int size = 0;
        while (tmp != null) {
            size++;
            tmp = tmp.next;
        }
        int middle = size / 2;
        for (int i = 0; i < middle; i++) {
            head = head.next;
        }
        return head;
    }
}
