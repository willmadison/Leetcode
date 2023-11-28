package leetcode

import (
	"bytes"
	"container/heap"
	"errors"
	"fmt"
	"math"
	"sort"
	"time"
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

// https://leetcode.com/problems/lru-cache/
type cacheEntry struct {
	key, index int
	lastAccess time.Time
}

func (c cacheEntry) String() string {
	return fmt.Sprintf("cacheEntry{key: %v, index: %v, lastAccess: %v}", c.key,
		c.index, c.lastAccess)
}

type priorityQueue []*cacheEntry

func (p priorityQueue) Len() int {
	return len(p)
}

func (p priorityQueue) Less(i, j int) bool {
	return p[i].lastAccess.Before(p[j].lastAccess)
}

func (p priorityQueue) Swap(i, j int) {
	p[i], p[j] = p[j], p[i]
	p[i].index = i
	p[j].index = j
}

func (p *priorityQueue) Push(x interface{}) {
	n := len(*p)
	entry := x.(*cacheEntry)
	entry.index = n
	*p = append(*p, entry)
}

func (p *priorityQueue) Pop() interface{} {
	old := *p
	n := len(old)
	entry := old[n-1]
	entry.index = -1
	*p = old[0 : n-1]

	return entry
}

func (p priorityQueue) String() string {
	var b bytes.Buffer

	b.WriteString("priorityQueue[")

	for i, e := range p {
		b.WriteString(fmt.Sprintf("%v", e))

		if i < len(p)-1 {
			b.WriteString(", ")
		}
	}

	b.WriteString("]")

	return b.String()
}

func (p *priorityQueue) updateLastAccess(c *cacheEntry) {
	c.lastAccess = time.Now()
	heap.Fix(p, c.index)
}

type LRUCache struct {
	data              map[int]int
	cacheEntriesByKey map[int]*cacheEntry
	queue             *priorityQueue
}

func Constructor(capacity int) LRUCache {
	q := make(priorityQueue, 0, capacity)
	heap.Init(&q)
	return LRUCache{data: map[int]int{},
		cacheEntriesByKey: map[int]*cacheEntry{},
		queue:             &q,
	}
}

func (c *LRUCache) Get(key int) int {
	if value, present := c.data[key]; present {
		entry := c.cacheEntriesByKey[key]
		c.queue.updateLastAccess(entry)
		return value
	}

	return -1
}

func (c *LRUCache) Put(key int, value int) {
	if _, present := c.data[key]; !present {
		if c.full() {
			c.evict()
		}

		entry := &cacheEntry{key: key, lastAccess: time.Now()}
		c.cacheEntriesByKey[key] = entry
		heap.Push(c.queue, entry)
		c.data[key] = value
	} else {
		c.data[key] = value
		entry := c.cacheEntriesByKey[key]
		c.queue.updateLastAccess(entry)
	}
}

func (c *LRUCache) full() bool {
	return cap(*c.queue) == c.queue.Len()
}

func (c *LRUCache) evict() {
	evictee := heap.Pop(c.queue).(*cacheEntry)
	delete(c.cacheEntriesByKey, evictee.key)
	delete(c.data, evictee.key)
}

// https://leetcode.com/problems/spiral-matrix
func spiralOrder(m [][]int) []int {
	type coordinate struct {
		row, col int
	}

	visited := map[coordinate]struct{}{}

	values := []int{}

	if len(m) == 0 {
		return values
	}

	current := coordinate{0, 0}

	minRow, maxRow, minCol, maxCol := 0, len(m), 0, len(m[0])

start:
	for len(visited) < len(m)*len(m[0]) {
		if _, seen := visited[current]; !seen {
			values = append(values, m[current.row][current.col])
			visited[current] = struct{}{}
		}

		canMoveRight := current.row == minRow && current.col < maxCol-1
		if canMoveRight {
			current.col++
			goto start
		}

		canMoveDown := current.col == maxCol-1 && current.row < maxRow-1
		if canMoveDown {
			current.row++
			goto start
		}

		canMoveLeft := current.row == maxRow-1 && current.col > minCol
		if canMoveLeft {
			current.col--
			goto start
		}

		canMoveUp := current.col == minCol && current.row > minRow+1
		if canMoveUp {
			current.row--
			goto start
		}

		minRow++
		maxRow--
		minCol++
		maxCol--
	}

	return values
}

func addTwoNumbers(l1, l2 *ListNode) *ListNode {
	var result *ListNode
	var last *ListNode

	var carry int

	for l1 != nil || l2 != nil {
		sum := carry

		if l1 != nil {
			sum += l1.Val
			l1 = l1.Next
		}

		if l2 != nil {
			sum += l2.Val
			l2 = l2.Next
		}

		digit := sum % 10
		carry = sum / 10

		node := &ListNode{digit, nil}

		if result == nil {
			result = node
			last = node
			continue
		}

		last.Next = node
		last = node
	}

	if carry > 0 {
		node := &ListNode{carry, nil}
		last.Next = node
	}

	return result
}
