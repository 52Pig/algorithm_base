package org.apache.hadoop.examples;

public class SingleListReverse {

	//链表定义
	class ListNode{
		int val;
		ListNode next;
		
		ListNode(int x){
			val = x;
		}
		
	}
	/**
	 * 非递归实现，只遍历一遍链表，在遍历过程中，把遍历的节点一次插入到头部
	 * @param head
	 * @return
	 */
	public ListNode reverseList(ListNode head){
		ListNode prev = null;
		while(head != null){
			ListNode tmp = head.next;
			head.next = prev;
			prev = head;
			head = tmp;
		}
		return prev;
	}
	
	/**
	 * 递归实现：翻转head ->为首的链表，然后head变为尾部节点
	 * @param head
	 * @return
	 */
	public ListNode reverseListRecursion(ListNode head){
		if(head == null || head.next == null){
			return head;
		}
		ListNode prev = reverseListRecursion(head.next);
		head.next.next = head;
		head.next = null;
		return prev;
	}
}
