package april

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMiddleNode(t *testing.T) {
	cases := []struct {
		name     string
		given    *ListNode
		expected *ListNode
	}{
		{
			"Even length",
			&ListNode{1, &ListNode{2, &ListNode{3, &ListNode{4, nil}}}},
			&ListNode{3, &ListNode{4, nil}},
		},
		{
			"Odd length",
			&ListNode{1, &ListNode{2, &ListNode{3, nil}}},
			&ListNode{2, &ListNode{3, nil}},
		},
		{
			"List of 5",
			&ListNode{1, &ListNode{2, &ListNode{3, &ListNode{4, &ListNode{5, nil}}}}},
			&ListNode{3, &ListNode{4, &ListNode{5, nil}}},
		},
		{
			"Only 2",
			&ListNode{1, &ListNode{2, nil}},
			&ListNode{2, nil},
		},
		{
			"Just 1",
			&ListNode{1, nil},
			&ListNode{1, nil},
		},
	}

	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			actual := middleNode(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}

}
