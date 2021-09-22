package 链表;

/**
 * https://leetcode-cn.com/problems/palindrome-linked-list/
 *
 * @author xiexu
 * @create 2021-07-18 12:35 下午
 */
public class _234_回文链表 {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slow = head, fast = head;
        ListNode pre = head, prepre = null;
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }
}
