package 链表;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/
 *
 * @author xiexu
 * @create 2021-07-14 9:09 下午
 */
public class _206_反转链表 {

    //递归解法
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    //非递归解法
    public ListNode reverseList1(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode tmp = head.next;
            head.next = newHead;
            newHead = head;
            head = tmp;
        }
        return newHead;
    }
}
