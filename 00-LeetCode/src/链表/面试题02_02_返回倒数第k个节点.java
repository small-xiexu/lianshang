package 链表;

/**
 * https://leetcode-cn.com/problems/kth-node-from-end-of-list-lcci/
 *
 * @author xiexu
 * @create 2021-07-24 9:28 上午
 */
public class 面试题02_02_返回倒数第k个节点 {

    public int kthToLast(ListNode head, int k) {
        ListNode tmp = head;
        int size = 0;
        while (tmp != null) {
            size++;
            tmp = tmp.next;
        }
        for (int i = 0; i < size - k; i++) {
            head = head.next;
        }
        return head.val;
    }

}
