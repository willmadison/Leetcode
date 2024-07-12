package leetcode

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

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
		assert.Equal(t, actual, tc.expected)
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
		assert.Equal(t, actual, tc.expected)
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

func TestIsSubsequence(t *testing.T) {
	cases := []struct {
		given struct {
			s, t string
		}
		expected bool
	}{
		{
			struct {
				s, t string
			}{
				s: "abc",
				t: "ahbgdc",
			},
			true,
		},
		{
			struct {
				s, t string
			}{
				s: "axc",
				t: "ahbgdc",
			},
			false,
		},
		{
			struct {
				s, t string
			}{
				s: "bb",
				t: "ahbgdc",
			},
			false,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("isSubsequence(%v, %v): %v", tc.given.s, tc.given.t, tc.expected), func(t *testing.T) {
			actual := isSubsequence(tc.given.s, tc.given.t)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestReverseSubstring(t *testing.T) {
	cases := []struct {
		given    string
		expected string
	}{
		{
			given:    "(abcd)",
			expected: "dcba",
		},
		{
			given:    "(u(love)i)",
			expected: "iloveu",
		},
		{
			given:    "(ed(et(oc))el)",
			expected: "leetcode",
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("reverseParenthesis(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := reverseParentheses(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
