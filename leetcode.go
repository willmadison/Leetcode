package leetcode

import (
	"bytes"
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
