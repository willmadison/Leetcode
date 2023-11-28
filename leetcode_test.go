package leetcode

import (
	"fmt"
	"math"
	"testing"

	"github.com/stretchr/testify/assert"
)

const epsilon = .00001

func TestFindMedianSortedArrays(t *testing.T) {
	cases := []struct {
		a, b     []int
		expected float64
	}{
		{
			a:        []int{1, 3},
			b:        []int{2},
			expected: 2.0,
		},
		{
			a:        []int{1, 2},
			b:        []int{3, 4},
			expected: 2.5,
		},
	}

	for _, tc := range cases {
		actual := findMedianSortedArrays(tc.a, tc.b)

		if math.Abs(actual-tc.expected) > epsilon {
			t.Fatalf("got %v, expected: %v", actual, tc.expected)
		}
	}
}

func TestLengthOfLongestSubstring(t *testing.T) {
	cases := []struct {
		given    string
		expected int
	}{
		{
			given:    "abcabcbb",
			expected: 3,
		},
		{
			given:    "bbbbb",
			expected: 1,
		},
		{
			given:    "pwwkew",
			expected: 3,
		},
	}

	for _, tc := range cases {
		actual := lengthOfLongestSubstring(tc.given)

		if actual != tc.expected {
			t.Fatalf("got %v, expected: %v", actual, tc.expected)
		}
	}
}

func TestZigZagConversion(t *testing.T) {
	cases := []struct {
		given    string
		numRows  int
		expected string
	}{
		{
			given:    "PAYPALISHIRING",
			numRows:  3,
			expected: "PAHNAPLSIIGYIR",
		},
		{
			given:    "PAYPALISHIRING",
			numRows:  4,
			expected: "PINALSIGYAHRPI",
		},
	}

	for _, tc := range cases {
		actual := convert(tc.given, tc.numRows)

		if actual != tc.expected {
			t.Fatalf("got %v, expected: %v", actual, tc.expected)
		}
	}
}

func TestRegExMatching(t *testing.T) {
	cases := []struct {
		given, pattern string
		expected       bool
	}{
		{
			given:   "aa",
			pattern: "a",
		},
		{
			given:    "aa",
			pattern:  "a*",
			expected: true,
		},
		{
			given:    "ab",
			pattern:  ".*",
			expected: true,
		},
		{
			given:    "aab",
			pattern:  "c*a*b",
			expected: true,
		},
		{
			given:   "mississippi",
			pattern: "mis*is*p*.",
		},
		{
			expected: true,
		},
	}

	for _, tc := range cases {
		actual := isMatch(tc.given, tc.pattern)

		if actual != tc.expected {
			t.Errorf("for isMatch(%v, %v) - got %v, expected: %v", tc.given, tc.pattern,
				actual, tc.expected)
		}
	}
}

func TestMergeKLists(t *testing.T) {
	given := []*ListNode{
		{Val: 1, Next: &ListNode{Val: 4, Next: &ListNode{Val: 5}}},
		{Val: 1, Next: &ListNode{Val: 3, Next: &ListNode{Val: 4}}},
		{Val: 2, Next: &ListNode{Val: 6}},
	}

	expected := &ListNode{Val: 1,
		Next: &ListNode{Val: 1,
			Next: &ListNode{Val: 2,
				Next: &ListNode{Val: 3,
					Next: &ListNode{Val: 4,
						Next: &ListNode{Val: 4,
							Next: &ListNode{Val: 5,
								Next: &ListNode{Val: 6}}}}}}}}

	actual := mergeKLists(given)

	assert.Equal(t, expected, actual)
}

func TestNumIslands(t *testing.T) {
	cases := []struct {
		given    [][]byte
		expected int
	}{
		{
			[][]byte{
				{'1', '1', '1', '1', '0'},
				{'1', '1', '0', '1', '0'},
				{'1', '1', '0', '0', '0'},
				{'0', '0', '0', '0', '0'},
			},
			1,
		},
		{
			[][]byte{
				{'1', '1', '0', '0', '0'},
				{'1', '1', '0', '0', '0'},
				{'0', '0', '1', '0', '0'},
				{'0', '0', '0', '1', '1'},
			},
			3,
		},
		{
			[][]byte{
				{'1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '0', '1', '1'},
				{'0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '0'},
				{'1', '0', '1', '1', '1', '0', '0', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '0', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '0', '1', '1', '1', '0', '1', '1', '1'},
				{'0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '0', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '0', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1'},
				{'1', '0', '1', '1', '1', '1', '1', '0', '1', '1', '1', '0', '1', '1', '1', '1', '0', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '0'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '0', '0'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
			},
			1,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("numIslands(%v)", tc.given), func(t *testing.T) {
			actual := numIslands(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestLRUCacheBaseCase(t *testing.T) {
	cache := Constructor(2)

	cache.Put(1, 1)
	cache.Put(2, 2)

	assert.Equal(t, 1, cache.Get(1))

	cache.Put(3, 3) // Should evict the item keyed under 2 since it hasn't been accessed...

	assert.Equal(t, -1, cache.Get(2))

	cache.Put(4, 4) // Should evict the item keyed under 1

	assert.Equal(t, -1, cache.Get(1))
	assert.Equal(t, 3, cache.Get(3))
	assert.Equal(t, 4, cache.Get(4))
}

func TestLRUCacheSubmissionCase(t *testing.T) {
	cache := Constructor(2)

	assert.Equal(t, -1, cache.Get(2))

	cache.Put(2, 6)

	assert.Equal(t, -1, cache.Get(1))

	cache.Put(1, 5)
	cache.Put(1, 2)

	assert.Equal(t, 2, cache.Get(1))
	assert.Equal(t, 6, cache.Get(2))

	cache.Put(4, 7)

	assert.Equal(t, -1, cache.Get(1))
}

func TestSpiralMatrix(t *testing.T) {
	cases := []struct {
		given    [][]int
		expected []int
	}{
		{
			[][]int{
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9},
			},
			[]int{1, 2, 3, 6, 9, 8, 7, 4, 5},
		},
		{
			[][]int{
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10, 11, 12},
			},
			[]int{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7},
		},
		{
			[][]int{
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10, 11, 12},
				{13, 14, 15, 16},
			},
			[]int{1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10},
		},
		{
			[][]int{},
			[]int{},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("spiralOrder(%v)", tc.given), func(t *testing.T) {
			actual := spiralOrder(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}

}

func TestAddTwoNumbers(t *testing.T) {
	cases := []struct {
		name  string
		given struct {
			l1, l2 *ListNode
		}
		expected *ListNode
	}{
		{
			"numbers of same length",
			struct {
				l1, l2 *ListNode
			}{
				l1: &ListNode{2, &ListNode{4, &ListNode{3, nil}}},
				l2: &ListNode{5, &ListNode{6, &ListNode{4, nil}}},
			},
			&ListNode{7, &ListNode{0, &ListNode{8, nil}}},
		},
		{
			"numbers of different length",
			struct {
				l1, l2 *ListNode
			}{
				l1: &ListNode{2, &ListNode{4, &ListNode{3, nil}}},
				l2: &ListNode{5, &ListNode{6, nil}},
			},
			&ListNode{7, &ListNode{0, &ListNode{4, nil}}},
		},
		{
			"carry all the time",
			struct {
				l1, l2 *ListNode
			}{
				l1: &ListNode{9, &ListNode{9, &ListNode{9, nil}}},
				l2: &ListNode{9, &ListNode{9, nil}},
			},
			&ListNode{8, &ListNode{9, &ListNode{0, &ListNode{1, nil}}}},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("addTwoNumbers(%v, %v): %v", tc.given.l1, tc.given.l2, tc.name), func(t *testing.T) {
			actual := addTwoNumbers(tc.given.l1, tc.given.l2)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestIsPalindrome(t *testing.T) {
	cases := []struct {
		given    string
		expected bool
	}{
		{
			"A man, a plan, a canal: Panama",
			true,
		},
		{
			"race a car",
			false,
		},
		{
			" ",
			true,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("isPalindrome(%v)", tc.given), func(t *testing.T) {
			actual := isPalindrome(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}

}
