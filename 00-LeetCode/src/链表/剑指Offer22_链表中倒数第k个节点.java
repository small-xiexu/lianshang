package 链表;

/**
 * https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/
 * @author xiexu
 * @create 2021-07-23 6:24 下午
 */
public class 剑指Offer22_链表中倒数第k个节点 {
    public ListNode getKthFromEnd(ListNode head, int k) {
        int size = 0;
        ListNode tail = head;
        while (tail != null) {
            size++;
            tail = tail.next;
        }
        for (int i = 0; i < size - k; i++) {
            head = head.next;
        }
        return head;
    }
}
