package 链表;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists-lcci/
 *
 * @author xiexu
 * @create 2021-07-18 11:28 上午
 */
public class 链表相交 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int countA = 0;
        int countB = 0;
        ListNode tmpA = headA;
        ListNode tmpB = headB;
        ListNode tmpBB = headB;
        while (headA != null) {
            countA++;
            headA = headA.next;
        }
        while (headB != null) {
            countB++;
            headB = headB.next;
        }
        for (int i = 0; i < countA; i++) {
            for (int j = 0; j < countB; j++) {
                if (tmpA == tmpB) {
                    System.out.println("Reference of the node with value = " + tmpA.val);
                    return tmpA;
                } else {
                    tmpB = tmpB.next;
                }
            }
            tmpA = tmpA.next;
            tmpB = tmpBB;
        }
        return null;
    }
}
