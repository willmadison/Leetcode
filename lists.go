package leetcode

import "math"

type ListNode struct {
	Val  int
	Next *ListNode
}

func mergeKLists(lists []*ListNode) *ListNode {
	var head, tail *ListNode

	for hasMore(lists) {
		minAt := -1
		min := math.MaxInt64

		for i, v := range lists {
			if v != nil && v.Val < min {
				minAt = i
				min = v.Val
			}
		}

		if head == nil {
			head = &ListNode{Val: min}
			tail = head
		} else {
			next := &ListNode{Val: min}
			tail.Next = next
			tail = next
		}

		lists[minAt] = lists[minAt].Next
	}

	return head
}

func hasMore(lists []*ListNode) bool {
	for _, v := range lists {
		if v != nil {
			return true
		}
	}

	return false
}

func addTwoNumbers(l1, l2 *ListNode) *ListNode {
	var result *ListNode
	var last *ListNode

	var carry int

	for l1 != nil || l2 != nil {
		sum := carry

		if l1 != nil {
			sum += l1.Val
			l1 = l1.Next
		}

		if l2 != nil {
			sum += l2.Val
			l2 = l2.Next
		}

		digit := sum % 10
		carry = sum / 10

		node := &ListNode{digit, nil}

		if result == nil {
			result = node
			last = node
			continue
		}

		last.Next = node
		last = node
	}

	if carry > 0 {
		node := &ListNode{carry, nil}
		last.Next = node
	}

	return result
}
