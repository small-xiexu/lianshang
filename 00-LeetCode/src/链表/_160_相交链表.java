package 链表;

/**
 * @author xiexu
 * @create 2021-07-18 12:14 下午
 */
public class _160_相交链表 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode tmpA = headA;
        ListNode tmpB = headB;
        while (tmpA != tmpB) {
            if (tmpA == null) {
                tmpA = headB;
            } else {
                tmpA = tmpA.next;
            }

            if (tmpB == null) {
                tmpB = headA;
            } else {
                tmpB = tmpB.next;
            }
        }
        return tmpA;
    }
}
