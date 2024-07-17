package leetcode

import (
	"strings"
)

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

type pathEntry struct {
	node      *TreeNode
	direction string
}

func getDirections(root *TreeNode, start, dest int) string {
	startNode := findNode(root, start)

	ancestryByNode := populateAncestry(root)

	q := NewQueue[*TreeNode]()
	q.Enqueue(startNode)

	visited := map[*TreeNode]struct{}{}

	path := map[*TreeNode]pathEntry{}

	visited[startNode] = struct{}{}

	for q.Size() > 0 {
		current, _ := q.Dequeue()

		if current.Val == dest {
			return retracePath(current, path)
		}

		if parent, present := ancestryByNode[current.Val]; present {
			if _, seen := visited[parent]; !seen {
				q.Enqueue(parent)
				path[parent] = pathEntry{current, "U"}
				visited[parent] = struct{}{}
			}
		}

		if _, seen := visited[current.Left]; !seen && current.Left != nil {
			q.Enqueue(current.Left)
			path[current.Left] = pathEntry{current, "L"}
			visited[current.Left] = struct{}{}
		}

		if _, seen := visited[current.Right]; !seen && current.Right != nil {
			q.Enqueue(current.Right)
			path[current.Right] = pathEntry{current, "R"}
			visited[current.Right] = struct{}{}
		}
	}

	return ""
}

func findNode(node *TreeNode, needle int) *TreeNode {
	if node == nil {
		return nil
	}

	if node.Val == needle {
		return node
	}

	result := findNode(node.Left, needle)

	if result != nil {
		return result
	}

	return findNode(node.Right, needle)
}

func populateAncestry(root *TreeNode) map[int]*TreeNode {
	ancestryByNode := map[int]*TreeNode{}

	var doPopulateAncestry func(*TreeNode)

	doPopulateAncestry = func(n *TreeNode) {
		if n == nil {
			return
		}

		if n.Left != nil {
			ancestryByNode[n.Left.Val] = n
			doPopulateAncestry(n.Left)
		}

		if n.Right != nil {
			ancestryByNode[n.Right.Val] = n
			doPopulateAncestry(n.Right)
		}
	}

	doPopulateAncestry(root)

	return ancestryByNode
}

func retracePath(node *TreeNode, path map[*TreeNode]pathEntry) string {
	nodePath := []string{}

	current, present := path[node]

	for present {
		nodePath = append(nodePath, current.direction)
		node = path[node].node
		current, present = path[node]
	}

	var pathSb strings.Builder

	for i := len(nodePath) - 1; i >= 0; i-- {
		pathSb.WriteString(nodePath[i])
	}

	return pathSb.String()
}

func delNodes(root *TreeNode, valuesToDelete []int) []*TreeNode {
	deletionLookup := map[int]struct{}{}

	for _, value := range valuesToDelete {
		deletionLookup[value] = struct{}{}
	}

	orphaned := []*TreeNode{}

	root, orphaned = handleDeletion(root, deletionLookup, orphaned)

	if root != nil {
		orphaned = append(orphaned, root)
	}

	return orphaned
}

func handleDeletion(node *TreeNode, deletionLookup map[int]struct{}, orphaned []*TreeNode) (*TreeNode, []*TreeNode) {
	if node == nil {
		return nil, orphaned
	}

	node.Left, orphaned = handleDeletion(node.Left, deletionLookup, orphaned)
	node.Right, orphaned = handleDeletion(node.Right, deletionLookup, orphaned)

	if _, markedForDeletion := deletionLookup[node.Val]; markedForDeletion {
		if node.Left != nil {
			orphaned = append(orphaned, node.Left)
		}

		if node.Right != nil {
			orphaned = append(orphaned, node.Right)
		}

		return nil, orphaned
	}

	return node, orphaned
}
