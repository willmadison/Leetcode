package leetcode

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMergeKLists(t *testing.T) {
	given := []*ListNode{
		{Val: 1, Next: &ListNode{Val: 4, Next: &ListNode{Val: 5}}},
		{Val: 1, Next: &ListNode{Val: 3, Next: &ListNode{Val: 4}}},
		{Val: 2, Next: &ListNode{Val: 6}},
	}

	expected := &ListNode{Val: 1,
		Next: &ListNode{Val: 1,
			Next: &ListNode{Val: 2,
				Next: &ListNode{Val: 3,
					Next: &ListNode{Val: 4,
						Next: &ListNode{Val: 4,
							Next: &ListNode{Val: 5,
								Next: &ListNode{Val: 6}}}}}}}}

	actual := mergeKLists(given)

	assert.Equal(t, expected, actual)
}

func TestAddTwoNumbers(t *testing.T) {
	cases := []struct {
		name  string
		given struct {
			l1, l2 *ListNode
		}
		expected *ListNode
	}{
		{
			"numbers of same length",
			struct {
				l1, l2 *ListNode
			}{
				l1: &ListNode{2, &ListNode{4, &ListNode{3, nil}}},
				l2: &ListNode{5, &ListNode{6, &ListNode{4, nil}}},
			},
			&ListNode{7, &ListNode{0, &ListNode{8, nil}}},
		},
		{
			"numbers of different length",
			struct {
				l1, l2 *ListNode
			}{
				l1: &ListNode{2, &ListNode{4, &ListNode{3, nil}}},
				l2: &ListNode{5, &ListNode{6, nil}},
			},
			&ListNode{7, &ListNode{0, &ListNode{4, nil}}},
		},
		{
			"carry all the time",
			struct {
				l1, l2 *ListNode
			}{
				l1: &ListNode{9, &ListNode{9, &ListNode{9, nil}}},
				l2: &ListNode{9, &ListNode{9, nil}},
			},
			&ListNode{8, &ListNode{9, &ListNode{0, &ListNode{1, nil}}}},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("addTwoNumbers(%v, %v): %v", tc.given.l1, tc.given.l2, tc.name), func(t *testing.T) {
			actual := addTwoNumbers(tc.given.l1, tc.given.l2)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
