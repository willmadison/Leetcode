package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestBstFromPreorder(t *testing.T) {
	cases := []struct {
		given    []int
		expected *TreeNode
	}{
		{
			[]int{8, 5, 1, 7, 10, 12},
			&TreeNode{Val: 8,
				Left: &TreeNode{Val: 5,
					Left:  &TreeNode{Val: 1},
					Right: &TreeNode{Val: 7}},
				Right: &TreeNode{Val: 10,
					Right: &TreeNode{Val: 12}},
			},
		},
		{
			[]int{8, 5, 1, 6, 7, 9, 10, 12},
			&TreeNode{Val: 8,
				Left: &TreeNode{Val: 5,
					Left: &TreeNode{Val: 1},
					Right: &TreeNode{Val: 6,
						Right: &TreeNode{Val: 7}}},
				Right: &TreeNode{Val: 9,
					Right: &TreeNode{Val: 10,
						Right: &TreeNode{Val: 12}}},
			},
		},
		{
			[]int{1, 3},
			&TreeNode{Val: 1,
				Right: &TreeNode{Val: 3},
			},
		},
		{
			[]int{3, 1, 2},
			&TreeNode{Val: 3,
				Left: &TreeNode{Val: 1, Right: &TreeNode{Val: 2}},
			},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("bstFromPreorder(%v)", tc.given), func(t *testing.T) {
			actual := bstFromPreorder(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}

}
