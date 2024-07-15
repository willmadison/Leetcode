package leetcode

type TreeNode struct {
	Val         int
	Left, Right *TreeNode
}

func createBinaryTree(descriptions [][]int) *TreeNode {
	ancestryByNode := map[*TreeNode]*TreeNode{}
	nodesByValue := map[int]*TreeNode{}

	for _, description := range descriptions {
		parentValue := description[0]
		value := description[1]
		isLeft := description[2] == 1

		var parentNode *TreeNode = nil
		var node *TreeNode = nil

		if _, present := nodesByValue[parentValue]; !present {
			nodesByValue[parentValue] = &TreeNode{Val: parentValue}
		}
		parentNode = nodesByValue[parentValue]

		if _, present := nodesByValue[value]; !present {
			nodesByValue[value] = &TreeNode{Val: value}
		}
		node = nodesByValue[value]

		if isLeft {
			parentNode.Left = node
		} else {
			parentNode.Right = node
		}

		ancestryByNode[node] = parentNode
	}

	var root *TreeNode

	for _, n := range nodesByValue {
		if _, present := ancestryByNode[n]; !present {
			root = n
			break
		}
	}

	return root
}
