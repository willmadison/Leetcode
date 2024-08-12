package leetcode

import (
	"bytes"
	"container/heap"
	"fmt"
	"math"
	"sort"
	"strconv"
	"strings"
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

func averageWaitingTime(customers [][]int) float64 {
	waitTimes := []int{}

	clock := customers[0][0]

	for _, customer := range customers {
		prepTime := customer[1]
		arrivalTime := customer[0]

		if arrivalTime > clock {
			clock = arrivalTime
		}

		clock += prepTime

		waitTime := clock - arrivalTime

		waitTimes = append(waitTimes, waitTime)
	}

	totalWaitingTime := sum(waitTimes)

	return float64(totalWaitingTime) / float64(len(customers))
}

func sum(nums []int) int {
	s := 0

	for _, n := range nums {
		s += n
	}

	return s
}

// https://leetcode.com/problems/can-place-flowers/submissions/1315608799/?envType=study-plan-v2&envId=leetcode-75

func canPlaceFlowers(flowerbed []int, n int) bool {
	flowersPlaced := 0

	for location := range flowerbed {
		if flowerbed[location] == 0 {
			leftEmpty := location == 0 || flowerbed[location-1] == 0
			rightEmpty := location == len(flowerbed)-1 || flowerbed[location+1] == 0

			if leftEmpty && rightEmpty {
				flowerbed[location] = 1
				flowersPlaced++
			}
		}
	}

	return flowersPlaced >= n
}

func minOperations(logs []string) int {
	var depth int

	for _, log := range logs {
		switch log {
		case "./":
		case "../":
			if depth > 0 {
				depth--
			}
		default:
			depth++
		}
	}

	return depth
}

func productExceptSelf(nums []int) []int {
	answer := make([]int, len(nums))

	answer[0] = 1

	for i := 1; i < len(nums); i++ {
		answer[i] = nums[i-1] * answer[i-1]
	}

	rightProduct := 1

	for i := len(nums) - 1; i >= 0; i-- {
		answer[i] = answer[i] * rightProduct
		rightProduct *= nums[i]
	}

	return answer
}

func increasingTriplet(nums []int) bool {
	firstNumber := math.MaxInt64
	secondNumber := math.MaxInt64

	for _, n := range nums {
		if n <= firstNumber {
			firstNumber = n
		} else if n <= secondNumber {
			secondNumber = n
		} else {
			return true
		}
	}

	return false
}

func compress(chars []byte) int {
	var buf bytes.Buffer

	if len(chars) > 0 {
		count := 1
		lastCharacter := chars[0]

		for i := 1; i < len(chars); i++ {
			if chars[i] == lastCharacter {
				count++
			} else {
				buf.WriteByte(lastCharacter)

				if count > 1 {
					buf.WriteString(strconv.Itoa(count))
				}

				count = 1
				lastCharacter = chars[i]
			}
		}

		if count > 0 {
			buf.WriteByte(lastCharacter)

			if count > 1 {
				buf.WriteString(strconv.Itoa(count))
			}
		}

	}

	compressed := buf.String()

	if len(compressed) > 0 && len(compressed) <= len(chars) {
		for i, c := range compressed {
			chars[i] = byte(c)
		}

		return len(compressed)
	}

	return len(chars)
}

func moveZeroes(nums []int) {
	current := 0

	var numZeroes int

	for _, n := range nums {
		if n == 0 {
			numZeroes++
		} else {
			nums[current] = n
			current++
		}
	}

	for i := len(nums) - numZeroes; i < len(nums); i++ {
		nums[i] = 0
	}
}

func maximumGain(s string, x, y int) int {
	var totalScore int

	highPriorityPair := "ab"

	if y > x {
		highPriorityPair = "ba"
	}

	lowPriorityPair := "ba"

	if highPriorityPair == "ba" {
		lowPriorityPair = "ab"
	}

	firstPassResult := removeSubstring(s, highPriorityPair)
	pairsRemoved := (len(s) - len(firstPassResult)) / 2

	totalScore += pairsRemoved * max(x, y)

	secondPassResult := removeSubstring(firstPassResult, lowPriorityPair)
	pairsRemoved = (len(firstPassResult) - len(secondPassResult)) / 2

	totalScore += pairsRemoved * min(x, y)

	return totalScore
}

func removeSubstring(s, toRemove string) string {
	runeStack := NewStack[rune]()
	_ = runeStack

	for _, c := range s {
		top, err := runeStack.Peek()

		if c == rune(toRemove[1]) && err == nil && top == rune(toRemove[0]) {
			runeStack.Pop()
		} else {
			runeStack.Push(c)
		}
	}

	var sb strings.Builder

	runes := []rune{}

	for runeStack.Size() > 0 {
		top, _ := runeStack.Pop()
		runes = append(runes, top)
	}

	for i := len(runes) - 1; i >= 0; i-- {
		sb.WriteRune(runes[i])
	}

	return sb.String()
}

// https://leetcode.com/problems/container-with-most-water/submissions/1318910954/?envType=study-plan-v2&envId=leetcode-75
func maxArea(heights []int) int {
	var area int
	left, right := 0, len(heights)-1

	for left < right {
		width := right - left

		area = max(area, min(heights[left], heights[right])*width)

		if heights[left] <= heights[right] {
			left++
		} else {
			right--
		}
	}

	return area
}

func survivedRobotsHealths(positions, healths []int, directions string) []int {
	robotStack := NewStack[int]()
	indices := []int{}

	for i := range positions {
		indices = append(indices, i)
	}

	sort.Slice(indices, func(i, j int) bool {
		return positions[indices[i]] < positions[indices[j]]
	})

	for _, i := range indices {
		if directions[i] == 'R' {
			robotStack.Push(i)
		} else {
			for robotStack.Size() > 0 && healths[i] > 0 {
				top, _ := robotStack.Pop()

				if healths[top] > healths[i] {
					healths[top]--
					healths[i] = 0
					robotStack.Push(top)
				} else if healths[top] < healths[i] {
					healths[i]--
					healths[top] = 0
				} else {
					healths[i] = 0
					healths[top] = 0
				}
			}
		}
	}

	result := []int{}

	for i := range indices {
		if healths[i] > 0 {
			result = append(result, healths[i])
		}
	}

	return result
}

func sortPeople(names []string, heights []int) []string {
	type person struct {
		name   string
		height int
	}

	people := []person{}

	for i, name := range names {
		people = append(people, person{name, heights[i]})
	}

	sort.Slice(people, func(i, j int) bool {
		return people[i].height > people[j].height
	})

	sortedNames := []string{}

	for _, person := range people {
		sortedNames = append(sortedNames, person.name)
	}

	return sortedNames
}

func removeElement(nums []int, val int) int {
	if len(nums) == 1 && nums[0] == val {
		return 0
	}

	replacementIndex := len(nums) - 1

	for i, n := range nums {
		for replacementIndex >= 0 && nums[replacementIndex] == val {
			replacementIndex--
		}

		if n == val && i <= replacementIndex {
			nums[i], nums[replacementIndex] = nums[replacementIndex], nums[i]
			replacementIndex--
		}
	}

	return replacementIndex + 1
}

func frequencySort(nums []int) []int {
	countsByNumber := map[int]int{}

	for _, n := range nums {
		countsByNumber[n]++
	}

	sort.Slice(nums, func(i, j int) bool {
		if countsByNumber[nums[i]] == countsByNumber[nums[j]] {
			return nums[i] > nums[j]
		}

		return countsByNumber[nums[i]] < countsByNumber[nums[j]]
	})

	return nums
}

func mySqrt(x int) int {
	if x < 2 {
		return x
	}

	value := 1
	left, right := 2, x/2

	for left <= right {
		midpoint := left + (right-left)/2
		value = midpoint * midpoint

		switch {
		case value > x:
			right = midpoint - 1
		case value < x:
			left = midpoint + 1
		default:
			return midpoint
		}
	}

	return right
}

func sortJumbled(mapping []int, nums []int) []int {
	mappedValues := mapNumbers(mapping, nums)

	sort.SliceStable(nums, func(i, j int) bool {
		return mappedValues[nums[i]] < mappedValues[nums[j]]
	})

	return nums
}

func mapNumbers(mapping []int, nums []int) map[int]int {
	mappedValues := map[int]int{}

	for _, num := range nums {
		mappedValues[num] = doMapping(mapping, num)
	}

	return mappedValues
}

func doMapping(mapping []int, num int) int {
	digits := getDigits(num)

	var sb strings.Builder

	for _, digit := range digits {
		sb.WriteString(strconv.Itoa(mapping[digit]))
	}

	mapped, _ := strconv.Atoi(sb.String())

	return mapped
}

func getDigits(num int) []int {
	if num == 0 {
		return []int{0}
	}

	digits := []int{}

	i := num

	for i > 0 {
		digit := i % 10
		digits = append(digits, digit)
		i /= 10
	}

	left, right := 0, len(digits)-1

	for left < right {
		digits[left], digits[right] = digits[right], digits[left]
		left++
		right--
	}

	return digits
}

func minSwaps(nums []int) int {
	minSwaps := math.MaxInt64

	numOnes := sum(nums)

	onesInWindow := nums[0]
	var end int

	for start := 0; start < len(nums); start++ {
		if start != 0 {
			onesInWindow -= nums[start-1]
		}

		for end-start+1 < numOnes {
			end++
			onesInWindow += nums[end%len(nums)]
		}

		minSwaps = min(minSwaps, numOnes-onesInWindow)
	}

	return minSwaps
}

func canBeEqual(target, source []int) bool {
	frequencies := map[int]int{}

	for _, i := range target {
		frequencies[i]++
	}

	for _, i := range source {
		frequencies[i]--

		if frequencies[i] == 0 {
			delete(frequencies, i)
		}
	}

	return len(frequencies) == 0
}

type slicePriorityQueue [][]int

func (pq slicePriorityQueue) Len() int { return len(pq) }

func (pq slicePriorityQueue) Less(i, j int) bool {
	return pq[i][0] < pq[j][0]
}

func (pq slicePriorityQueue) Swap(i, j int) {
	pq[i], pq[j] = pq[j], pq[i]
}

func (pq *slicePriorityQueue) Push(x any) {
	entry := x.([]int)
	*pq = append(*pq, entry)
}

func (pq *slicePriorityQueue) Pop() any {
	old := *pq
	item := old[len(old)-1]
	old[len(old)-1] = nil
	*pq = old[0 : len(old)-1]
	return item
}

func rangeSum(nums []int, n int, left int, right int) int {
	pq := make(slicePriorityQueue, 0)

	for i := 0; i < n; i++ {
		pq.Push([]int{nums[i], i})
	}

	var answer int
	var modulo int = 1e10 + 7

	for i := 1; i <= right; i++ {
		p := pq.Pop().([]int)

		if i >= left {
			answer = (answer + p[0]) % modulo
		}

		if p[1] < n-1 {
			p[1]++
			p[0] += nums[p[1]]
			pq.Push(p)
		}
	}

	return answer
}

func kthDistinct(strings []string, k int) string {
	s := NewStack[int]()
	occurrencesByWord := map[string]int{}

	for _, word := range strings {
		occurrencesByWord[word]++
	}

	for i, word := range strings {
		if occurrencesByWord[word] == 1 {
			s.Push(i)
		}
	}

	if s.Size() < k {
		return ""
	}

	for s.Size() > k {
		s.Pop()
	}

	i, _ := s.Peek()

	return strings[i]
}

func minimumPushes(word string) int {
	characterCounts := map[rune]int{}
	characters := []rune{}

	for _, c := range word {
		if _, present := characterCounts[c]; !present {
			characters = append(characters, c)
		}

		characterCounts[c]++
	}

	sort.SliceStable(characters, func(i, j int) bool {
		return characterCounts[characters[i]] > characterCounts[characters[j]]
	})

	var totalPushes int

	for i, c := range characters {
		totalPushes += (i/8 + 1) * characterCounts[c]
	}

	return totalPushes
}

func numberToWords(num int) string {
	if num == 0 {
		return "Zero"
	}

	return convertToWords(num)
}

var underTen = []string{"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"}
var underTwenty = []string{"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"}
var underOneHundred = []string{"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"}

func convertToWords(i int) string {
	if i < 10 {
		return underTen[i]
	}

	if i < 20 {
		return underTwenty[i-10]
	}

	if i < 100 {
		base := underOneHundred[i/10]

		if i%10 != 0 {
			return base + " " + convertToWords(i%10)
		}

		return base
	}

	if i < 1000 {
		base := convertToWords(i/100) + " Hundred"

		if i%100 != 0 {
			return base + " " + convertToWords(i%100)
		}

		return base
	}

	if i < 1000000 {
		base := convertToWords(i/1000) + " Thousand"

		if i%1000 != 0 {
			return base + " " + convertToWords(i%1000)
		}

		return base
	}

	if i < 1000000000 {
		base := convertToWords(i/1000000) + " Million"

		if i%1000000 != 0 {
			return base + " " + convertToWords(i%1000000)
		}

		return base
	}

	base := convertToWords(i/1000000000) + " Billion"

	if i%1000000000 != 0 {
		return base + " " + convertToWords(i%1000000000)
	}

	return base
}

func spiralMatrixIII(rows int, cols int, rStart int, cStart int) [][]int {
	right := []int{0, 1}
	down := []int{1, 0}
	left := []int{0, -1}
	up := []int{-1, 0}

	directions := [][]int{right, down, left, up}
	path := make([][]int, rows*cols)

	for i := range path {
		path[i] = make([]int, 2)
	}

	var i int

	currentRow, currentCol := rStart, cStart

	for step, direction := 1, 0; i < rows*cols; {
		for j := 0; j < 2; j++ {
			for k := 0; k < step; k++ {
				if currentRow >= 0 && currentRow < rows && currentCol >= 0 && currentCol < cols {
					path[i][0] = currentRow
					path[i][1] = currentCol
					i++
				}

				currentRow += directions[direction][0]
				currentCol += directions[direction][1]
			}

			direction = (direction + 1) % len(directions)
		}
		step++
	}

	return path
}

type minHeap []int

func (h minHeap) Len() int           { return len(h) }
func (h minHeap) Less(i, j int) bool { return h[i] < h[j] }
func (h minHeap) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }

func (h *minHeap) Push(x any) {
	*h = append(*h, x.(int))
}

func (h *minHeap) Pop() any {
	old := *h
	n := len(old)
	x := old[n-1]
	*h = old[0 : n-1]
	return x
}

func (h minHeap) Peek() any {
	return h[0]
}

type KthLargest struct {
	k int
	h *minHeap
}

func NewKthLargest(k int, nums []int) KthLargest {
	h := minHeap([]int{})
	heap.Init(&h)

	kthLargest := KthLargest{k: k, h: &h}

	for _, i := range nums {
		kthLargest.Add(i)
	}

	return kthLargest
}

func (this *KthLargest) Add(val int) int {
	if this.h.Len() < this.k || this.h.Peek().(int) < val {
		heap.Push(this.h, val)

		if this.h.Len() > this.k {
			heap.Pop(this.h)
		}
	}

	return this.h.Peek().(int)
}
