package thirtydaychallenge

type TreeNode struct {
	Left, Right *TreeNode
	Val         int
}

var maxHeight int

func diameterOfBinaryTree(root *TreeNode) int {
	maxHeight = 1
	calculateHeight(root)
	return maxHeight - 1
}

func calculateHeight(node *TreeNode) int {
	if node == nil {
		return 0
	}

	left := calculateHeight(node.Left)
	right := calculateHeight(node.Right)
	maxHeight = max(maxHeight, left+right+1)

	return max(left, right) + 1
}
