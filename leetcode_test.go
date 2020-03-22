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

func TestIsValid(t *testing.T) {
	cases := []struct {
		given    string
		expected bool
	}{
		{
			"()",
			true,
		},
		{
			"()[]{}",
			true,
		},
		{
			"(]",
			false,
		},
		{
			"([)]",
			false,
		},
		{
			"{[]}",
			true,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("isValid(%v)", tc.given), func(t *testing.T) {
			actual := isValid(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
