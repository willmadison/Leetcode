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

func TestDelNodes(t *testing.T) {
	nineteenNode := &TreeNode{
		Val: 19,
	}

	twentyNode := &TreeNode{
		Val: 20,
		Left: &TreeNode{
			Val: 15,
		},
		Right: &TreeNode{
			Val: 17,
		},
	}

	cases := []struct {
		given struct {
			root     *TreeNode
			toDelete []int
		}
		expected []*TreeNode
	}{
		{
			given: struct {
				root     *TreeNode
				toDelete []int
			}{
				root: &TreeNode{
					Val:  50,
					Left: twentyNode,
					Right: &TreeNode{
						Val:  80,
						Left: nineteenNode,
					},
				},
				toDelete: []int{80, 50},
			},
			expected: []*TreeNode{nineteenNode, twentyNode},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("delNodes(%v, %v): %v", tc.given.root, tc.given.toDelete, tc.expected), func(t *testing.T) {
			actual := delNodes(tc.given.root, tc.given.toDelete)
			assert.ElementsMatch(t, tc.expected, actual)
		})
	}
}

func TestPostorder(t *testing.T) {
	root := &Node{
		Val: 1,
		Children: []*Node{
			{
				Val: 3,
				Children: []*Node{
					{Val: 5},
					{Val: 6},
				},
			},
			{Val: 2},
			{Val: 4},
		},
	}

	actual := postorder(root)
	expected := []int{5, 6, 3, 2, 4, 1}
	assert.Equal(t, actual, expected)

}
