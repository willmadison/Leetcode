package leetcode

import (
	"math"
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

func luckyNumbers(matrix [][]int) []int {
	minimumByRow := map[int]int{}
	maximumByColumn := map[int]int{}

	for row, values := range matrix {
		min := determineMinimum(values)
		minimumByRow[row] = min
	}

	columns := [][]int{}

	for i := 0; i < len(matrix[0]); i++ {
		columns = append(columns, []int{})
	}

	for column := range columns {
		for row := range matrix {
			columns[column] = append(columns[column], matrix[row][column])
		}

		maximum := determineMaximum(columns[column])
		maximumByColumn[column] = maximum
	}

	luckies := []int{}

	for row := range matrix {
		for col := range matrix[row] {
			min := minimumByRow[row]
			max := maximumByColumn[col]

			isLucky := min == max

			if isLucky {
				luckies = append(luckies, min)
			}
		}
	}

	return luckies
}

func determineMinimum(values []int) int {
	min := math.MaxInt64

	for _, n := range values {
		if n < min {
			min = n
		}
	}

	return min
}

func determineMaximum(values []int) int {
	maximum := math.MinInt64

	for _, n := range values {
		if n > maximum {
			maximum = n
		}
	}

	return maximum
}

func buildMatrix(k int, rowConditions [][]int, colConditions [][]int) [][]int {
	rowOrder := topologicalSort(rowConditions, k)
	colOrder := topologicalSort(colConditions, k)

	if len(rowOrder) == 0 || len(colOrder) == 0 {
		return [][]int{}
	}

	matrix := make([][]int, k)

	for i := 0; i < k; i++ {
		matrix[i] = make([]int, k)
	}

	for i := 0; i < k; i++ {
		for j := 0; j < k; j++ {
			if rowOrder[i] == colOrder[j] {
				matrix[i][j] = rowOrder[i]
			}
		}
	}

	return matrix
}

func topologicalSort(edges [][]int, n int) []int {
	var adjacencyList [][]int

	for i := 0; i <= n; i++ {
		adjacencyList = append(adjacencyList, []int{})
	}

	for _, edge := range edges {
		adjacencyList[edge[0]] = append(adjacencyList[edge[0]], edge[1])
	}

	order := []int{}
	visited := make([]int, n+1)
	var cycleDetected bool

	var dfs func(int)

	dfs = func(node int) {
		visited[node] = 1

		for _, neighbor := range adjacencyList[node] {
			if visited[neighbor] == 0 {
				dfs(neighbor)

				if cycleDetected {
					return
				}
			} else if visited[neighbor] == 1 {
				cycleDetected = true
				return
			}
		}

		visited[node] = 2
		order = append(order, node)
	}

	for i := 1; i <= n; i++ {
		if visited[i] == 0 {
			dfs(i)

			if cycleDetected {
				return []int{}
			}
		}
	}

	for i, j := 0, len(order)-1; i < j; i, j = i+1, j-1 {
		order[i], order[j] = order[j], order[i]
	}

	return order
}

type Node struct {
	Val      int
	Children []*Node
}

func postorder(root *Node) []int {
	if root == nil {
		return []int{}
	}

	var traversal []int

	for _, child := range root.Children {
		if child != nil {
			traversal = append(traversal, postorder(child)...)
		}
	}

	traversal = append(traversal, root.Val)

	return traversal
}
