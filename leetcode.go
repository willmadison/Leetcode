package leetcode

import (
	"bytes"
	"errors"
	"math"
	"sort"
)

// https://leetcode.com/problems/median-of-two-sorted-arrays/description/
func findMedianSortedArrays(nums1, nums2 []int) float64 {
	var all []int

	all = append(all, nums1...)
	all = append(all, nums2...)

	sort.Ints(all)

	length := len(all)

	var median float64

	midpoint := length / 2

	if isEven := length%2 == 0; isEven {
		median = float64(all[midpoint]+all[midpoint-1]) / float64(2.0)
	} else {
		median = float64(all[midpoint])
	}

	return median
}

// https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
func lengthOfLongestSubstring(word string) int {
	lastIndexOf := map[byte]int{}

	currentLength, maxLength := 0, 0

	numCharacters := len(word)

	for i := 0; i < numCharacters; i++ {
		character := word[i]
		lastIndex, seen := lastIndexOf[character]

		if !seen || i-currentLength > lastIndex {
			currentLength++
		} else {
			if currentLength > maxLength {
				maxLength = currentLength
			}

			currentLength = i - lastIndex
		}

		lastIndexOf[character] = i
	}

	if currentLength > maxLength {
		maxLength = currentLength
	}

	return maxLength
}

type RuneLocale struct {
	Rune        rune
	Row, Column int
}

type Direction int

const (
	Downward = iota
	Diagonal
)

// https://leetcode.com/problems/zigzag-conversion/description/
func convert(s string, numRows int) string {
	if numRows == 1 {
		return s
	}

	var currentRow, currentColumn int

	direction := Downward

	var runeLocales []RuneLocale

	for _, r := range s {
		runeLocales = append(runeLocales, RuneLocale{Rune: r, Row: currentRow, Column: currentColumn})

		if currentRow == numRows-1 {
			direction = Diagonal
		} else if currentRow == 0 {
			direction = Downward
		}

		if direction == Downward {
			currentRow++
		} else {
			currentRow--
			currentColumn++
		}

	}

	sort.Slice(runeLocales, func(i, j int) bool {
		return runeLocales[i].Row < runeLocales[j].Row ||
			(runeLocales[i].Row == runeLocales[j].Row && runeLocales[i].Column < runeLocales[j].Column)
	})

	var buf bytes.Buffer

	for _, c := range runeLocales {
		buf.WriteRune(c.Rune)
	}

	return buf.String()
}

// https://leetcode.com/problems/regular-expression-matching/description/
func isMatch(s, pattern string) bool {
	if pattern == "" {
		return s == ""
	}

	firstMatches := s != "" && (s[0] == pattern[0] || pattern[0] == '.')

	if len(pattern) > 1 && pattern[1] == '*' {
		return isMatch(s, pattern[2:]) || (firstMatches && isMatch(s[1:], pattern))
	} else {
		return firstMatches && isMatch(s[1:], pattern[1:])
	}
}

type ListNode struct {
	Val  int
	Next *ListNode
}

func mergeKLists(lists []*ListNode) *ListNode {
	var head, tail *ListNode

	for hasMore(lists) {
		minAt := -1
		min := math.MaxInt64

		for i, v := range lists {
			if v != nil && v.Val < min {
				minAt = i
				min = v.Val
			}
		}

		if head == nil {
			head = &ListNode{Val: min}
			tail = head
		} else {
			next := &ListNode{Val: min}
			tail.Next = next
			tail = next
		}

		lists[minAt] = lists[minAt].Next
	}

	return head
}

func hasMore(lists []*ListNode) bool {
	for _, v := range lists {
		if v != nil {
			return true
		}
	}

	return false
}

var errAllLandVisited = errors.New("AllLandVisited")

type coordinate struct {
	row, col int
}

type island struct {
	coordinates []coordinate
}

// https://leetcode.com/problems/number-of-islands/
func numIslands(grid [][]byte) int {
	islands := discoverIslands(grid)
	return len(islands)
}

func discoverIslands(grid [][]byte) []island {
	var islands []island

	visited := map[coordinate]struct{}{}

	unvisitedSpot, err := findFirstUnvisitedLandMass(grid, visited)

	for err != errAllLandVisited {
		coordinates := discoverContiguousLand(unvisitedSpot, grid, visited)
		islands = append(islands, island{coordinates: coordinates})
		markVisited(coordinates, visited)
		unvisitedSpot, err = findFirstUnvisitedLandMass(grid, visited)
	}

	return islands
}

func findFirstUnvisitedLandMass(grid [][]byte, seen map[coordinate]struct{}) (coordinate, error) {
	for row := 0; row < len(grid); row++ {
		for col := 0; col < len(grid[row]); col++ {
			c := coordinate{row, col}

			isLand := grid[row][col] == '1'
			_, visited := seen[c]

			if isLand && !visited {
				return c, nil
			}
		}
	}

	return coordinate{}, errAllLandVisited
}

func markVisited(coordinates []coordinate, visited map[coordinate]struct{}) {
	for _, c := range coordinates {
		visited[c] = struct{}{}
	}
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

func discoverContiguousLand(c coordinate, grid [][]byte, seen map[coordinate]struct{}) []coordinate {
	var coordinates []coordinate

	q := newCoordinateQueue()

	q.enqueue(c)

	for !q.empty() {
		current, _ := q.dequeue()
		seen[current] = struct{}{}

		coordinates = append(coordinates, current)

		hasLandAbove := current.row-1 >= 0 && grid[current.row-1][current.col] == '1'
		hasLandRight := current.col+1 < len(grid[c.row]) && grid[current.row][current.col+1] == '1'
		hasLandBelow := current.row+1 < len(grid) && grid[current.row+1][current.col] == '1'
		hasLandLeft := current.col-1 >= 0 && grid[current.row][current.col-1] == '1'

		if hasLandAbove {
			above := coordinate{current.row - 1, current.col}
			if _, visited := seen[above]; !visited {
				seen[above] = struct{}{}
				q.enqueue(above)
			}
		}

		if hasLandRight {
			right := coordinate{current.row, current.col + 1}
			if _, visited := seen[right]; !visited {
				seen[right] = struct{}{}
				q.enqueue(right)
			}
		}

		if hasLandBelow {
			below := coordinate{current.row + 1, current.col}
			if _, visited := seen[below]; !visited {
				seen[below] = struct{}{}
				q.enqueue(below)
			}
		}

		if hasLandLeft {
			left := coordinate{current.row, current.col - 1}
			if _, visited := seen[left]; !visited {
				seen[left] = struct{}{}
				q.enqueue(left)
			}
		}
	}

	return coordinates
}

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

// https://leetcode.com/problems/valid-parentheses/
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
