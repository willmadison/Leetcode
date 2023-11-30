package grind75

import (
	"errors"
	"math"
	"sort"
	"strings"
	"unicode"
)

// https://leetcode.com/problems/two-sum
func twoSum(nums []int, target int) []int {
	indiciesByValue := map[int]map[int]struct{}{}

	for i, value := range nums {
		if _, present := indiciesByValue[value]; !present {
			indiciesByValue[value] = map[int]struct{}{}
		}

		indiciesByValue[value][i] = struct{}{}
	}

	for i, value := range nums {
		complement := target - value

		if indicies, present := indiciesByValue[complement]; present {

			if complement == value && len(indicies) == 1 {
				continue
			}

			var otherIndex int

			for index := range indicies {
				if index != i {
					otherIndex = index
					break
				}
			}

			return []int{i, otherIndex}
		}
	}

	return nil
}

// https://leetcode.com/problems/valid-parentheses/
type stack interface {
	push(rune)
	pop() (rune, error)
	peek() (rune, error)
	empty() bool
}

type runeStack struct {
	data []rune
	size int
}

func newRuneStack() stack {
	return &runeStack{data: []rune{}}
}

func (s *runeStack) push(r rune) {
	s.data = append(s.data, r)
	s.size++
}

func (s *runeStack) pop() (rune, error) {
	if s.size > 0 {
		value := s.data[s.size-1]
		s.size--
		s.data = s.data[:s.size]
		return value, nil
	}

	return 0, errors.New("No Such Element")
}

func (s *runeStack) peek() (rune, error) {
	if s.size > 0 {
		value := s.data[s.size-1]
		return value, nil
	}

	return 0, errors.New("No Such Element")
}

func (s *runeStack) empty() bool {
	return s.size == 0
}

func isValid(s string) bool {
	openingParenByClosingParen := map[rune]rune{
		')': '(',
		']': '[',
		'}': '{',
	}

	stack := newRuneStack()

	for _, r := range s {
		switch r {
		case '(', '{', '[':
			stack.push(r)
		default:
			opening := openingParenByClosingParen[r]

			if top, err := stack.peek(); top != opening || err != nil {
				return false
			}

			stack.pop()
		}
	}

	return stack.empty()
}

// https://leetcode.com/problems/merge-two-sorted-lists/
type ListNode struct {
	Val  int
	Next *ListNode
}

func mergeTwoLists(list1 *ListNode, list2 *ListNode) *ListNode {
	var head *ListNode
	var tail *ListNode

	for list1 != nil || list2 != nil {
		var left *ListNode
		var right *ListNode

		left = list1
		right = list2

		if left != nil && right != nil {
			var smaller *ListNode

			if left.Val <= right.Val {
				smaller = left
				list1 = list1.Next
			} else {
				smaller = right
				list2 = list2.Next
			}

			if head == nil {
				head = smaller
				tail = smaller
			} else {
				tail.Next = smaller
				tail = tail.Next
			}
		} else {
			if left != nil {
				if head == nil {
					head = left
					tail = left
				} else {
					tail.Next = left
					tail = tail.Next
				}

				list1 = list1.Next
			} else if right != nil {
				if head == nil {
					head = right
					tail = right
				} else {
					tail.Next = right
					tail = tail.Next
				}

				list2 = list2.Next
			}
		}
	}

	return head
}

// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
func maxProfit(prices []int) int {
	var maximumProfit int

	lowestPrice := math.MaxInt64

	for _, price := range prices {
		if price < lowestPrice {
			lowestPrice = price
		}

		profit := price - lowestPrice

		if profit > maximumProfit {
			maximumProfit = profit
		}
	}

	return maximumProfit
}

// https://leetcode.com/problems/valid-palindrome/description/
func isPalindrome(s string) bool {
	scrubbed := scrubNonAlphanumeric(s)
	left, right := 0, len(scrubbed)-1

	for left <= right {
		if scrubbed[left] != scrubbed[right] {
			return false
		}

		left++
		right--
	}

	return true
}

func scrubNonAlphanumeric(s string) string {
	var sb strings.Builder

	for _, c := range s {
		if unicode.IsLetter(c) || unicode.IsDigit(c) {
			sb.WriteRune(c)
		}
	}

	return strings.ToLower(sb.String())
}

// https://leetcode.com/problems/invert-binary-tree/
type TreeNode struct {
	Val         int
	Left, Right *TreeNode
}

func invertTree(root *TreeNode) *TreeNode {
	if root == nil {
		return nil
	}

	invertTree(root.Left)
	invertTree(root.Right)
	root.Left, root.Right = root.Right, root.Left

	return root
}

// https://leetcode.com/problems/valid-anagram/
func isAnagram(s, t string) bool {
	if len(s) != len(t) {
		return false
	}

	return key(s) == key(t)
}

func key(value string) string {
	characters := strings.Split(value, "")

	sort.Slice(characters, func(i, j int) bool {
		return characters[i] < characters[j]
	})

	return strings.Join(characters, "")
}

func search(haystack []int, needle int) int {
	left := 0
	right := len(haystack) - 1

	for left <= right {
		midpoint := (left + right) / 2

		value := haystack[midpoint]

		if value == needle {
			return midpoint
		} else if needle < value {
			right = midpoint - 1
		} else {
			left = midpoint + 1
		}
	}

	return -1
}

type coordinate struct {
	row, col int
}

type queue interface {
	enqueue(coordinate)
	dequeue() (coordinate, error)
	peek() (coordinate, error)
	empty() bool
}

type coordinateQueue struct {
	data []coordinate
	size int
}

func (q *coordinateQueue) enqueue(value coordinate) {
	q.data = append(q.data, value)
	q.size++
}

func (q *coordinateQueue) dequeue() (coordinate, error) {
	if q.size > 0 {
		value := q.data[0]
		q.size--
		q.data = q.data[1:]

		return value, nil
	}

	return coordinate{}, errors.New("No Such Element")
}

func (q *coordinateQueue) peek() (coordinate, error) {
	if q.size > 0 {
		value := q.data[0]
		return value, nil
	}

	return coordinate{}, errors.New("No Such Element")
}

func (q coordinateQueue) empty() bool {
	return q.size == 0
}

func newCoordinateQueue() queue {
	return &coordinateQueue{data: []coordinate{}}
}

func floodFill(image [][]int, row, col, color int) [][]int {
	if image[row][col] == color {
		return image
	}

	q := newCoordinateQueue()
	origin := coordinate{row, col}
	q.enqueue(origin)

	var toFill []coordinate
	visited := map[coordinate]struct{}{}

	toFill = append(toFill, origin)

	for !q.empty() {
		coord, _ := q.dequeue()
		visited[coord] = struct{}{}

		adjacents := []coordinate{
			{coord.row - 1, coord.col},
			{coord.row, coord.col + 1},
			{coord.row + 1, coord.col},
			{coord.row, coord.col - 1},
		}

		for _, c := range adjacents {
			inbounds := c.col >= 0 && c.row >= 0 && c.col <= len(image[0])-1 && c.row <= len(image)-1

			if inbounds && image[c.row][c.col] == image[origin.row][origin.col] {
				toFill = append(toFill, c)

				if _, seen := visited[c]; !seen {
					q.enqueue(c)
				}
			}
		}
	}

	for _, coord := range toFill {
		image[coord.row][coord.col] = color
	}

	return image
}

// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
type ancestry []*TreeNode

func lowestCommonAncestor(root, p, q *TreeNode) *TreeNode {
	pLineage := determineAncestry(root, p)
	qLineage := determineAncestry(root, q)

	for pLineage[0] != qLineage[0] {
		switch {
		case len(pLineage) > len(qLineage):
			pLineage = pLineage[1:]
		case len(qLineage) > len(pLineage):
			qLineage = qLineage[1:]
		default:
			pLineage = pLineage[1:]
			qLineage = qLineage[1:]
		}
	}

	return pLineage[0]
}

func determineAncestry(root, descendant *TreeNode) ancestry {
	parentsByChild := map[*TreeNode]*TreeNode{}

	parentsByChild[root] = nil

	var found bool

	current := root

	for !found {
		switch {
		case current == descendant:
			found = true
		case descendant.Val > current.Val:
			if current.Right != nil {
				parentsByChild[current.Right] = current
			}
			current = current.Right
		default:
			if current.Left != nil {
				parentsByChild[current.Left] = current
			}
			current = current.Left
		}
	}

	var lineage ancestry

	for current != root {
		lineage = append(lineage, current)
		current = parentsByChild[current]
	}

	lineage = append(lineage, root)

	return lineage

}

// https://leetcode.com/problems/balanced-binary-tree
func isBalanced(root *TreeNode) bool {
	if root == nil {
		return true
	}

	leftDepth := maxDepth(root.Left)
	rightDepth := maxDepth(root.Right)

	if abs(leftDepth-rightDepth) > 1 {
		return false
	}

	return isBalanced(root.Left) && isBalanced(root.Right)
}

func maxDepth(node *TreeNode) int {
	if node == nil {
		return 0
	}

	return 1 + max(maxDepth(node.Left), maxDepth(node.Right))
}

func max(a, b int) int {
	if a < b {
		return b
	}

	return a
}

func abs(a int) int {
	if a < 0 {
		return -a
	}

	return a
}

// https://leetcode.com/problems/linked-list-cycle/
func hasCycle(head *ListNode) bool {
	if head == nil {
		return false
	}

	slow := head
	fast := head.Next
	if fast != nil {
		fast = fast.Next
	}

	for fast != nil && slow != nil {
		if fast == slow {
			return true
		}

		fast = fast.Next
		if fast != nil {
			fast = fast.Next
		}

		slow = slow.Next
	}

	return false
}
