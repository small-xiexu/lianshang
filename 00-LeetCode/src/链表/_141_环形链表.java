package 链表;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle/
 *
 * @author xiexu
 * @create 2021-07-14 9:42 下午
 */
public class _141_环形链表 {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
