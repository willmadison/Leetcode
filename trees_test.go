package leetcode

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestCreateBinaryTree(t *testing.T) {
	cases := []struct {
		given    [][]int
		expected *TreeNode
	}{
		{
			[][]int{
				{20, 15, 1},
				{20, 17, 0},
				{50, 20, 1},
				{50, 80, 0},
				{80, 19, 1},
			},
			&TreeNode{
				Val: 50,
				Left: &TreeNode{
					Val: 20,
					Left: &TreeNode{
						Val: 15,
					},
					Right: &TreeNode{
						Val: 17,
					},
				},
				Right: &TreeNode{
					Val: 80,
					Left: &TreeNode{
						Val: 19,
					},
				},
			},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("createBinaryTree(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := createBinaryTree(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}

}

func TestGetDirections(t *testing.T) {
	cases := []struct {
		given struct {
			root        *TreeNode
			start, dest int
		}
		expected string
	}{
		{
			given: struct {
				root        *TreeNode
				start, dest int
			}{
				root: &TreeNode{
					Val: 50,
					Left: &TreeNode{
						Val: 20,
						Left: &TreeNode{
							Val: 15,
						},
						Right: &TreeNode{
							Val: 17,
						},
					},
					Right: &TreeNode{
						Val: 80,
						Left: &TreeNode{
							Val: 19,
						},
					},
				},
				start: 50,
				dest:  20,
			},
			expected: "L",
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("getDirections(%v, %v, %v): %v", tc.given.root, tc.given.start, tc.given.dest, tc.expected), func(t *testing.T) {
			actual := getDirections(tc.given.root, tc.given.start, tc.given.dest)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
