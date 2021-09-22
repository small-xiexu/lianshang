package 链表;

/**
 * https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 *
 * @author xiexu
 * @create 2021-08-04 2:24 下午
 */
public class _24_两两交换链表中的节点 {
    public ListNode swapPairs(ListNode head) {
        //终止条件：链表只剩下一个节点或者没有节点了，没得交换了。
        // 返回的是已经处理好的链表
        if (head == null || head.next == null) {
            return head;
        }
        //一共三个节点：head、next、swapPairs(next.next)
        //下面的任务便是交换这3个节点中的前两个节点
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        //根据第二步：返回给上一级的是当前已经完成交换后，即处理好了的链表部分
        return next;
    }
}
