package thirtydaychallenge

// ListNode represents a single node in a singly-linked list.
type ListNode struct {
	Val  int
	Next *ListNode
}

func middleNode(head *ListNode) *ListNode {
	var middle *ListNode
	var fast *ListNode
	slow := head

	if slow.Next == nil {
		return head
	}

	if slow.Next.Next == nil {
		middle = slow.Next
	} else {
		fast = slow.Next.Next
	}

	for middle == nil {
		slow = slow.Next

		if fast.Next != nil {
			fast = fast.Next.Next
		} else {
			middle = slow
			break
		}

		switch {
		case fast == nil || fast.Next == nil:
			middle = slow.Next
		}
	}

	return middle
}
