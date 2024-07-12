package leetcode

import (
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
